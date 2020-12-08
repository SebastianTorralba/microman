package ar.com.twoboot.microman;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ar.com.twoboot.microman.R.id;
import ar.com.twoboot.microman.dominio.OnRuta;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.sincronizacion.Sincronizador;
import ar.com.twoboot.microman.dominio.sincronizacion.SyncRutas;
import ar.com.twoboot.microman.items.ItemRuta;
import ar.com.twoboot.microman.objetos.Ruta;
import ar.com.twoboot.microman.util.MailNotificacion;

public class ActExportarRutas extends Activity implements ISync {
	private ListView lvRutas;
	private Ruta ruta;
	private OnRuta oRuta;
	private Transaccion mTrans;
	private SyncRutas syncRutas;
	private ISync sincro;
	private AlertDialog adMensaje;
	private Context contextDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_exportar_rutas);
		mTrans = MicroMan.mTrans;
		contextDialog=this;
		
		oRuta = new OnRuta(mTrans);
		oRuta.setModo(0);
		sincro = this;
		lvRutas = (ListView) findViewById(id.lvRutas);
		ArrayList<Ruta> itemsRutas = obtenerRutas();
		lvRutas.setAdapter(new ItemRuta(this, itemsRutas));
		lvRutas.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ruta = (Ruta) lvRutas.getItemAtPosition(position);
				AlertDialog.Builder adRuta = new AlertDialog.Builder(view
						.getContext());
				adRuta.setTitle("Seleccion de Ruta");
				adRuta.setMessage("Desea Exportar Esta Ruta?");
				adRuta.setPositiveButton("Si",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								syncRutas = new SyncRutas(MicroMan.configuracion, contextDialog);
								syncRutas.setmObjetoSync(sincro);
								syncRutas.setmTrans(mTrans);
								oRuta.setEstadoHitos(Ruta.HITOS_LEIDOS);
								ruta=oRuta.extraer(ruta.getCodRuta(), ruta.getTipo());
								ruta.setHitosVisitados(oRuta.hitosVisitados());
								syncRutas.setRuta(ruta);
								syncRutas.execute(Sincronizador.OP_EXPORTAR);
							}
						});
				adRuta.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								// do something when the Cancel button is
								// clicked
							}
						});
				adRuta.show();

			

			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_exportar_rutas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		}
		return super.onOptionsItemSelected(item);
	}

	private ArrayList<Ruta> obtenerRutas() {
		ArrayList<Ruta> items = new ArrayList(
				oRuta.getListadoRutasTerminadas());
		return items;
	}

	@Override
	public void ejecutarSync() {
		Integer lecturas;
		String estado,avance;
		lecturas=syncRutas.getRegistros();
		estado=syncRutas.getEstadoTransmision();
		avance=syncRutas.getAvance();
		MailNotificacion mailNotifacion = new MailNotificacion(contextDialog,ruta,lecturas,estado,avance);
		adMensaje = new AlertDialog.Builder(this).create();
		adMensaje.setMessage("Ruta Transmitida a Servidor");
		adMensaje.setCancelable(true);
		adMensaje.show();

	}

	@Override
	public void ejecucionCorrecta() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ejecucionInCorrecta() {
		// TODO Auto-generated method stub
		
	}

}
