package ar.com.twoboot.microman.dominio.sincronizacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import ar.com.twoboot.microman.dominio.OnCliente;
import ar.com.twoboot.microman.dominio.OnHito;
import ar.com.twoboot.microman.dominio.OnMedidor;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.objetos.Hito;
import ar.com.twoboot.microman.objetos.Ruta;
import ar.com.twoboot.microman.objetos.TurnoRuta;
import ar.com.twoboot.microman.util.Util;

public final class SyncRutaRepaso extends Sincronizador {
	private String turno;
	private Ruta ruta;
	private Integer registros = 0;
	private String estadoTransmision = "";
	private String avance = "";

	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public SyncRutaRepaso(Config configuracion, Context context) {
		super(configuracion, context);
		setmNombreTabla("Ruta");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean importar() {
		try {
			resultado = true;
			getTotalImportados();
			String sqlQuery = "SELECT tc.ruta_med,rm.descripcion,tc.zona,"
					+ "tc.turno,tc.can_medidores "
					+ " FROM GMtur_cab_rep tc, GMruta_med rm"
					+ " where tc.ruta_med=rm.ruta_med and tc.turno='"
					+ getTurno() + "'";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.execute();
			Log.i(Util.APP, sqlQuery);
			ContentValues valores = new ContentValues();
			ResultSet rs = stmt.getResultSet();
			// contarRegistros(rs);
			mTrans.baseDatos.beginTransaction();
			while (rs.next()) {

				Log.i(Util.APP, String.valueOf(rs.getInt(1)));
				valores.put("codRuta", rs.getString(1));
				
				valores.put("ruta", rs.getString(2));
				valores.put("zona", rs.getString(3));
				valores.put("turno", rs.getString(4));
				valores.put("cantidadClientes", registros);
				valores.put("estado", 0);
				valores.put("fechaCarga", Util.fomtaearFecha(new Date()));
				valores.put("idUsuario", 1);
				valores.put("basedatos", getConfiguracion().getBaseDatos());
				valores.put("tipo","R");
				
				setTotalRegistros(rs.getInt(5));
				Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
				Log.i(Util.APP, valores.toString());
				// actualizarSqlServer();
				rtn = mTrans.baseDatos.insert(getmNombreTabla(), null, valores);
				if (rtn > 0) {
					resultado = importarHitos();
					if (resultado) {
						mTrans.baseDatos.setTransactionSuccessful();
						actualizarEstadoRuta('C');
					}
				} else {

				}
				mTrans.baseDatos.endTransaction();
				Log.i(Util.APP, "INSERTANDO " + rtn);
			}
			stmt.close();
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			resultado = false;
		}
		return resultado;

	}

	private Integer getTotalImportados() {
		
		Boolean resultado = false;
		try {
			String sqlQuery = "select count(*) from GMtur_ren_rep where turno='"
					+ getTurno() + "'";
			// + " and turno>='40001542'" + " and turno<='40001550'";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.execute();
			Log.i(Util.APP, sqlQuery);
			ContentValues valores = new ContentValues();
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				registros = rs.getInt(1);
			}
			Log.i(Util.APP, sqlQuery);
			stmt.close();
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
		}

		return registros;
	}
	
	private Integer getTotalExportados() {
		Integer registros = 0;
		Boolean resultado = false;
		try {
			String sqlQuery = "select count(*) from GMtur_ren_rep where est_med is not null and turno='"
					+ ruta.getTurno() + "'";
			// + " and turno>='40001542'" + " and turno<='40001550'";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.execute();
			Log.i(Util.APP, sqlQuery);
			ContentValues valores = new ContentValues();
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				registros = rs.getInt(1);
			}
			Log.i(Util.APP, sqlQuery);
			stmt.close();
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
		}

