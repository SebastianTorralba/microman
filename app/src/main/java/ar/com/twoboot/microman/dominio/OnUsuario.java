package ar.com.twoboot.microman.dominio;

import android.database.Cursor;
import android.util.Log;
import ar.com.twoboot.microman.objetos.Usuario;
import ar.com.twoboot.microman.util.Util;

public class OnUsuario extends OnNegocio  {
	private Usuario usuario;

	public OnUsuario(Transaccion paramTransaccion) {
		super(paramTransaccion);
	}


	public Usuario extraer(Integer idUsuario) {
		String selectQuery = "select * from usuario where id_usuario="
				+ idUsuario;
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		Log.i(Util.APP, "REGISTROS: " + String.valueOf(cursor.getCount()));
		if (cursor.moveToFirst()) {
			usuario = new Usuario();
			usuario.setIdUsuario(cursor.getInt(0));
			usuario.setUsuario(cursor.getString(1));
			usuario.setClave(cursor.getString(1));
		} else {
			return null;
		}
		cursor.close();

		return usuario;
	}
	public Usuario extraer(String usuario, String clave) {
		String selectQuery = "select * from usuario where usuario='"
				+ usuario + "'" + " and clave='" + clave + "'";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		Log.i(Util.APP, "REGISTROS: " + String.valueOf(cursor.getCount()));
		if (cursor.moveToFirst()) {
			this.usuario = new Usuario();
			this.usuario.setIdUsuario(cursor.getInt(0));
			this.usuario.setUsuario(cursor.getString(1));
			this.usuario.setClave(cursor.getString(1));
		} else {
			return null;
		}
		cursor.close();

		return this.usuario;
	}
	
	public boolean autorizarUsuario(String usuario, String clave) {
		String selectQuery = "select idUsuario from usuario where usuario='"
				+ usuario + "'" + " and clave='" + clave + "'";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		Log.i(Util.APP, "REGISTROS: " + String.valueOf(cursor.getCount()));
		if (cursor.moveToFirst()) {

			if (cursor.getInt(0) > 0) {
				return true;
			}

		} else {
			return false;
		}
		cursor.close();
		return false;
	}

}

