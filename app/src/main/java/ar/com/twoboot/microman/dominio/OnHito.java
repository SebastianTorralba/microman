package ar.com.twoboot.microman.dominio;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import ar.com.twoboot.microman.objetos.Hito;
import ar.com.twoboot.microman.objetos.LecturaAnterior;
import ar.com.twoboot.microman.objetos.Observacion;
import ar.com.twoboot.microman.objetos.Ruta;
import ar.com.twoboot.microman.util.CoderLog;
import ar.com.twoboot.microman.util.Util;

public class OnHito extends OnNegocio implements Transaccionable {
	private Hito hito;
	private byte[] foto = null;
	private byte[] fotoComplementaria = null;
	private Integer estadoHitos = Ruta.HITOS_TODOS;
	private String sqlError;

	public byte[] getFotoComplementaria() {
		return fotoComplementaria;
	}

	public void setFotoComplementaria(byte[] fotoComplementaria) {
		this.fotoComplementaria = fotoComplementaria;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Hito getHito() {
		return hito;
	}

	public void setHito(Hito hito) {
		this.hito = hito;
	}

	private String rtn;

	public OnHito(Transaccion paramTransaccion) {
		super(paramTransaccion);
		hito = new Hito();
		setNombreTabla("Hito");
	}

	public Integer calcularConsumo() {
		Integer consumo = 0;
		consumo = hito.getLecturaActual() - hito.getLecturaAnterior();
		return consumo;
	}
	public Integer calcularConsumo(Integer lecturaActual) {
		Integer consumo = 0;
		consumo = lecturaActual - hito.getLecturaAnterior();
		return consumo;
	}
		
	public int actualizar() {
		long rtn = 0;
		valores = new ContentValues();
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("ordenEfectivo", hito.getOrdenEfectivo());
			valores.put("codObservacion", hito.getObservacion()
					.getCodObservacion());
			valores.put("lecturaActual", hito.getLecturaActual());
			valores.put("consumo", hito.getConsumo());
			valores.put("gpsLatitud", hito.getGpslatitud());
			valores.put("gpsLongitud", hito.getGpslongitud());
			valores.put("idUsuario", 1/* hito.getUsuario().getIdUsuario() */);
			valores.put("estado", hito.getEstado());
			valores.put("fechaCarga", Util.fomtaearFecha(hito.getFechaCarga()));
			valores.put("intentos", hito.getIntentos());
			valores.put("fuera_rango", hito.getFueraRango());
			valores.put("tipo_lectura", hito.getTipoLectura());
			valores.put("observaciones", hito.getObservaciones());
			CoderLog.log(hito, "DATOS A GRABAR", valores.toString());
			valores.put("foto", getFoto());
			valores.put("foto_complementaria", getFotoComplementaria());
			whereString = "codRuta='" + hito.getRuta().getCodRuta() + "' "
					+ "and tipo='" + hito.getRuta().getTipo() + "' and orden="
					+ hito.getOrden();
			rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores,
					whereString, null);
			if (rtn > 0) {
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i("MicroMan", "ACTUALIZANDO " + rtn);
			} else {
				rtn = -1;
			}
			getTransaccion().baseDatos.endTransaction();
			CoderLog.log(hito, "DATOS GRABADOS", hito.toLog());
			CoderLog.log(hito, "COMMIT", "OK");

		} catch (SQLException e) {
			// TODO: handle exception
			CoderLog.log(hito, "ROLLBACK", e.getMessage());
			rtn = -1;
			setSqlError(e.getMessage());
			Log.e("MicroMan", e.getMessage());
		}
		return (int) rtn;
	}

	public void reset() {
		hito.setConsumo(0);
		hito.setFueraRango("");
		hito.setLecturaActual(0);
		hito.setObservacion(new Observacion());
		hito.setObservaciones("");
		hito.setEstado(0);
		hito.setIntentos(0);
		CoderLog.log(hito, "RESET", "HITO");

	}

	public int actualizarEstado() {
		long rtn = 0;
		valores = new ContentValues();
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("estado", hito.getEstado());

			whereString = "codRuta='" + hito.getRuta().getCodRuta() + "' "
					+ " and tipo='" + hito.getRuta().getTipo()

					+ "' and orden=" + hito.getOrden();
			rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores,
					whereString, null);
			if (rtn > 0) {
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i("MicroMan", "ACTUALIZANDO " + rtn);
			} else {
				rtn = -1;
			}
			getTransaccion().baseDatos.endTransaction();

		} catch (SQLException e) {
			// TODO: handle exception
			rtn = -1;
			setSqlError(e.getMessage());
			Log.e("MicroMan", e.getMessage());
		}
		return (int) rtn;
	}

	public int eliminar() {
		long rtn = 0;

		try {
			whereString = "codRuta='" + hito.getRuta() + "' " + " and tipo='"
					+ hito.getRuta().getTipo() + "and orden=" + hito.getOrden();
			rtn = getTransaccion().baseDatos.delete(getNombreTabla(),
					whereString, null);
			if (rtn > 0) {
				OnCliente oCliente = new OnCliente(getTransaccion());
				oCliente.setCliente(hito.getCliente());
				oCliente.eliminar();
				OnMedidor oMedidor = new OnMedidor(getTransaccion());
				oMedidor.setMedidor(hito.getMedidor());
				oMedidor.eliminar();
				OnLecturaAnterior oLecturaAnterior = new OnLecturaAnterior(
						getTransaccion());
				ArrayList<LecturaAnterior> lecturas = hito
						.getLecturasAnteriores();
				for (Iterator iterator = lecturas.iterator(); iterator
						.hasNext();) {
					LecturaAnterior lecturaAnterior = (LecturaAnterior) iterator
							.next();
					oLecturaAnterior.setLecturaAnterior(lecturaAnterior);
					oLecturaAnterior.eliminar();

				}
				Log.i("MicroMan", "BORRANDO " + rtn);
			} else {
				rtn = -1;
			}
			getTransaccion().baseDatos.endTransaction();

		} catch (SQLException e) {
			// TODO: handle exception
			rtn = -1;
			setSqlError(e.getMessage());
			Log.e("MicroMan", e.getMessage());
		}
		return (int) rtn;
	}

	public int insertar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();

			valores.put("codRuta", hito.getRuta().getCodRuta());
			valores.put("tipo", hito.getRuta().getTipo());

			valores.put("orden", hito.getOrden());
			valores.put("domicilio", hito.getDomicilio());
			valores.put("datosComplementario", hito.getDatosComplementario());
			valores.put("idCliente", hito.getCliente().getIdCliente());
			valores.put("idMedidor", hito.getMedidor().getIdMedidor());
			valores.put("lecturaAnterior", hito.getLecturaAnterior());
			valores.put("ordenEfectivo", hito.getOrdenEfectivo());
			valores.put("codObservacion", hito.getObservacion()
					.getCodObservacion());
			valores.put("lecturaActual", hito.getLecturaActual());
			valores.put("consumo", hito.getConsumo());
			// valores.put("foto", hito.getFoto());
			valores.put("gpsLatitud", hito.getGpslatitud());
			valores.put("gpsLongitud", hito.getGpslongitud());
			valores.put("idUsuario", hito.getUsuario().getIdUsuario());
			valores.put("estado", hito.getEstado());
			valores.put("fechaCarga", Util.fomtaearFecha(hito.getFechaCarga()));
			valores.put("intentos", hito.getIntentos());

			valores.put("fuera_rango", hito.getFueraRango());
			valores.put("tipo_lectura", hito.getTipoLectura());
			valores.put("observaciones", hito.getObservaciones());
			rtn = getTransaccion().baseDatos.insertOrThrow(getNombreTabla(),
					null, valores);
			if (rtn > 0) {
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i("MicroMan", "INSERTANDO " + rtn);
			} else {
				rtn = -1;
			}
			getTransaccion().baseDatos.endTransaction();

		} catch (SQLException e) {
			// TODO: handle exception
			rtn = -1;
			setSqlError(e.getMessage());
			Log.e("MicroMan", e.getMessage());
		}
		return (int) rtn;

	}

	public int validar() {
		return 0;
	}

	public void extraer(Ruta ruta) {
		boolean cargar = true;
		String sqlEstados = " and estado=";
		int estado = 0;
		if (getContext() != null) {
			pDialog = new ProgressDialog(getContext());
			pDialog.setCancelable(false);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setMessage("Procesando Ruta " + ruta.getCodRuta());
			pDialog.setMax(ruta.getCantidadClientes());
			pDialog.show();

		}
		String select = "select codRuta, orden, domicilio, datosComplementario, IdCliente, idMedidor, "
				+ "modelo, lecturaAnterior, ordenEfectivo, codObservacion,"
				+ "lecturaActual, consumo, gpsLatitud, gpsLongitud,"
				+ "estado, intentos,fuera_rango,tipo_lectura,observaciones,fechacarga "
				+ "from hito where codruta='"
				+ ruta.getCodRuta()
				+ "'"
				+ " and tipo='" + ruta.getTipo() + "'";
		if (getEstadoHitos() != Ruta.HITOS_TODOS) {
			// estado = quitarNull(localCursor.getInt(15));
			sqlEstados += getEstadoHitos().toString();
			select += sqlEstados;

		}
		Integer contador = 0;
		select += " order by orden";
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, null);
		Log.i("MicroMan", select);
		Log.i("MicroMan", String.valueOf(localCursor.getCount()));

		if (localCursor.moveToFirst()) {
			do {
				// if (getEstadoHitos() != Ruta.HITOS_TODOS) {
				// estado = quitarNull(localCursor.getInt(15));
				// if (estado == getEstadoHitos()) {
				// cargar = true;
				// } else {
				// cargar = false;
				// }
				//
				// } else {
				// cargar = true;
				// }
				//
				// if (cargar) {
				Log.i("MicroMan", "Hito:" + contador++);
				if (pDialog != null) {
					pDialog.setProgress(contador);
				}
				Hito hitoLocal = new Hito();
				hitoLocal.setOrden(quitarNull(localCursor.getInt(1)));
				hitoLocal.setDomicilio(quitarNull(localCursor.getString(2)));
				hitoLocal.setDatosComplementario(quitarNull(localCursor
						.getString(3)));
				hitoLocal.setCliente(new OnCliente(getTransaccion())
						.extraer(localCursor.getInt(4)));
				hitoLocal.setMedidor(new OnMedidor(getTransaccion()).extraer(
						localCursor.getString(5), localCursor.getString(6)));
				hitoLocal.setLecturaAnterior(quitarNull(localCursor.getInt(7)));
				hitoLocal.setOrdenEfectivo(quitarNull(localCursor.getInt(8)));
				hitoLocal.setObservacion(new OnObservacion(getTransaccion())
						.extraer(localCursor.getString(9)));
				hitoLocal.setLecturaActual(quitarNull(localCursor.getInt(10)));
				hitoLocal.setConsumo(quitarNull(localCursor.getInt(11)));
				// hitoLocal.setFoto(localCursor.getBlob(12));
				hitoLocal.setGpslatitud(quitarNull(localCursor.getDouble(12)));
				hitoLocal.setGpslongitud(quitarNull(localCursor.getDouble(13)));
				hitoLocal.setEstado(quitarNull(localCursor.getInt(14)));
				hitoLocal.setIntentos(quitarNull(localCursor.getInt(15)));
				hitoLocal.setFueraRango(quitarNull(localCursor.getString(16)));
				hitoLocal.setTipoLectura(quitarNull(localCursor.getString(17)));
				hitoLocal
						.setObservaciones(quitarNull(localCursor.getString(18)));
				hitoLocal.setFechaCarga(Util.fomtaearFecha(localCursor
						.getString(19)));
				if (getEstadoHitos() == Ruta.HITOS_LEIDOS) {
					// hitoLocal.setFoto(extraerFoto(ruta,
					// hitoLocal.getOrden()));
				}
				hitoLocal.setLecturasAnteriores(new OnLecturaAnterior(
						getTransaccion()).extraer(hitoLocal));
				hitoLocal.setRuta(ruta);
				// }
			} while (localCursor.moveToNext());
		}
		localCursor.close();
		if (pDialog != null) {
			pDialog.dismiss();
		}
	}

	public Integer calcularOrdenEfectivo() {
		Integer ordenEfectivo = 0;
		String select = "select max(ordenEfectivo) from hito where codruta='"
				+ hito.getRuta().getCodRuta() + "'";
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, null);
		if (localCursor.moveToFirst()) {
			ordenEfectivo = localCursor.getInt(0);
			ordenEfectivo++;
		}

		localCursor.close();
		return ordenEfectivo;
	}

	public byte[] extraerFoto(Ruta ruta, Integer orden) {
		String select = "select foto from hito where codruta='"
				+ ruta.getCodRuta() + "'" + " and orden=" + orden;
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, null);
		if (localCursor.moveToFirst()) {
			foto = localCursor.getBlob(0);
		}
		localCursor.close();
		return foto;
	}

	public byte[] extraerFotoComplementaria(Ruta ruta, Integer orden) {
		String select = "select foto_complementaria from hito where codruta='"
				+ ruta.getCodRuta() + "'" + " and orden=" + orden;
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, null);
		if (localCursor.moveToFirst()) {
			fotoComplementaria = localCursor.getBlob(0);
		}
		localCursor.close();
		return fotoComplementaria;
	}

	public Hito extraer(Ruta ruta, Integer orden) {
		String select = "select codRuta, orden, domicilio, datosComplementario, IdCliente, idMedidor, "
				+ "modelo, lecturaAnterior, ordenEfectivo, codObservacion,"
				+ "lecturaActual, consumo, gpsLatitud, gpsLongitud,"
				+ "estado, intentos,fuera_rango,tipo_lectura,observaciones "
				+ "from hito where codruta='"
				+ ruta.getCodRuta()
				+ " and tipo='" + ruta.getTipo() + "' and orden=" + orden;
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, null);
		if (localCursor.moveToFirst()) {
			hito.setOrden(quitarNull(localCursor.getInt(1)));
			hito.setDomicilio(quitarNull(localCursor.getString(2)));
			hito.setDatosComplementario(quitarNull(localCursor.getString(3)));
			hito.setCliente(new OnCliente(getTransaccion()).extraer(localCursor
					.getInt(4)));
			hito.setMedidor(new OnMedidor(getTransaccion()).extraer(
					localCursor.getString(5), localCursor.getString(6)));
			hito.setLecturaAnterior(quitarNull(localCursor.getInt(7)));
			hito.setOrdenEfectivo(quitarNull(localCursor.getInt(8)));
			hito.setObservacion(new OnObservacion(getTransaccion())
					.extraer(localCursor.getString(9)));
			hito.setLecturaActual(quitarNull(localCursor.getInt(10)));
			hito.setConsumo(quitarNull(localCursor.getInt(11)));
			// hito.setFoto(localCursor.getBlob(12));
			hito.setGpslatitud(quitarNull(localCursor.getDouble(13)));
			hito.setGpslongitud(quitarNull(localCursor.getDouble(14)));
			hito.setEstado(quitarNull(localCursor.getInt(16)));
			hito.setIntentos(quitarNull(localCursor.getInt(18)));
			hito.setFueraRango(quitarNull(localCursor.getString(16)));
			hito.setTipoLectura(quitarNull(localCursor.getString(17)));
			hito.setObservaciones(quitarNull(localCursor.getString(18)));

		}
		localCursor.close();
		return hito;
	}

	public boolean fueraDeRango(Integer consumo) {
		Boolean resultado = false;
		Float fueraRango = new Float(0);
		Float diferencia = new Float(0);
		Float promedio = calcularPromedioConsumo();
		if (promedio > 0) {
			if (consumo <= 0) {
				return true;
			}
			// fueraRango = Math.abs(100 - Math.abs(((promedio * 100) /
			// consumo)));
			diferencia = promedio - consumo;
			fueraRango = Math.abs(diferencia * 100 / promedio);
			if (fueraRango >= Observacion.PORCENTAJE_FUERA_RANGO) {
				if (consumo > 30) {
					resultado = true;
				} else {
					if (diferencia > 30) {
						return true;
					} else {
						return false;
					}
				}
			}
		} else {
			//ACA VER CONSUMO_NEGATIVO
			if (consumo > 30 || consumo<0) {
				resultado = true;
			} else {
				return false;
			}
		}
		return resultado;
	}

	public Float calcularPromedioConsumo() {
		Float consumoMensual = (float) 0.00;
		Integer consumo;
		Float promedio = new Float(0);
		Float promedioDiario;
		Integer diasMes = 0;
		Integer lecturas = 0;
		Integer i = 0;
		java.util.Date fechaInicio = null;
		java.util.Date fechaFin = null;
		Long dias = (long) 0;
		lecturas = hito.getLecturasAnteriores().size();
		if (lecturas > 0) {
			for (Iterator iterator = hito.getLecturasAnteriores().iterator(); iterator
					.hasNext();) {

				LecturaAnterior lectura = (LecturaAnterior) iterator.next();
				i++;
				if (i > 1) {
					consumoMensual += lectura.getConsumo();
				}
				if (lecturas == 1) {
					consumoMensual += lectura.getConsumo();
				}
				if (i == 1) {
					fechaInicio = lectura.getFecha();

				}
				if (i == lecturas) {
					fechaFin = lectura.getFecha();
				}
			}
			dias = Util.calcularDias(fechaInicio, fechaFin);
			if (dias == 0) {
				dias = (long) 1;
			}
			promedioDiario = (consumoMensual / dias);
			if (lecturas > 1) {
				diasMes = 30;
			} else {
				diasMes = 1;
			}
			promedio = (promedioDiario * diasMes);

		} else {
			promedio = new Float(0);
		}

		return promedio;
	}

	public Integer getEstadoHitos() {
		return estadoHitos;
	}

	public void setEstadoHitos(Integer estadoHitos) {
		this.estadoHitos = estadoHitos;
	}

	public String getSqlError() {
		return sqlError;
	}

	public void setSqlError(String sqlError) {
		this.sqlError = sqlError;
	}
}

/*
 * Location: C:\Documents and Settings\Administrador.TWOBOOT.000\Mis
 * documentos\android\MicroMan_dex2jar.jar Qualified Name:
 * ar.com.twoboot.microman.dominio.OnHito JD-Core Version: 0.6.0
 */
