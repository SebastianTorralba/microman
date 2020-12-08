package ar.com.twoboot.microman.dominio;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import ar.com.twoboot.microman.objetos.Medidor;
import ar.com.twoboot.microman.util.Util;

public class OnMedidor extends OnNegocio implements Transaccionable {

	public OnMedidor(Transaccion paramTransaccion) {
		super(paramTransaccion);
		// TODO Auto-generated constructor stub
	}

	private Medidor medidor;

	public Medidor getMedidor() {
		return medidor;
	}
	public void setMedidor(Medidor medidor) {
		this.medidor = medidor;
	}
	public Medidor extraer(String idMedidor, String modelo) {
		String select = "select * from Medidor where idMedidor=?"
				+ " and modelo=?";

		String[] args=new String[2];
		args[0]=idMedidor;
		args[1]=modelo;
		
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, args);
		if (localCursor.moveToFirst()) {
			medidor = new Medidor();
			medidor.setIdMedidor(quitarNull(localCursor.getString(0)));
			medidor.setModelo(quitarNull(localCursor.getString(1)));
		}
		localCursor.close();
		return medidor;
	}
	public boolean existe(String idMedidor, String modelo) {
		boolean existe=false;		
		String select = "select count(*) from Medidor where idMedidor=?"
				+ " and modelo=?";

		String[] args=new String[2];
		args[0]=idMedidor;
		args[1]=modelo;
		
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, args);
		if (localCursor.moveToFirst()) {
			int i=localCursor.getInt(0);
			if(i>0){
				existe=true;
			}else{
				existe=false;
			}
	}
		localCursor.close();
		return existe;
	}

	@Override
	public int actualizar() {
		long rtn = 0;
		try {
			valores.put("fechaInstalacion",
					Util.fomtaearFecha(medidor.getFechaInstalacion()));

			whereString = "idMedidor='" + medidor.getIdMedidor()
					+ "' and modelo='" + medidor.getModelo() + "'";
			;

			rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores,
					whereString, null);
			if (rtn > 0) {
				Log.i("MicroMan", "ACTUALIZANDO " + rtn);
			} else {
				// getTransaccion().baseDatos.
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			Log.e("MicroMan", e.getMessage());
		}
		return (int) rtn;

	}

	@Override
	public int eliminar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			whereString = "idMedidor='" + medidor.getIdMedidor()
					+ "' and modelo='" + medidor.getModelo() + "'";

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
			valores.put("idMedidor", medidor.getIdMedidor());
			valores.put("modelo", medidor.getModelo());
			valores.put("fechaInstalacion",
					Util.fomtaearFecha(medidor.getFechaInstalacion()));

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
