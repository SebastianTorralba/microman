package ar.com.twoboot.microman;

import java.util.ArrayList;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import ar.com.twoboot.microman.R.id;
import ar.com.twoboot.microman.dialogabm.DialogLogin;
import ar.com.twoboot.microman.dominio.OnConfig;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.OnRuta;
import ar.com.twoboot.microman.dominio.OnSistema;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.dominio.sincronizacion.Config;

import ar.com.twoboot.microman.dominio.sincronizacion.Sincronizador;
import ar.com.twoboot.microman.dominio.sincronizacion.SyncCorreoNotificacion;
import ar.com.twoboot.microman.dominio.sincronizacion.SyncSistema;
import ar.com.twoboot.microman.objetos.Ruta;
import ar.com.twoboot.microman.objetos.Usuario;
import ar.com.twoboot.microman.objetos.configuracion.Sistema;

@SuppressLint("NewApi")
public class MicroMan extends FragmentActivity implements ISync {
	public static final String PARAM_COD_RUTA_ACTIVA = "codigo_ruta_activa";
	public static final String PARAM_TIPO_RUTA_ACTIVA = "tipo_ruta_activa";

	public static final String PARAM_RUTA_ACTIVA = "ruta_activa";
	public static final String PARAM_CLIENTE = "cliente_activo";
	public static final String PARAM_MEDIDOR = "medidor_activo";
	public static final String PARAM_HITO = "hito_activo";
	public static final String PARAM_USUARIO = "usuario_activo";
	public static final String PARAM_ESTADO_HITO = "estado_hito";

	private SyncSistema syncSistema;
	private SyncCorreoNotificacion syncCorreoNotificacion;
	private ArrayList<Sincronizador> sincronizadores = new ArrayList<Sincronizador>();

	private Context contextDialog = null;
	private ISync sincro;
	private ListView list;
	private TextView tvCodRuta;
	private TextView tvBaseDatos;
	private ProgressBar pbAvance;
	private TextView tvAvance;
	private ProgressBar pbTransmitidos;
	private TextView tvTransmitidos;
	private Iterator<Sincronizador> itSincro;
	private AlertDialog adMensaje;
	private Sincronizador sincronizadorActual;
	private boolean resultadoAdSincronizacion;
	public static Transaccion mTrans = new Transaccion();
	public static Ruta rutaActiva;
	public static Config configuracion;
	public static String imei = "";
	public static Sistema sistema;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		checkRutaActiva();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		if (savedInstanceState != null) {
			setRutaActiva(((Ruta) savedInstanceState
					.getSerializable(PARAM_RUTA_ACTIVA)));
			checkRutaActiva();
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable(PARAM_RUTA_ACTIVA, rutaActiva);
	}

	public static Ruta getRutaActiva() {
		return rutaActiva;
	}

	public static void setRutaActiva(Ruta rutaActiva) {
		MicroMan.rutaActiva = rutaActiva;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_microman);
		TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		imei = mngr.getDeviceId();
		mTrans.conectarDB();
		configuracion = new OnConfig(mTrans).extraer();

		sistema = new OnSistema(mTrans).extraer();
		login();
		if (configuracion.getRuta() != null) {
			rutaActiva = configuracion.getRuta();
		}
		tvBaseDatos = (TextView) findViewById(id.tvBaseDatos);

		if (configuracion.getBaseDatos() != null) {
			tvBaseDatos.setText(configuracion.getBaseDatos());
		}
		sincro = this;
		contextDialog = this;

