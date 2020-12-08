package ar.com.twoboot.microman.dominio;

import java.util.Date;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import ar.com.twoboot.microman.objetos.configuracion.Sistema;
import ar.com.twoboot.microman.util.Util;

public class OnSistema extends OnNegocio implements Transaccionable {

	public OnSistema(Transaccion paramTransaccion) {
		super(paramTransaccion);
		setNombreTabla("Sistema");
		// TODO Auto-generated constructor stub
	}

	private Sistema sistema;

	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema Sistema) {
		this.sistema = Sistema;
	}
	public Sistema extraer() {
		String select = "select * from Sistema ";

		String[] args=new String[2];
		args[0]="";
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, null);
		if (localCursor.moveToFirst()) {
			sistema = new Sistema();
			sistema.setVersion(quitarNull(localCursor.getString(0)));
			sistema.setFechaActual(new Date());
			sistema.setCpm(quitarNull(localCursor.getInt(2)));
			sistema.setConsumoElevado(quitarNull(localCursor.getInt(3)));
			
		}
		localCursor.close();
		return sistema;
	}
	
	@Override
	public int actualizar() {
	  return 0;
	}

	@Override
	public int eliminar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			whereString = "";

			rtn = getTransaccion().baseDatos.delete(getNombreTabla(),
					whereString, null);
			if (rtn > 0) {
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i("MicroMan", "ACTUALIZANDO " + rtn);
			} else {
				// getTransaccion().baseDatos.
			}
			getTransaccion().baseDatos.endTransaction();

		} catch (SQLException e) {
			// TODO: handle exception
			Log.e("MicroMan", e.getMessage());
		}
		return (int) rtn;
	}

	@Override
	public int insertar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("version", sistema.getVersion());
			valores.put("cpm", sistema.getCpm());
			valores.put("promedio_consumo", sistema.getConsumoElevado());
			
			rtn = getTransaccion().baseDatos.insertOrThrow(getNombreTabla(),
					null, valores);
			if (rtn > 0) {
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i("MicroMan", "INSERTANDO " + rtn);
			} else {
				// getTransaccion().baseDatos.
			}
			getTransaccion().baseDatos.endTransaction();

		} catch (SQLException e) {
			// TODO: handle exception
			Log.e("MicroMan", e.getMessage());
		}
		return (int) rtn;

	}

	@Override
	public int validar() {
		// TODO Auto-generated method stub
		return 0;
	}

}
