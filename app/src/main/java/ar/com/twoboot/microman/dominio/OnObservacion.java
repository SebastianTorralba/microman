package ar.com.twoboot.microman.dominio;

import java.util.ArrayList;

import android.database.Cursor;
import ar.com.twoboot.microman.objetos.Observacion;

public class OnObservacion extends OnNegocio implements Transaccionable {
	private Observacion observacion;

	public OnObservacion(Transaccion paramTransaccion) {
		super(paramTransaccion);
		observacion = new Observacion();
	}

	public int actualizar() {
		return 0;
	}

	public int eliminar() {
		return 0;
	}

	public int insertar() {
		return 0;
	}

	public int validar() {
		return 0;
	}

	public Observacion extraer(String codigo) {
		String select = "select * from observacion where codObservacion='"
				+ codigo + "'";
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, null);
		if (localCursor.moveToFirst()) {
			observacion.setCodObservacion(localCursor.getString(0));
			observacion.setObservacion(localCursor.getString(1));
			observacion.setTipoMedicion(localCursor.getString(2));

		}
		localCursor.close();
		return observacion;
	}

	@Override
	public ArrayList<?> getListado() {
		ArrayList<Observacion> observaciones = new ArrayList<Observacion>();
		String select = "select * from observacion";
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, null);
		if (localCursor.moveToFirst()) {

			do {
				Observacion o = new Observacion();
				o.setCodObservacion(localCursor.getString(0));
				o.setObservacion(localCursor.getString(1));
				o.setTipoMedicion(localCursor.getString(2));
				observaciones.add(o);
			} while (localCursor.moveToNext());

		}
		localCursor.close();
		return observaciones;
	}

}

/*
 * Location: C:\Documents and Settings\Administrador.TWOBOOT.000\Mis
 * documentos\android\MicroMan_dex2jar.jar Qualified Name:
 * ar.com.twoboot.microman.dominio.OnObservacion JD-Core Version: 0.6.0
 */