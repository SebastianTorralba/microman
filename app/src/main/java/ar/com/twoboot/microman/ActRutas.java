package ar.com.twoboot.microman;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import ar.com.twoboot.microman.R.id;
import ar.com.twoboot.microman.dominio.OnRuta;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.items.ItemRuta;
import ar.com.twoboot.microman.objetos.Ruta;

public class ActRutas extends Activity {
	private Transaccion mTrans;
	private ListView lvRutas;
	private OnRuta oRuta;
	private CheckBox cbCargarNoLeidas;
	private Ruta rutaSeleccionada;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mTrans = new Transaccion();
		mTrans.conectarDB();
		oRuta = new OnRuta(mTrans);
		setContentView(R.layout.activity_rutas);
		cbCargarNoLeidas = (CheckBox) findViewById(id.cbCargarNoLeidas);
		cbCargarNoLeidas.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton view, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					oRuta.setEstadoHitos(Ruta.HITOS_NO_LEIDOS);
				}else{
					oRuta.setEstadoHitos(Ruta.HITOS_TODOS);
				}
				
			}
		});
		lvRutas = (ListView) findViewById(id.lvRutas);
		ArrayList<Ruta> itemsRutas = obtenerRutas();
		lvRutas.setAdapter(new ItemRuta(this, itemsRutas));
		lvRutas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				rutaSeleccionada = (Ruta) lvRutas.getItemAtPosition(position);
				AlertDialog.Builder adRuta = new AlertDialog.Builder(view
						.getContext());
				adRuta.setTitle("Seleccion de Ruta");
				adRuta.setMessage("Desea Utilizar Esta Ruta?");
				adRuta.setPositiveButton("Si",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
//								Ruta ruta = oRuta.extraer(rutaSeleccionada
//										.getCodRuta());
//								MicroMan.setRutaActiva(ruta);
								Intent intent = new Intent(
										getApplicationContext(), ActHitos.class);
//								intent.putExtra(MicroMan.PARAM_RUTA_ACTIVA,
//										ruta);
								intent.putExtra(MicroMan.PARAM_COD_RUTA_ACTIVA,
									rutaSeleccionada.getCodRuta());

								intent.putExtra(MicroMan.PARAM_TIPO_RUTA_ACTIVA,
									rutaSeleccionada.getTipo());
								intent.putExtra(MicroMan.PARAM_ESTADO_HITO, oRuta.getEstadoHitos());
								startActivity(intent);
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
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rutas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		}
		return super.onOptionsItemSelected(item);
	}

	private ArrayList<Ruta> obtenerRutas() {
		ArrayList<Ruta> items = new ArrayList(new OnRuta(mTrans).getListado());
		return items;
	}

}
