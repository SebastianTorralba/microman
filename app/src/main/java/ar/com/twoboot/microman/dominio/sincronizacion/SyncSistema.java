package ar.com.twoboot.microman.dominio.sincronizacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.objetos.configuracion.Sistema;
import ar.com.twoboot.microman.util.Util;

public final class SyncSistema extends Sincronizador {
	private Sistema sistema;
	private Integer registros = 0;
	private String estadoTransmision = "";

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema Sistema) {
		this.sistema = Sistema;
	}

	public SyncSistema(Config configuracion, Context context) {
		super(configuracion, context);
		setmNombreTabla("Sistema");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean importar() {
		try {
			resultado = true;
			String sqlQuery = "SELECT * FROM MMsistema";
			Log.i(Util.APP, "CONECTADO");
			PreparedStatement stmt = mConexion.prepareStatement(sqlQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.execute();
			Log.i(Util.APP, sqlQuery);
			ContentValues valores = new ContentValues();
			ResultSet rs = stmt.getResultSet();
			contarRegistros(rs);
			mTrans.baseDatos.beginTransaction();
			mTrans.baseDatos.delete(getmNombreTabla(), null, null);
			while (rs.next()) {
				sistema = new Sistema();
				sistema.setVersion(rs.getString(1));
				sistema.setCpm(rs.getInt(3));
				sistema.setConsumoElevado(rs.getInt(4));
				valores.put("version", rs.getString(1));
				// valores.put("fecha_actual", rs.getString(2));
				valores.put("cpm", rs.getInt(3));
				valores.put("consumo_elevado", rs.getInt(4));

				Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
				Log.i(Util.APP, valores.toString());
				// actualizarSqlServer();
				rtn = mTrans.baseDatos.insert(getmNombreTabla(), null, valores);
				if (rtn > 0) {
					mTrans.baseDatos.setTransactionSuccessful();

				} 	
				mTrans.baseDatos.endTransaction();
				
				Log.i(Util.APP, "INSERTANDO " + rtn);
				actualizarRegistrosImportacion();
			}

			stmt.close();
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
			sistema = null;
			resultado = false;
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

}
