package ar.com.twoboot.microman.dominio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import ar.com.twoboot.microman.dominio.sincronizacion.Config;
import ar.com.twoboot.microman.util.Util;

public class OnConfig extends OnNegocio implements Transaccionable {
	private Config configuracion;

	public OnConfig(Transaccion paramTransaccion) {
		super(paramTransaccion);
		setNombreTabla("configuracion");
		valores = new ContentValues();
	}

	/**
	 * @return the configuracion
	 */
	public final Config getConfiguracion() {
		return configuracion;
	}

	/**
	 * @param configuracion
	 *            the configuracion to set
	 */
	public final void setConfiguracion(Config configuracion) {
		this.configuracion = configuracion;
	}

	public Config extraer() {
		configuracion = new Config();

		String selectQuery = "select * from configuracion";
		Cursor cursor = getTransaccion().baseDatos.rawQuery(selectQuery, null);
		Log.i(Util.APP, "REGISTROS: " + String.valueOf(cursor.getCount()));
		if (cursor.moveToFirst()) {
			configuracion.setPassword(cursor.getString(2));
			configuracion.setDbServerUrl(cursor.getString(3));
			configuracion.setDbServerInstancia(cursor.getString(4));
			configuracion.setFtpUrl(cursor.getString(5));
			configuracion.setBaseDatos(cursor.getString(9));
			
			String codRuta=Util.quitarNull(cursor.getString(6));
			
			if (!codRuta.isEmpty()){
			configuracion.setRuta(new OnRuta(getTransaccion()).extraer(codRuta, null));
			configuracion.setOrden(Util.quitarNull(cursor.getInt(7)));}
		} else {
			cursor.close();
			return null;
		}
		cursor.close();

		return configuracion;

	}

	@Override
	public int actualizar() {
		long rtn = 0;
		valores = new ContentValues();
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("codRuta", configuracion.getRuta().getCodRuta());
			valores.put("orden",configuracion.getOrden());
			whereString = "";
			rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores,
					null, null);
			if (rtn > 0) {
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i(Util.APP, "ACTUALIZANDO " + rtn);
			} else {
				// getTransaccion().baseDatos.
			}
			getTransaccion().baseDatos.endTransaction();

		} catch (SQLException e) {
			// TODO: handle exception
			Log.e(Util.APP, e.getMessage());
		}
		return (int) rtn;
}

	public int actualizarBaseDatos(String baseDatos) {
		long rtn = 0;
		valores = new ContentValues();
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("basedatos", baseDatos);
			whereString = "";
			rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores,
					null, null);
			if (rtn > 0) {
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i(Util.APP, "ACTUALIZANDO BASE DE DATOS " + rtn);
			} else {
				// getTransaccion().baseDatos.
			}
			getTransaccion().baseDatos.endTransaction();

		} catch (SQLException e) {
			// TODO: handle exception
			Log.e(Util.APP, e.getMessage());
		}
		return (int) rtn;
}
	@Override
	public int eliminar() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertar() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int validar() {
		// TODO Auto-generated method stub
		return 0;
	}
}
