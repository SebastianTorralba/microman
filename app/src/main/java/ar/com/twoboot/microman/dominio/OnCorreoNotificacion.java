package ar.com.twoboot.microman.dominio;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import ar.com.twoboot.microman.objetos.configuracion.CorreoNotificacion;
import ar.com.twoboot.microman.util.Util;

public class OnCorreoNotificacion extends OnNegocio implements Transaccionable {

	public OnCorreoNotificacion(Transaccion paramTransaccion) {
		super(paramTransaccion);
		setNombreTabla("Correos_Notificacion");
		// TODO Auto-generated constructor stub
	}

	private CorreoNotificacion correoNotificacion;

	public CorreoNotificacion getCorreoNotificacion() {
		return correoNotificacion;
	}

	public void setCorreoNotificacion(CorreoNotificacion correoNotificacion) {
		this.correoNotificacion = correoNotificacion;
	}

	public List<CorreoNotificacion> extraerTodo() {
		ArrayList<CorreoNotificacion> correos = new ArrayList<CorreoNotificacion>();
		String select = "select * from Correos_Notificacion";

		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, null);
		if (localCursor.moveToFirst()) {
			do {
				correoNotificacion = new CorreoNotificacion();
				correoNotificacion.setCorreoElectronico(quitarNull(localCursor
						.getString(0)));
				correoNotificacion.setAlias(Util.quitarNull(localCursor
						.getString(1)));
				correos.add(correoNotificacion);
			} while (localCursor.moveToNext());
		}
		localCursor.close();
		return correos;
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
			valores.put("correo", correoNotificacion.getCorreoElectronico());
			valores.put("alias", correoNotificacion.getAlias());

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