		list = (ListView) findViewById(id.list);
		tvCodRuta = (TextView) findViewById(id.tvCodRuta);
		tvCodRuta.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				AlertDialog.Builder adRuta = new AlertDialog.Builder(view
						.getContext());
				adRuta.setTitle("Ruta Activa");
				adRuta.setMessage("Desea Continuar Con Esta Ruta?");
				adRuta.setPositiveButton("Si",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								Intent intent = new Intent(
										getApplicationContext(), ActHitos.class);
								intent.putExtra(MicroMan.PARAM_RUTA_ACTIVA,
										rutaActiva);
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
		tvAvance = (TextView) findViewById(id.tvAvance);
		pbAvance = (ProgressBar) findViewById(id.pbAvance);
		tvTransmitidos = (TextView) findViewById(id.tvTransmitidos);
		pbTransmitidos = (ProgressBar) findViewById(id.pbTransmitidos);

		checkRutaActiva();
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				String item = (String) list.getAdapter().getItem(position);
				if (item.equals("Rutas")) {
					Intent intent = new Intent(getApplicationContext(),
							ActRutas.class);
					intent.putExtra(PARAM_RUTA_ACTIVA, rutaActiva);
					startActivity(intent);
				} else if (item.equals("Listado")) {
					Intent intent = new Intent(getApplicationContext(),
							CoderLogReader.class);
					startActivity(intent);

				} else if (item.equals("Sincronizacion")) {
					Intent intent = new Intent(getApplicationContext(),
							Sync.class);
					startActivity(intent);
				}

			}
		});
		syncSistema = new SyncSistema(configuracion, contextDialog);
		syncSistema.setmObjetoSync(sincro);
		syncSistema.setSistema(sistema);
		syncSistema.setmTrans(MicroMan.mTrans);
		sincronizadores.add(syncSistema);
		syncCorreoNotificacion = new SyncCorreoNotificacion(configuracion,
				contextDialog);
		syncCorreoNotificacion.setmObjetoSync(sincro);
		syncCorreoNotificacion.setmTrans(MicroMan.mTrans);
		sincronizadores.add(syncCorreoNotificacion);
		itSincro = sincronizadores.iterator();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.micro_man, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_settings) {
			Intent intent = new Intent(getApplicationContext(),
					ActConfiguracion.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	private void checkRutaActiva() {
		String avance;
		if (rutaActiva == null) {
			tvCodRuta.setText("Sin Seleccionar");
			avance = "0/0";
			tvAvance.setText(avance);
			tvTransmitidos.setText(avance);
		} else {
			OnRuta oRuta = new OnRuta(mTrans);
			oRuta.setRuta(rutaActiva);
			tvCodRuta.setText(rutaActiva.toString());
			pbAvance.setMax(rutaActiva.getCantidadClientes());
			pbAvance.setProgress(oRuta.hitosVisitados());
			avance = oRuta.hitosVisitados() + "/"
					+ rutaActiva.getCantidadClientes();
			tvAvance.setText(avance);
			pbTransmitidos.setMax(rutaActiva.getCantidadClientes());
			pbTransmitidos.setProgress(oRuta.hitosTransmitidos());
			avance = oRuta.hitosTransmitidos() + "/"
					+ rutaActiva.getCantidadClientes();
			tvTransmitidos.setText(avance);

		}
	}

	public void actualizar() {
		configuracion = new OnConfig(mTrans).extraer();
		if (configuracion.getRuta() != null) {
			rutaActiva = configuracion.getRuta();
		}
		if (configuracion.getBaseDatos() != null) {
			tvBaseDatos.setText(configuracion.getBaseDatos());
		}
		ejecutarSync();
	}

	private void login() {
		FragmentManager fm = getSupportFragmentManager();
		DialogLogin dlgLogin = new DialogLogin(OnNegocio.ACCION_MODIFICAR,
				new Usuario(), R.layout.dlg_login);
		dlgLogin.setRetainInstance(true);
		dlgLogin.setActividadLlamada(this);
		dlgLogin.show(fm, "Login");
	}

	@Override
	public void ejecutarSync() {
		 resultadoAdSincronizacion = false;
		AlertDialog.Builder adSicronizacion = new AlertDialog.Builder(this);
		adSicronizacion.setTitle("Esta Seguro");
		adSicronizacion.setMessage("Desea Cargar la Configuracion desde el Servidor");
		adSicronizacion.setCancelable(true);
		adSicronizacion.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				resultadoAdSincronizacion=true;
			}
			
		});
		adSicronizacion.setNegativeButton("No",  new DialogInterface.OnClickListener() {		
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				resultadoAdSincronizacion=false;
			}
			
		});
		if(resultadoAdSincronizacion){
		try {
			sincronizadorActual = (Sincronizador) itSincro.next();
			sincronizadorActual.execute(Sincronizador.OP_IMPORTAR);

		} catch (Exception e) {

		}}

	}

	@Override
	public void ejecucionCorrecta() {
		adMensaje = new AlertDialog.Builder(this).create();
		adMensaje.setMessage("Descargada");
		adMensaje.setCancelable(true);
		adMensaje.show();
		adMensaje.dismiss();
		ejecutarSync();
		if (sincronizadorActual instanceof SyncSistema) {
			sistema = new OnSistema(mTrans).extraer();
		}

	}

	@Override
	public void ejecucionInCorrecta() {
		adMensaje = new AlertDialog.Builder(this).create();
		adMensaje.setMessage("Error al Descargar");
		adMensaje.setCancelable(true);
		adMensaje.show();
		adMensaje.dismiss();

	}
}