		return registros;
	}

	private Boolean importarHitos() {
		Boolean resultado = false;
		String nombreTabla = "Hito";
		try {
			resultado = true;
			String sqlQuery = "SELECT tr.ruta_med,cast(tr.ruta_sec as UNSIGNED ),concat(calle,' Nro: ',cast(altura as char(5))), "
					+ "dat_complem,tr.unidad,tr.num_med,tr.tpo_med,est_med_ant,gpsLatitud,gpsLongitud "
					+ "from GMtur_ren_rep tr,CAPITAL.GMmed_ser ms "
					+ "where  ms.tpo_med=tr.tpo_med "
					+ "and ms.num_med=tr.num_med "
					+ "and ms.unidad=tr.unidad "
					+ "and tr.turno='"
					+ getTurno()
					+ "' and ms.estado='I'"
					+ " and tr.unidad in (select unidad from CAPITAL.GCunidad)";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.execute();
			Log.i(Util.APP, sqlQuery);
			ContentValues valores = new ContentValues();
			ResultSet rs = stmt.getResultSet();
			contarRegistros(rs);
			while (rs.next()) {
				Log.i(Util.APP, String.valueOf(rs.getInt(1)));
				valores.put("codRuta", rs.getString(1));
				
				valores.put("orden", rs.getInt(2));
				valores.put("domicilio", rs.getString(3));
				valores.put("datoscomplementario", rs.getString(4));
				Integer idCliente = rs.getInt(5);
				resultado = importarClientes(idCliente);
				if (!resultado) {
					return false;
				}
				valores.put("idCliente", idCliente);
				String idMedidor = rs.getString(6);
				String modelo = rs.getString(7);
				resultado = importarMedidores(idMedidor, modelo);
				if (!resultado) {
					return false;
				}
				valores.put("idMedidor", idMedidor);
				valores.put("modelo", modelo);
				valores.put("lecturaAnterior", rs.getInt(8));
				valores.put("ordenEfectivo", 0);
				valores.put("codObservacion", "00");
				valores.put("lecturaActual", 0);
				valores.put("consumo", 0);
				valores.put("gpsLatitud", rs.getFloat(9));
				valores.put("gpsLongitud", rs.getFloat(10));
				valores.put("idUsuario", 1);
				valores.put("estado", 0);
				valores.put("FechaCarga", Util.fomtaearFecha(new Date()));
				valores.put("intentos", 0);
				valores.put("tipo","R");
				resultado = importarLecturaAnterior(idCliente, idMedidor,
						modelo);
				if (!resultado) {
					return false;
				}
				Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
				Log.i(Util.APP, valores.toString());
				rtn = mTrans.baseDatos.insert(nombreTabla, null, valores);
				if (rtn > 0) {
					resultado = true;
				} else {
					resultado = false;
				}
				if (!resultado) {
					return false;
				}
				publishProgress(actualizarRegistrosImportacion());

				Log.i(Util.APP, "INSERTANDO " + rtn);
			}
			stmt.close();
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			resultado = false;
		}
		return resultado;
	}

	private Boolean importarClientes(Integer idCliente) {
		Boolean resultado = false;
		String nombreTabla = "Cliente";
		try {
			boolean existe = new OnCliente(mTrans).existe(idCliente);
			if (!existe) {
				resultado = true;
				String sqlQuery = "SELECT unidad,razon FROM CAPITAL.GCunidad where unidad="
						+ idCliente;
				Log.i(Util.APP, "CONECTADO");
				PreparedStatement stmt = mConexion.prepareStatement(sqlQuery,
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				stmt.execute();
				Log.i(Util.APP, sqlQuery);
				ContentValues valores = new ContentValues();
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {

					Log.i(Util.APP, String.valueOf(rs.getInt(1)));
					valores.put("idCliente", rs.getInt(1));
					valores.put("razonSocial", rs.getString(2));
					rtn = mTrans.baseDatos.insert(nombreTabla, null, valores);
					if (rtn > 0) {
						resultado = true;
					} else {
						resultado = false;
					}
					if (!resultado) {
						return false;
					}

					Log.i(Util.APP, "INSERTANDO " + rtn);
				}
				stmt.close();
			} else {
				resultado = true;
			}

		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			resultado = false;
		}

		return resultado;
	}

	private Boolean importarMedidores(String idMedidor, String modelo) {
		Boolean resultado = false;
		String nombreTabla = "Medidor";
		// try {
		// resultado = true;
		// String sqlQuery =
		// "SELECT num_med,tpo_med FROM CAPITAL.GMmed_ser where num_med='"
		// + idMedidor + "' and tpo_med='" + modelo + "'";
		// Log.i(Util.APP, "CONECTADO");
		// PreparedStatement stmt = mConexion.prepareStatement(sqlQuery,
		// ResultSet.TYPE_SCROLL_INSENSITIVE,
		// ResultSet.CONCUR_READ_ONLY);
		// stmt.execute();
		// Log.i(Util.APP, sqlQuery);
		// ContentValues valores = new ContentValues();
		// ResultSet rs = stmt.getResultSet();
		// while (rs.next()) {
		//
		// Log.i(Util.APP, String.valueOf(rs.getString(1)));
		// valores.put("idMedidor", rs.getString(1));
		// valores.put("modelo", rs.getString(2));
		// rtn = mTrans.baseDatos.insert(nombreTabla, null, valores);
		// if (rtn > 0) {
		// resultado = true;
		// } else {
		// resultado = false;
		// }
		// if (!resultado) {
		// return false;
		// }
		//
		// Log.i(Util.APP, "INSERTANDO " + rtn);
		// }
		// stmt.close();
		// } catch (SQLException e) {
		// Log.e(Util.APP, "Statement error: " + e.getMessage());
		// resultado = false;
		// }
		boolean existe = new OnMedidor(mTrans).existe(idMedidor, modelo);
		if (!existe) {
			ContentValues valores = new ContentValues();
			valores.put("idMedidor", idMedidor);
			valores.put("modelo", modelo);
			rtn = mTrans.baseDatos.insert(nombreTabla, null, valores);
			if (rtn > 0) {
				resultado = true;
			} else {
				resultado = false;
			}
			if (!resultado) {
				return false;
			}

			Log.i(Util.APP, "INSERTANDO " + rtn);
		} else {
			resultado = true;
		}
		return resultado;
	}

	private Boolean importarLecturaAnterior(Integer idCliente,
			String idMedidor, String modelo) {
		Boolean resultado = false;
		String nombreTabla = "LecturasAnteriores";
		try {
			int secuencial = 0;
			resultado = true;
			String sqlQuery = "SELECT unidad,num_med,tpo_med,fec_med,sum(consumo),est_med "
					+ "FROM CAPITAL.GMest_med where num_med='"
					+ idMedidor
					+ "' and tpo_med='"
					+ modelo
					+ "'"
					+ " and unidad="
					+ idCliente
					+
					// " and `ori_med` = 'L'" +
					" group by unidad,num_med,tpo_med,fec_med , est_med"
					+ " order by fec_med desc limit 0,4";

			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.execute();
			Log.i(Util.APP, sqlQuery);
			ContentValues valores = new ContentValues();
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				secuencial++;
				Log.i(Util.APP, String.valueOf(rs.getInt(1)));
				valores.put("idCliente", rs.getInt(1));
				valores.put("idMedidor", rs.getString(2));
				valores.put("modelo", rs.getString(3));
				valores.put("secuencial", secuencial);
				valores.put("fecha",
						Util.fomtaearFecha(Util.dateToSqlDate(rs.getDate(4))));
				valores.put("Lectura", rs.getFloat(5));
				valores.put("consumo", rs.getInt(6));
				rtn = mTrans.baseDatos.insert(nombreTabla, null, valores);
				if (rtn > 0) {
					resultado = true;
				} else {
					resultado = true;
				}
				if (!resultado) {
					return false;
				}

				Log.i(Util.APP, "INSERTANDO " + rtn);
			}
			stmt.close();
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			resultado = false;
		}

		return resultado;
	}

	@Override
	public void exportar() {
		registros = 0;
		setTotalRegistros(ruta.getHitosVisitados());
		for (Iterator iterator = ruta.getIteratorHito(); iterator.hasNext();) {
			Hito hito = (Hito) iterator.next();
			if (hito.getEstado() == 1) {
				try {
					Log.i(Util.APP, "CONECTADO:" + hito.getOrden());
					PreparedStatement stmt = mConexion
							.prepareStatement("update GMtur_ren set est_med=?,cod_obs=?,consumo=?,fec_med=?,hora_med=? "
									+ " ,tipo=?,fuera_rango=?,observacion=? "
									+ " ,ajuste=0,motivo='',cons_prom=? "
									+ " where turno=? and unidad=?  and num_med=? and tpo_med=?");
					stmt.setInt(1, hito.getLecturaActual());
					stmt.setString(2, hito.getObservacion().getCodObservacion());

					stmt.setInt(3, hito.getConsumo());
					java.sql.Date fecha = Util.dateToSqlDate(hito
							.getFechaCarga());
					stmt.setDate(4, fecha);
					java.sql.Timestamp fechaHora = Util.dateToTimestamp(hito
							.getFechaCarga());

					stmt.setTimestamp(5, fechaHora);
					stmt.setString(6, hito.getTipoLectura());
					stmt.setString(7, hito.getFueraRango());
					stmt.setString(8, hito.getObservaciones());
					OnHito oHito = new OnHito(mTrans);
					oHito.setHito(hito);
					stmt.setFloat(9, oHito.calcularPromedioConsumo());
					stmt.setString(10, ruta.getTurno());
					stmt.setInt(11, hito.getCliente().getIdCliente());
					stmt.setString(12, hito.getMedidor().getIdMedidor());
					stmt.setString(13, hito.getMedidor().getModelo());

					Log.i(Util.APP, "REGISTROS " + mtotalRegistros);

					stmt.executeUpdate();
					Log.i(Util.APP, "Insertando en : " + stmt.toString());
					stmt.close();
					exportarDireccionGps(hito);
					exportarTurnoOtrosDatos(hito);
					actualizarEstadoHito(hito);
					registros++;
					actualizarRegistrosExportacion();
					publishProgress(registros);

				} catch (SQLException e) {
					Log.e(Util.APP, "Statement error: " + e.getMessage());
				}
			}
		}
		exportarTurnoCabecera();
	}

	private Boolean exportarTurnoCabecera() {
		Boolean resultado = false;
		Integer exportados = 0;
		Integer diferencia = 0;
		String estado = "";
		try {
			exportados = getTotalExportados();
			if (exportados == ruta.getCantidadClientes()) {
				estado += "Terminado";
			} else {
				estado += "Parcial";
			}
			setAvance(exportados + "/" + ruta.getCantidadClientes());

			setEstadoTransmision(estado);
			diferencia = ruta.getCantidadClientes() - exportados;
			Log.i(Util.APP, "CONECTADO:" + ruta.getCodRuta());
			PreparedStatement stmt = mConexion
					.prepareStatement("update GMtur_cab set estado='R' "
							+ " ,usu_rep=usu_gen,fec_rep=now()"
							+ " ,fec_lec=now()" + "where turno=?");

			stmt.setString(1, ruta.getTurno());

			Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
			// actualizarSqlLite();
			stmt.executeUpdate();
			Log.i(Util.APP, "Insertando en : " + stmt.toString());
			stmt.close();
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
		}
		return resultado;
	}

	private Boolean exportarTurnoOtrosDatos(Hito hito) {
		byte[] foto = new OnHito(mTrans)
				.extraerFoto(getRuta(), hito.getOrden());
		byte[] fotoComplementaria = new OnHito(mTrans)
				.extraerFotoComplementaria(getRuta(), hito.getOrden());
		Boolean resultado = false;
		eliminarOtrosDatos(hito);
		if (foto != null) {
			try {
				Log.i(Util.APP, "CONECTADO:" + hito.getOrden());
				PreparedStatement stmt = mConexion
						.prepareStatement("insert into GMimagen "
								+ "values(?,?,?,?,?,?,?,?)");
				stmt.setString(1, ruta.getTurno());
				stmt.setString(2, hito.getMedidor().getModelo());
				stmt.setString(3, hito.getMedidor().getIdMedidor());
				stmt.setInt(4, hito.getCliente().getIdCliente());
				stmt.setBytes(5, foto);
				stmt.setDouble(6, hito.getGpslatitud());
				stmt.setDouble(7, hito.getGpslongitud());
				stmt.setInt(8, hito.getOrdenEfectivo());
				Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
				// actualizarSqlLite();
				stmt.executeUpdate();
				Log.i(Util.APP, "Insertando en : " + stmt.toString());
				stmt.close();
			} catch (SQLException e) {
				Log.e(Util.APP, "Statement error: " + e.getMessage());
			}
		}
		if (fotoComplementaria != null) {
			try {
				Log.i(Util.APP, "CONECTADO:" + hito.getOrden());
				PreparedStatement stmt = mConexion
						.prepareStatement("insert into GMimagen "
								+ "values(?,?,?,?,?,?,?,?)");
				stmt.setString(1, ruta.getTurno());
				stmt.setString(2, hito.getMedidor().getModelo());
				stmt.setString(3, hito.getMedidor().getIdMedidor());
				stmt.setInt(4, hito.getCliente().getIdCliente());
				stmt.setBytes(5, fotoComplementaria);
				stmt.setDouble(6, hito.getGpslatitud());
				stmt.setDouble(7, hito.getGpslongitud());
				stmt.setInt(8, hito.getOrdenEfectivo());
				Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
				// actualizarSqlLite();
				stmt.executeUpdate();
				Log.i(Util.APP, "Insertando en : " + stmt.toString());
				stmt.close();
			} catch (SQLException e) {
				Log.e(Util.APP, "Statement error: " + e.getMessage());
			}
		}

		return resultado;
	}

	private Boolean exportarDireccionGps(Hito hito) {

		Boolean resultado = false;

		try {
			Log.i(Util.APP, "CONECTADO:" + hito.getOrden());
			PreparedStatement stmt = mConexion
					.prepareStatement("update GMmed_ser set gpsLatitud=?,gpsLongitud=? "
							+ " where tpo_med=? and num_med=?"
							+ " and (gpslatitud=0 or gpsLatitud is null) or (gpsLongitud=0 or gpsLongitud is null)");
			stmt.setDouble(1, hito.getGpslatitud());
			stmt.setDouble(2, hito.getGpslongitud());
			stmt.setString(3, hito.getMedidor().getModelo());
			stmt.setString(4, hito.getMedidor().getIdMedidor());

			Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
			// actualizarSqlLite();
			stmt.executeUpdate();
			Log.i(Util.APP, "Insertando en : " + stmt.toString());
			stmt.close();
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
		}

		return resultado;
	}

	@Override
	public void getLista(ArrayList<DalusObject> lista) {
		try {
			String sqlQuery = "Select tc.turno,tc.ruta_med,rm.descripcion from GMtur_cab_rep tc, GMruta_med rm"
					+ " where tc.ruta_med=rm.ruta_med"
					+ " and tc.estado='G' order by 2";
			// + " and turno>='40001542'" + " and turno<='40001550'";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery);
			stmt.execute();
			Log.i(Util.APP, sqlQuery);
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				TurnoRuta tr = new TurnoRuta();
				tr.setTurno(rs.getString(1));
				tr.setCodRuta(rs.getString(2));
				tr.setRuta(rs.getString(3));
				tr.setTipo("R");
				lista.add(tr);
			}
			stmt.close();
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
		}
	}

	private Boolean actualizarEstadoRuta(char estadoRutaServidor) {
		Boolean resultado = false;
		try {
			String sqlQuery = "update GMtur_cab_rep set estado='"
					+ estadoRutaServidor + "' " + "where turno='" + getTurno()
					+ "'";
			// + " and turno>='40001542'" + " and turno<='40001550'";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			resultado = stmt.execute();
			Log.i(Util.APP, sqlQuery);
			stmt.close();
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
		}
		return resultado;
	}

	private Boolean actualizarEstadoHito(Hito hito) {
		Boolean resultado = false;
		OnHito oHito = new OnHito(mTrans);
		oHito.setHito(hito);
		hito.setEstado(2);
		int rtn = oHito.actualizarEstado();
		if (rtn > 0) {
			resultado = true;
		}
		return resultado;
	}

	public Integer getRegistros() {
		return registros;
	}

	public void setRegistros(Integer registros) {
		this.registros = registros;
	}

	public String getEstadoTransmision() {
		return estadoTransmision;
	}

	public void setEstadoTransmision(String estadoTransmision) {
		this.estadoTransmision = estadoTransmision;
	}

	public String getAvance() {
		return avance;
	}

	public void setAvance(String avance) {
		this.avance = avance;
	}
	public Boolean eliminarOtrosDatos(Hito hito){
		try {
			Log.i(Util.APP, "CONECTADO:" + hito.getOrden());
			PreparedStatement stmt = mConexion
					.prepareStatement("delete from GMimagen "
							+ "WHERE turno=? and tpo_med=? and num_med=? and unidad=?");
			stmt.setString(1, ruta.getTurno());
			stmt.setString(2, hito.getMedidor().getModelo());
			stmt.setString(3, hito.getMedidor().getIdMedidor());
			stmt.setInt(4, hito.getCliente().getIdCliente());
			Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
			// actualizarSqlLite();
			stmt.executeUpdate();
			Log.i(Util.APP, "Insertando en : " + stmt.toString());
			stmt.close();
			return true;
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			return false;
		}
	}
	
}
