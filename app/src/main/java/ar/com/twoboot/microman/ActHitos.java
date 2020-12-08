package ar.com.twoboot.microman;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import ar.com.twoboot.microman.dialogabm.DialogBuscarDomicilio;
import ar.com.twoboot.microman.dialogabm.DialogBuscarMedidores;
import ar.com.twoboot.microman.dialogabm.DialogBuscarUnidad;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.OnRuta;
import ar.com.twoboot.microman.objetos.Ruta;
import ar.com.twoboot.microman.util.Util;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ActHitos extends AppCompatActivity {
	private static final int DIALOG_BUSCAR_MEDIDOR = 0;
	private Integer paginaSeleccionada = 0;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	private OnRuta oRuta;
	private Ruta rutaActiva;
	private String codRutaActiva;
	private Context context;
	private Integer estadoHitos = 0;
	private String tipoRutaActiva;
	private FragmentActivity actividad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hitos);
		Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

		setSupportActionBar(myToolbar);
		getSupportActionBar().show();
		context = this;
		actividad=this;
		// Show the Up button in the action bar.
		Bundle bundle = getIntent().getExtras();
		rutaActiva = (Ruta) bundle.getSerializable(MicroMan.PARAM_RUTA_ACTIVA);
		if (rutaActiva == null) {
			codRutaActiva = (String) bundle
					.getString(MicroMan.PARAM_COD_RUTA_ACTIVA);

			tipoRutaActiva = (String) bundle
					.getString(MicroMan.PARAM_TIPO_RUTA_ACTIVA);
			estadoHitos = bundle.getInt(MicroMan.PARAM_ESTADO_HITO);
		}
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				paginaSeleccionada = arg0;

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hitos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int pos;
		FragmentManager fm = getSupportFragmentManager();
		if (item.getItemId() == R.id.ver_mapa) {
			Intent intent = new Intent(getApplicationContext(),
					MapaActivity.class);
			intent.putExtra(MicroMan.PARAM_RUTA_ACTIVA,
					rutaActiva);
			startActivity(intent);
		}
		if (item.getItemId() == R.id.menu_buscador_domicilio) {
			DialogBuscarDomicilio dlgBuscarDomicilio = new DialogBuscarDomicilio(
					OnNegocio.ACCION_MODIFICAR, rutaActiva,
					R.layout.dlg_buscar_domicilio);
			dlgBuscarDomicilio.setRetainInstance(true);
			dlgBuscarDomicilio.setActividadLlamada(this);
			dlgBuscarDomicilio.show(fm, "Buscar Domicilio");
		}
		if (item.getItemId() == R.id.menu_buscador_personas) {

			DialogBuscarUnidad dlgBuscarUnidad = new DialogBuscarUnidad(
					OnNegocio.ACCION_MODIFICAR, rutaActiva,
					R.layout.dlg_buscar_persona);
			dlgBuscarUnidad.setRetainInstance(true);
			dlgBuscarUnidad.setActividadLlamada(this);
			dlgBuscarUnidad.show(fm, "Buscar Unidad");
		}
		if (item.getItemId() == R.id.menu_buscador_medidores) {
			DialogBuscarMedidores dlgBuscarMedidores = new DialogBuscarMedidores(
					OnNegocio.ACCION_MODIFICAR, rutaActiva,
					R.layout.activity_buscar_medidores);
			dlgBuscarMedidores.setRetainInstance(true);
			dlgBuscarMedidores.setActividadLlamada(this);
			dlgBuscarMedidores.show(fm, "Buscar Medidores");
		}
		if (item.getItemId() == R.id.menu_buscar_siguiente) {
			pos = rutaActiva.buscarSiguienteSinLectura(paginaSeleccionada);
			if (pos >= 0) {
				irAHito(pos);
			} else {
				Toast.makeText(this, "No hay pendiente de Lectura",
						Toast.LENGTH_SHORT);
			}
		}
		if (item.getItemId() == R.id.menu_buscar_anterior) {
			pos = rutaActiva.buscarAnteriorSinLectura(paginaSeleccionada);
			if (pos >= 0) {
				irAHito(pos);
			} else {
				Toast.makeText(this, "No hay pendiente de Lectura",
						Toast.LENGTH_SHORT);
			}
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {

			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			FrgDomicilios fragment = new FrgDomicilios();
			fragment.setActividad(actividad);
			Bundle args = new Bundle();
			args.putSerializable(MicroMan.PARAM_RUTA_ACTIVA, rutaActiva);
			args.putInt(MicroMan.PARAM_HITO, position);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return rutaActiva.getHito().size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String titulo;
			titulo = "Domicilio Orden [" + position + "]";
			return titulo;
		}

	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public void irAHito(int posicion) {
		Util.posicionEnViewPager(mViewPager, posicion);
	}

	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		if (MicroMan.rutaActiva != null) {
			outState.putSerializable(MicroMan.PARAM_RUTA_ACTIVA,
					MicroMan.rutaActiva);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		if (rutaActiva == null) {
			oRuta = new OnRuta(MicroMan.mTrans);
			oRuta.setContext(this);
			oRuta.setEstadoHitos(estadoHitos);
			rutaActiva = oRuta.extraer(codRutaActiva, tipoRutaActiva);

		}
		MicroMan.setRutaActiva(rutaActiva);
		setTitle(rutaActiva.toString());

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager.setAdapter(mSectionsPagerAdapter);

		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(MicroMan.PARAM_RUTA_ACTIVA)) {
				MicroMan.setRutaActiva((Ruta) savedInstanceState
						.getSerializable(MicroMan.PARAM_RUTA_ACTIVA));
			}
		}

	}

}
