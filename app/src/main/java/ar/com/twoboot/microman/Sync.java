package ar.com.twoboot.microman;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import ar.com.twoboot.microman.R.id;
import ar.com.twoboot.microman.dominio.OnRuta;
import ar.com.twoboot.microman.util.Util;

public class Sync extends Activity {
	private ListView lvSync;
	EditText input;	
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Sincronizacion");
		setContentView(R.layout.activity_sync);
		context= this; 
		lvSync = (ListView) findViewById(id.lvSync);
		lvSync.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				String item = (String) lvSync.getAdapter().getItem(position);
				if (item.equals("Exportar")) {
					Intent intent = new Intent(getApplicationContext(),
							ActExportarRutas.class);
					startActivity(intent);
				} else if (item.equals("Importar")) {
					Intent intent = new Intent(getApplicationContext(),
							ActImportarTurnosRutas.class);
					startActivity(intent);
				} else if (item.equals("Importar Repasos")) {
					Intent intent = new Intent(getApplicationContext(),
							ActImportarTurnosRepasos.class);
					startActivity(intent);
				
				} else if (item.equals("Reset DB Local")) {
					AlertDialog.Builder adRuta = new AlertDialog.Builder(view
							.getContext());
					adRuta.setTitle("Eliminacion Total de Rutas");
					adRuta.setMessage("Desea Continuar?");
					input = new EditText(getApplicationContext());
					input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
					input.setTransformationMethod(PasswordTransformationMethod
							.getInstance());
					adRuta.setView(input);
					adRuta.setPositiveButton("Si",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0,
										int arg1) {
									String pass = input.getText().toString();
									if (Util.validarPasswordAuditor(pass,
											MicroMan.mTrans)) {
										OnRuta oRuta = new OnRuta(
												MicroMan.mTrans);
										
										oRuta.eliminarTodas(context);
									}else{
										Toast.makeText(context, "Error en Pass", Toast.LENGTH_SHORT).show();
									}
								}
							});
					adRuta.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									// do something when the Cancel button is
									// clicked
								}
							});
					adRuta.show();

				}

			}
		});

		// Show the Up button in the action bar.
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sync, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		}
		return super.onOptionsItemSelected(item);
	}

}
