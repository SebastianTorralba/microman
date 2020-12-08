package ar.com.twoboot.microman;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import ar.com.twoboot.microman.R.id;
import ar.com.twoboot.microman.dominio.OnConfig;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.sincronizacion.Config;
import ar.com.twoboot.microman.dominio.sincronizacion.Sincronizador;
import ar.com.twoboot.microman.util.Util;

public class ActConfiguracion extends Activity {
	private EditText etDbServerUrl;
	private EditText etDbServerInstancia;
	private Button bTestConexion;
	private Button bGuardar;
	private Transaccion mTrans;
	private Config config;
	private OnConfig onConfig;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTrans = MicroMan.mTrans;

		setContentView(R.layout.activity_configuracion);
		etDbServerUrl = (EditText) findViewById(id.etDbServerUrl);
		etDbServerInstancia = (EditText) findViewById(id.etDbServerInstancia);
		bTestConexion = (Button) findViewById(id.bTestConexion);
		bGuardar = (Button) findViewById(id.bGuardar);
		inicializar();
		bTestConexion.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Sincronizador dbServer = new Sincronizador(config, null);
				dbServer.execute(Sincronizador.OP_TESTCONEXION);

			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.configuracion, menu);
		return true;
	}

	public void inicializar() {
		int rtn = 0;
		onConfig = new OnConfig(mTrans);
		if (rtn == 0) {
			config = onConfig.extraer();
			if (config != null) {
				etDbServerUrl.setText(config.getDbServerUrl());
				etDbServerInstancia.setText(config.getDbServerInstancia());

			} else {
				Log.e(Util.APP, "Error al Extraer el Objeto");
			}
		}
	}

	public void consolidar() {
		config.setDbServerUrl(etDbServerUrl.getText().toString());
		config.setDbServerInstancia(etDbServerUrl.getText().toString());
	}

}
