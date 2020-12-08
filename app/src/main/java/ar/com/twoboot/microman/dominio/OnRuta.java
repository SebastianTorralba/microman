package ar.com.twoboot.microman.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import ar.com.twoboot.microman.dominio.sincronizacion.Config;
import ar.com.twoboot.microman.objetos.Hito;
import ar.com.twoboot.microman.objetos.Ruta;
import ar.com.twoboot.microman.util.Util;

public class OnRuta extends OnNegocio implements Transaccionable {
	private Integer estadoHitos=Ruta.HITOS_TODOS;
	private Config configuracion;
	private OnConfig onConfig;
	public OnRuta(Transaccion paramTransaccion) {
		super(paramTransaccion);
		setNombreTabla("Ruta");
		configuracion=new Config();
		onConfig=new OnConfig(transaccion);
		
		// TODO Auto-generated constructor stub
	}

	private Ruta ruta = new Ruta();
	private int modo=1;

	public int getModo() {
		return modo;
	}

	public void setModo(int modo) {
		this.modo = modo;
	}

	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	public Ruta extraer(String codRuta, String tipo) {
		String select = "select * from ruta where codruta='" + codRuta + "' and tipo='"+tipo+"'";
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, null);
		Log.i("MicroMan",select);
		Log.i("MicroMan", String.valueOf( localCursor.getCount()));
		if (localCursor.moveToFirst()) {
			this.ruta = new Ruta();
			this.ruta.setCodRuta(quitarNull(localCursor.getString(0)));

			this.ruta.setTipo(quitarNull(localCursor.getString(1)));
			this.ruta.setRuta(quitarNull(localCursor.getString(2)));
			this.ruta.setZona(quitarNull(localCursor.getString(3)));
			this.ruta.setTurno(quitarNull(localCursor.getString(4)));
			this.ruta.setCantidadClientes(hitosCantidad(this.ruta));
			this.ruta.setEstado(quitarNull(localCursor.getInt(6)));
			OnHito oHito=new OnHito(getTransaccion());
			oHito.setContext(this.getContext());
			oHito.setEstadoHitos(estadoHitos);
			oHito.extraer(ruta);
		}
		localCursor.close();
		return this.ruta;
	}

	public Integer hitosVisitados() {
		Integer cantidad=0;
		ArrayList<Ruta> localArrayList = new ArrayList<Ruta>();
		Cursor localCursor;
		if(modo==0){
		localCursor = transaccion.baseDatos.rawQuery(
				"select count(*) from Hito where codRuta='" + ruta.getCodRuta()
						+ "' and tipo='"+ruta.getTipo()
						+ "' and estado=1", null);}
		else{
			localCursor = transaccion.baseDatos.rawQuery(
					"select count(*) from Hito where codRuta='" + ruta.getCodRuta()
					+ "' and tipo='"+ruta.getTipo()		
					+ "' and estado in (1,2)", null);}
				
		if (localCursor.moveToFirst()){
		cantidad = localCursor.getInt(0);
		}
		localCursor.close();

		return cantidad;
	}
	public Integer hitosTransmitidos() {
		Integer cantidad=0;
		ArrayList<Ruta> localArrayList = new ArrayList<Ruta>();
		Cursor localCursor;
		if(modo==0){
		localCursor = transaccion.baseDatos.rawQuery(
				"select count(*) from Hito where codRuta='" + ruta.getCodRuta()
				+ "' and tipo='"+ruta.getTipo()		
				+ "' and estado=2", null);}
		else{
			localCursor = transaccion.baseDatos.rawQuery(
					"select count(*) from Hito where codRuta='" + ruta.getCodRuta()
					+ "' and tipo='"+ruta.getTipo()		
					+ "' and estado in (2)", null);}
				
		if (localCursor.moveToFirst()){
		cantidad = localCursor.getInt(0);
		}
		localCursor.close();

		return cantidad;
	}

	public Integer hitosVisitados(Ruta ruta) {
		Integer cantidad=0;
		ArrayList<Ruta> localArrayList = new ArrayList<Ruta>();
		Cursor localCursor;
		if(modo==0){
		localCursor = transaccion.baseDatos.rawQuery(
				"select count(*) from Hito where codRuta='" + ruta.getCodRuta()
				+ "' and tipo='"+ruta.getTipo()		
				+ "' and estado=1", null);}
		else{
			localCursor = transaccion.baseDatos.rawQuery(
					"select count(*) from Hito where codRuta='" + ruta.getCodRuta()
					+ "' and tipo='"+ruta.getTipo()		
					+ "' and estado in (1,2)", null);}
				
		if (localCursor.moveToFirst()){
		cantidad = localCursor.getInt(0);
		}
		localCursor.close();

		return cantidad;
	}
	public Integer hitostTransmitidos(Ruta ruta) {
		Integer cantidad=0;
		ArrayList<Ruta> localArrayList = new ArrayList<Ruta>();
		Cursor localCursor;
		if(modo==0){
		localCursor = transaccion.baseDatos.rawQuery(
				"select count(*) from Hito where codRuta='" + ruta.getCodRuta()
				+ "' and tipo='"+ruta.getTipo()		
				+ "' and estado=2", null);}
		else{
			localCursor = transaccion.baseDatos.rawQuery(
					"select count(*) from Hito where codRuta='" + ruta.getCodRuta()
					+ "' and tipo='"+ruta.getTipo()
					+ "' and estado in (2)", null);}
				
		if (localCursor.moveToFirst()){
		cantidad = localCursor.getInt(0);
		}
		localCursor.close();

		return cantidad;
	}
	public Integer hitosCantidad(Ruta ruta) {
		Integer cantidad=0;
		ArrayList<Ruta> localArrayList = new ArrayList<Ruta>();
		Cursor localCursor;
		localCursor = transaccion.baseDatos.rawQuery(
				"select count(*) from Hito where codRuta='" + ruta.getCodRuta()
				+ "' and tipo='"+ruta.getTipo()+"'", null);
				
		if (localCursor.moveToFirst()){
		cantidad = localCursor.getInt(0);
		}
		localCursor.close();

		return cantidad;
	}	
	@Override
	public ArrayList<Ruta> getListado() {
		configuracion=onConfig.extraer();

		ArrayList<Ruta> localArrayList = new ArrayList<Ruta>();
		Cursor localCursor = transaccion.baseDatos.rawQuery(
				"select * from ruta where basedatos='"+configuracion.getBaseDatos()+"'", null);
		if (localCursor.moveToFirst())
			do {
				Ruta localRuta = new Ruta();
				localRuta.setCodRuta(localCursor.getString(0));
				localRuta.setTipo(localCursor.getString(1));
				
				localRuta.setRuta(localCursor.getString(2));
				localRuta.setZona(quitarNull(localCursor.getString(3)));
				localRuta.setTurno(quitarNull(localCursor.getString(4)));				
				localRuta.setCantidadClientes(hitosCantidad(localRuta));
				localRuta.setHitosVisitados(hitosVisitados(localRuta));
				
				localArrayList.add(localRuta);
			} while (localCursor.moveToNext());
		localCursor.close();
		return localArrayList;
	}

	public ArrayList<Ruta> getListadoRutasTerminadas() {
		configuracion=onConfig.extraer();


		ArrayList<Ruta> localArrayList = new ArrayList<Ruta>();
		Cursor localCursor = transaccion.baseDatos.rawQuery(
				
				" select * from ruta where basedatos='"+configuracion.getBaseDatos()+"' and exists (select 1 from hito where estado=1 and codRuta=ruta.codRuta and tipo=ruta.tipo)", null);
		if (localCursor.moveToFirst())
			do {
				Ruta localRuta = new Ruta();
				localRuta.setCodRuta(localCursor.getString(0));
				localRuta.setTipo(localCursor.getString(1));
				
				localRuta.setRuta(localCursor.getString(2));
				localRuta.setZona(quitarNull(localCursor.getString(3)));
				localRuta.setTurno(quitarNull(localCursor.getString(4)));				
				localRuta.setCantidadClientes(hitosCantidad(localRuta));
				localRuta.setHitosVisitados(hitosVisitados(localRuta));
				localArrayList.add(localRuta);
			} while (localCursor.moveToNext());
		localCursor.close();
		
		return localArrayList;
	}

	@Override
	public int actualizar() {
		long rtn = 0;
		valores=new ContentValues();
		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("estado", ruta.getEstado());
			valores.put("fechaCarga", Util.fomtaearFecha(ruta.getFechaCarga()));
			valores.put("idUsuario", 1);
			whereString = "codRuta='" + ruta.getCodRuta() + "' and tipo='"+ruta.getTipo()+"'";
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
			getTransaccion().baseDatos.beginTransaction();
			whereString = "turno='" + ruta.getTurno() + "'";
			rtn = getTransaccion().baseDatos.delete(getNombreTabla(),
					whereString, null);
			if (rtn > 0) {	
				OnHito oHito = new OnHito(getTransaccion());
				for (Iterator iterator = ruta.getIteratorHito(); iterator
						.hasNext();) {
					Hito hito = (Hito) iterator.next();
					oHito.setHito(hito);
					oHito.eliminar();
				}
			
				
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i("MicroMan", "BORRANDO " + rtn);
			} else {
				
			}
			getTransaccion().baseDatos.endTransaction();
		} catch (SQLException e) {
			// TODO: handle exception
			Log.e("MicroMan", e.getMessage());
		}
		return (int) rtn;

	}
	public int eliminarTodas(Context context) {
		ProgressDialog progressDialog = new ProgressDialog(context) ;
		long rtn = -1;
		boolean resultado=false;
		try {
			progressDialog = ProgressDialog.show(context, "Espere", "Borrando Rutas...", true, false);
			getTransaccion().baseDatos.beginTransaction();
			whereString = null;
			rtn = getTransaccion().baseDatos.delete("Ruta",
					whereString, null);
			resultado=(rtn > 0) ? true : false;
			rtn = getTransaccion().baseDatos.delete("Hito",
					whereString, null);
			resultado=(rtn > 0) ? true : false;
			rtn = getTransaccion().baseDatos.delete("Cliente",
					whereString, null);
			resultado=(rtn > 0) ? true : false;
			rtn = getTransaccion().baseDatos.delete("Medidor",
					whereString, null);
			resultado=(rtn > 0) ? true : false;
			rtn = getTransaccion().baseDatos.delete("LecturasAnteriores",
					whereString, null);
			resultado=(rtn > 0) ? true : false;
			if (resultado) {		
				getTransaccion().baseDatos.setTransactionSuccessful();
				rtn=0;
				Log.i("MicroMan", "BORRANDO " + rtn);
			} else {
				rtn = -1;
			}
		} catch (SQLException e) {
			// TODO: handle exception
			Log.e("MicroMan", e.getMessage());
		}
		getTransaccion().baseDatos.endTransaction();
		progressDialog.dismiss();
		return (int) rtn;

	}	
	@Override
	public int insertar() {
		long rtn = 0;

		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("codRuta", ruta.getCodRuta());
			valores.put("tipo", ruta.getTipo());
			valores.put("Ruta", ruta.getRuta());
			
			valores.put("zona", ruta.getZona());
			valores.put("turno", ruta.getTurno());
			valores.put("cantidadCliente", ruta.getCantidadClientes());
			valores.put("estado", ruta.getEstado());
			valores.put("fechaCarga", Util.fomtaearFecha(ruta.getFechaCarga()));
			valores.put("idUsuario", ruta.getUsuario().getIdUsuario()
					.toString());

			rtn = getTransaccion().baseDatos.insertOrThrow(getNombreTabla(),
					null, getValores());

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

	public Boolean marcarSiTerminada() {
		Boolean terminada = true;
		for (Iterator iterator = ruta.getIteratorHito(); iterator.hasNext();) {
			Hito hito = (Hito) iterator.next();
			if (hito.getEstado() == 0) {
				return false;
			}
		}
		if (terminada) {
			ruta.setEstado(2);
			ruta.setFechaCarga(new Date());
			actualizar();
		}
		return terminada;
	}

	public Integer getEstadoHitos() {
		return estadoHitos;
	}

	public void setEstadoHitos(Integer estadoHitos) {
		this.estadoHitos = estadoHitos;
	}
	}
