package ar.com.twoboot.microman;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import ar.com.twoboot.microman.R.id;
import ar.com.twoboot.microman.objetos.Hito;
import ar.com.twoboot.microman.objetos.Ruta;

public class ActBuscarMedidores extends Activity {
	private AutoCompleteTextView actvIdMedidor;
	private Ruta ruta;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_medidores);
		ruta=MicroMan.getRutaActiva();
		actvIdMedidor=(AutoCompleteTextView) findViewById(id.actvMedidor);
		if(ruta!=null){
			  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		                android.R.layout.select_dialog_item, ruta.extraerMedidores());
			  actvIdMedidor.setThreshold(1);
			  actvIdMedidor.setAdapter(adapter);
		}
		actvIdMedidor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buscar_medidores, menu);
		return true;
	}
	public ArrayList<Hito> buscarIdMedidor(String idMedidor){
		ArrayList<Hito> hitos = new ArrayList<Hito>();
		for (Iterator iterator = ruta.getIteratorHito(); iterator.hasNext();) {
			Hito hito=(Hito) iterator.next();
			if(hito.getMedidor().getIdMedidor().startsWith(idMedidor)){
				hitos.add(hito);
			}
		}
			
		return hitos;
	}
	
}
