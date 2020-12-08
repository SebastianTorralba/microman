package ar.com.twoboot.microman.dominio;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import ar.com.twoboot.microman.objetos.Hito;
import ar.com.twoboot.microman.objetos.LecturaAnterior;
import ar.com.twoboot.microman.util.Util;

public class OnLecturaAnterior extends OnNegocio implements Transaccionable {

	public OnLecturaAnterior(Transaccion paramTransaccion) {
		super(paramTransaccion);
	}

	private LecturaAnterior lecturaAnterior;

	public LecturaAnterior getLecturaAnterior() {
		return lecturaAnterior;
	}

	public void setLecturaAnterior(LecturaAnterior lecturaAnterior) {
		this.lecturaAnterior = lecturaAnterior;
	}

	@Override
	public int actualizar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("idCliente", lecturaAnterior.getCliente()
					.getIdCliente());
			valores.put("idMedidor", lecturaAnterior.getMedidor()
					.getIdMedidor());
			valores.put("secuencial", lecturaAnterior.getSecuencial());

			valores.put("fecha", Util.fomtaearFecha(lecturaAnterior.getFecha()));
			valores.put("consumo", lecturaAnterior.getConsumo());
			valores.put("lectura", lecturaAnterior.getLectura());

			whereString = "idMedidor='"
					+ lecturaAnterior.getMedidor().getIdMedidor() + "' "
					+ " and idCliente="
					+ lecturaAnterior.getCliente().getIdCliente()
					+ " and secuencial" + lecturaAnterior.getSecuencial();
			rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores,
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
	public int eliminar() {
		long rtn = 0;
		try {
			whereString = "idMedidor='"
					+ lecturaAnterior.getMedidor().getIdMedidor() + "' "
					+ " and idCliente="
					+ lecturaAnterior.getCliente().getIdCliente()
					+ " and secuencial" + lecturaAnterior.getSecuencial();
			rtn = getTransaccion().baseDatos.delete(getNombreTabla(),
					whereString, null);
			if (rtn > 0) {
				Log.i("MicroMan", "BORRANDO " + rtn);
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
	public int insertar() {
		long rtn = 0;
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("unidad", lecturaAnterior.getCliente().getIdCliente());
			valores.put("idMedidor", lecturaAnterior.getMedidor()
					.getIdMedidor());
			valores.put("secuencial", lecturaAnterior.getSecuencial());
			valores.put("fecha", Util.fomtaearFecha(lecturaAnterior.getFecha()));
			valores.put("consumo", lecturaAnterior.getConsumo());
			valores.put("lectura", lecturaAnterior.getLectura());
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
		return 0;
	}

	public LecturaAnterior extraer(Hito hito, Integer secuencial) {
		String select = "select * from lecturasAnteriores where idCliente=?"
				+" and idMedidor=? and secuencial=?";

		String[] args=new String[3];
		args[0]=hito.getCliente().getIdCliente().toString();
		args[1]=hito.getMedidor().getIdMedidor().toString();
		args[2]=secuencial.toString();
		
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, args);
		if (localCursor.moveToFirst()) {
			lecturaAnterior = new LecturaAnterior();
			lecturaAnterior.setCliente(hito.getCliente());
			lecturaAnterior.setMedidor(hito.getMedidor());
			valores.put("secuencial", lecturaAnterior.getSecuencial());
			lecturaAnterior.setFecha(Util.fomtaearFecha(localCursor
					.getString(2)));
			lecturaAnterior.setLectura(quitarNull(localCursor.getInt(6)));
			lecturaAnterior.setConsumo(quitarNull(localCursor.getInt(5)));
		}
		localCursor.close();

		return lecturaAnterior;
	}

	public ArrayList<LecturaAnterior> extraer(Hito hito) {
		ArrayList<LecturaAnterior> lecturas = new ArrayList<LecturaAnterior>();
		String select = "select * from lecturasAnteriores where idCliente=?"
				+" and idMedidor=? order by fecha asc";

		String[] args=new String[2];
		args[0]=hito.getCliente().getIdCliente().toString();
		args[1]=hito.getMedidor().getIdMedidor().toString();
			
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, args);
		Log.i("MicroMan",select);
		Log.i("MicroMan",args[0]);
		Log.i("MicroMan",args[1]);
		
		Log.i("MicroMan", String.valueOf( localCursor.getCount()));
		
		if (localCursor.moveToFirst()) {
			do {
				LecturaAnterior la = new LecturaAnterior();
				la.setCliente(hito.getCliente());
				la.setMedidor(hito.getMedidor());

				la.setSecuencial(localCursor.getInt(3));
				la.setFecha(Util.fomtaearFecha(localCursor.getString(4)));
				la.setLectura(quitarNull(localCursor.getInt(5)));
				la.setConsumo(quitarNull(localCursor.getInt(6)));
				la.setHito(hito);
				lecturas.add(la);
			} while (localCursor.moveToNext());
		}
		localCursor.close();
		return lecturas;
	}

}
