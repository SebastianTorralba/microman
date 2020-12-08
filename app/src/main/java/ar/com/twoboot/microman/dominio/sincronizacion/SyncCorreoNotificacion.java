package ar.com.twoboot.microman.dominio.sincronizacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import ar.com.twoboot.microman.objetos.configuracion.CorreoNotificacion;
import ar.com.twoboot.microman.util.Util;

public final class SyncCorreoNotificacion extends Sincronizador {
	private CorreoNotificacion CorreoNotificacion;
	private Integer registros = 0;
	private String estadoTransmision = "";

	public CorreoNotificacion getCorreoNotificacion() {
		return CorreoNotificacion;
	}

	public void setCorreoNotificacion(CorreoNotificacion CorreoNotificacion) {
		this.CorreoNotificacion = CorreoNotificacion;
	}

	public SyncCorreoNotificacion(Config configuracion, Context context) {
		super(configuracion, context);
		setmNombreTabla("correos_notificacion");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean importar() {
		try {
			resultado = true;
			String sqlQuery = "SELECT * FROM MMcorreos_notificacion";
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

				valores.put("correo", rs.getString(2));
				valores.put("alias", rs.getString(3));
				
				//	valores.put("fecha_actual", rs.getString(2));
				Log.i(Util.APP, "REGISTROS " + mtotalRegistros);
				Log.i(Util.APP, valores.toString());
				// actualizarSqlServer();
				rtn = mTrans.baseDatos.insert(getmNombreTabla(), null, valores);
				
				Log.i(Util.APP, "INSERTANDO " + rtn);
				actualizarRegistrosImportacion();
			}
			if (rtn > 0) {
				mTrans.baseDatos.setTransactionSuccessful();

			} 
				mTrans.baseDatos.endTransaction();

			stmt.close();
		} catch (SQLException e) {
			Log.e(Util.APP, "Statement error: " + e.getMessage());
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
