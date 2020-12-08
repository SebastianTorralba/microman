package ar.com.twoboot.microman.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.database.Cursor;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.objetos.DalusObject;

public final class Util {
	public final static String DEFAULT_PATH_DB = "/mnt/sdcard";
	public final static long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
	public final static String VERSION_MICROMAN="3.00.00";
	public static final String APP = "MicroMan Aguas";
	public static final String fomtaearFecha(Date fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechastring = sdf.format(fecha);
		return fechastring;
	}

	public static final Date fomtaearFecha(String fecha) {
		Date fechaDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			fechaDate = sdf.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fechaDate;
	}	public static final long calcularDias(java.util.Date fechaAnterior,java.util.Date fechaPosterior){
		long diferencia;
		diferencia= ( fechaPosterior.getTime() - fechaAnterior.getTime() )/ MILLSECS_PER_DAY;
		return diferencia;
	}
	public static final int posicionEnSpinner(Spinner sp, DalusObject o) {
		ArrayAdapter ags = (ArrayAdapter) sp.getAdapter();
		Log.i("MicroMan SPINNER", o.toString());
		int pos = ags.getPosition(o);
		Log.i("MicroMan  SPINNER", "Posicion: " + pos);
		sp.setSelection(pos);
		return pos;
		// sAgenteSanitario.setSelection(pos);
	}

	public static final int posicionEnSpinner(Spinner sp, Object o) {
		ArrayAdapter ags = (ArrayAdapter) sp.getAdapter();
		Log.i("DALUS SPINNER", o.toString());
		int pos = ags.getPosition(o);
		Log.i("DALUS SPINNER", "Posicion: " + pos);
		sp.setSelection(pos);
		return pos;
	}
	public static final int posicionEnViewPager(ViewPager vp, Object o) {
		 PagerAdapter ags = vp.getAdapter();
		Log.i("DALUS PAGER", o.toString());
		int pos = ags.getItemPosition(o);
		Log.i("DALUS PAGER", "Posicion: " + pos);
		vp.setCurrentItem(pos, true);
		return pos;
	}
	public static final int posicionEnViewPager(ViewPager vp, int pos) {
		 PagerAdapter ags = vp.getAdapter();
		vp.setCurrentItem(pos, true);
		return pos;
	}

	public static final Integer quitarNull(Integer dato) {
		if (dato == null) {
			dato = 0;
		}
		return dato;
	}

	public static final String quitarNull(String dato) {
		if (dato == null) {
			dato = "";
		}
		return dato;
	}

	public static final Double quitarNull(Double dato) {
		if (dato == null) {
			dato = 0.0;
		}
		return dato;
	}

	public static final Date quitarNull(Date dato) {
		if (dato == null) {
			dato = new Date(1900, 1, 1);
		}
		return dato;
	}

	public static java.sql.Date dateToSqlDate(java.util.Date fecha) {

		java.sql.Date fechaSql;
		if (fecha != null) {
			fechaSql = new java.sql.Date(fecha.getTime());
		} else {
			return null;
		}
		return fechaSql;
	}
	public static java.sql.Timestamp dateToTimestamp(java.util.Date fecha) {

		java.sql.Timestamp fechaSql;
		if (fecha != null) {
			fechaSql = new Timestamp(fecha.getTime());
		} else {
			return null;
		}
		return fechaSql;
	}
	public static Boolean validarPasswordAuditor(String pass,Transaccion trans){
		String selectQuery = "select count(*) from configuracion where password='" + pass + "'";
		Cursor cursor = trans.baseDatos.rawQuery(selectQuery, null);
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
	
	public static String convertirAGms(Double valor, int tipo) {
		Double grados = (double) Math.abs(valor.intValue());
		Double minutos = (Math.abs(valor) - grados) * 60;
		Double segundos = minutos;
		minutos = (double) Math.abs(minutos.intValue());
		segundos = (double) (Math.round((segundos - minutos) * 60 * 1000000) / 1000000);
		Integer signo = (valor < 0) ? -1 : 1;
		char direccion = (tipo == GpsLog.LATITUD) ? ((signo > 0) ? 'N' : 'S')
				: ((signo > 0) ? 'E' : 'W');

		grados = grados * signo;
		return grados + " " + minutos + "' " + segundos + "\"";
	}
	
}