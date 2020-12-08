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
import ar.com.twoboot.microman.dominio.sincronizacion.Sincronizador;
import ar.com.twoboot.microman.dominio.sincronizacion.SyncRutaRepaso;
import ar.com.twoboot.microman.items.ItemTurnoRuta;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.objetos.TurnoRuta;

public class ActImportarTurnosRepasos extends Activity implements
		ar.com.twoboot.microman.ISync {
	private ListView llRutas;
	private ArrayList<DalusObject> itemsTurnoRutas = new ArrayList();
	private SyncRutaRepaso syncRutaRepaso;
	private Boolean importarRuta;
	private ISync sincro;

	private AlertDialog adMensaje;
	private Context contextDialog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_act_turnos_repaso);
		llRutas = (ListView) findViewById(id.llRutas);
		importarRuta = false;
		sincro = this;
		contextDialog=this;
		actualizarListadoRutas();
		llRutas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				TurnoRuta tr;
				ItemTurnoRuta itr = (ItemTurnoRuta) adapter.getAdapter();
				tr = (TurnoRuta) itr.getItem(position);
				importarRuta = true;
				syncRutaRepaso = new SyncRutaRepaso(MicroMan.configuracion, contextDialog);
				syncRutaRepaso.setmObjetoSync(sincro);
				syncRutaRepaso.setmTrans(MicroMan.mTrans);
				syncRutaRepaso.setTurno(tr.getTurno());

				AlertDialog.Builder adRuta = new AlertDialog.Builder(view
						.getContext());
				adRuta.setTitle("Seleccion de Repaso");
				adRuta.setMessage("Desea Importar Este Repaso?");
				adRuta.setPositiveButton("Si",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								syncRutaRepaso.execute(Sincronizador.OP_IMPORTAR);
								
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
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_turnos_rutas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void ejecutarSync() {
		if (importarRuta) {
			adMensaje = new AlertDialog.Builder(this).create();
			adMensaje.setMessage("Repaso Descargado");
			adMensaje.setCancelable(true);
			adMensaje.show();
			importarRuta = false;			
			actualizarListadoRutas();
			
		} else {
			llRutas.setAdapter(new ItemTurnoRuta(this, itemsTurnoRutas));
		}
		adMensaje.dismiss();
	}
	private void actualizarListadoRutas(){
		syncRutaRepaso = new SyncRutaRepaso(MicroMan.configuracion, contextDialog);
		itemsTurnoRutas = new ArrayList();
		syncRutaRepaso.setLista(itemsTurnoRutas);
		syncRutaRepaso.setmObjetoSync(this);
		syncRutaRepaso.execute(Sincronizador.OP_LISTAR);
		adMensaje = new AlertDialog.Builder(this).create();
		adMensaje.setMessage("Cargando Repasos Espere Por Favor");
		adMensaje.setCancelable(false);
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
