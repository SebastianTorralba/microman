package ar.com.twoboot.microman;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import ar.com.twoboot.microman.R.id;
import ar.com.twoboot.microman.dialogabm.DialogBuscarDomicilio;
import ar.com.twoboot.microman.dialogabm.DialogBuscarMedidores;
import ar.com.twoboot.microman.dialogabm.DialogBuscarUnidad;
import ar.com.twoboot.microman.dialogabm.DialogHitoLectura;
import ar.com.twoboot.microman.dominio.OnHito;
import ar.com.twoboot.microman.dominio.OnNegocio;
import ar.com.twoboot.microman.dominio.OnRuta;
import ar.com.twoboot.microman.dominio.Transaccion;
import ar.com.twoboot.microman.objetos.Hito;
import ar.com.twoboot.microman.objetos.Ruta;

public class FrgDomicilios extends Fragment {
	private View rootView;
	private Ruta rutaActiva;
	private Hito hito;
	private OnHito oHito;
	private OnRuta oRuta;
	private Transaccion mTrans = new Transaccion();
	private Spinner spObservacion;
	private TextView tvDomicilio;
	private TextView tvEstado;
	private TextView tvDatosComplementario;
	private TextView tvIdMedidor;
	private TextView tvUnidad;
	private TextView tvRazonSocial;
	private Button bIngresarLectura;
//	private GpsLog gps;
	private int orden = 0;
	private Fragment frg;
	private FragmentActivity actividad;

	private Button bVerMapa;
	private Button bBuscarDomicilio;
	private Button bBuscarMedidor;
	private Button bBuscarUnidad;

	public FragmentActivity getActividad() {
		return actividad;
	}

	public void setActividad(FragmentActivity actividad) {
		this.actividad = actividad;
	}

	private Context context;

	public FrgDomicilios() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	gps = new GpsLog(getActivity());
		if (getArguments().containsKey(MicroMan.PARAM_RUTA_ACTIVA)) {
			rutaActiva = (Ruta) getArguments().getSerializable(
					MicroMan.PARAM_RUTA_ACTIVA);

		}

		if (getArguments().containsKey(MicroMan.PARAM_HITO)) {
			orden = getArguments().getInt(MicroMan.PARAM_HITO);
			hito = rutaActiva.getHito().get(orden);

		}
		context=getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_domicilios, container,
				false);
		mTrans.conectarDB();
		frg = this;
		// Bundle bundle = getIntent().getExtras();
		tvDomicilio = (TextView) rootView.findViewById(id.tvDomicilio);

		tvDatosComplementario = (TextView) rootView
				.findViewById(id.tvDatosComplementarios);
		tvIdMedidor = (TextView) rootView.findViewById(id.tvIdMedidor);
		tvIdMedidor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
		tvUnidad = (TextView) rootView.findViewById(id.tvUnidad);
		tvUnidad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		tvRazonSocial = (TextView) rootView.findViewById(id.tvRazonSocial);
		bIngresarLectura = (Button) rootView.findViewById(id.bIngresarLectura);
		bIngresarLectura.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				FragmentManager fm = getFragmentManager();
				DialogHitoLectura dlgHitoLectura = new DialogHitoLectura(
						OnNegocio.ACCION_MODIFICAR, hito,
						R.layout.dlg_hito_lectura);
				dlgHitoLectura.setRetainInstance(true);
		//		dlgHitoLectura.setGps(gps);
				dlgHitoLectura.setContext(context);
				dlgHitoLectura.setActividadLlamada(actividad);
				dlgHitoLectura.setTargetFragment(frg, 0);
				dlgHitoLectura.show(fm, "Hito Lectura");

			}
		});
		bVerMapa = (Button) rootView.findViewById(id.bVerMapa);
		bVerMapa.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(context,
						MapaActivity.class);
				intent.putExtra(MicroMan.PARAM_RUTA_ACTIVA,
						rutaActiva);
				startActivity(intent);
			}

		});
		bBuscarDomicilio = (Button) rootView.findViewById(id.bBuscarDomicilio);
		bBuscarDomicilio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				FragmentManager fm = getFragmentManager();
				DialogBuscarDomicilio dlgBuscarDomicilio = new DialogBuscarDomicilio(
						OnNegocio.ACCION_MODIFICAR, rutaActiva,
						R.layout.dlg_buscar_domicilio);
				dlgBuscarDomicilio.setRetainInstance(true);
				dlgBuscarDomicilio.setActividadLlamada(actividad);
				dlgBuscarDomicilio.show(fm, "Buscar Domicilio");

			}
		});
		bBuscarMedidor = (Button) rootView.findViewById(id.bBuscarMedidor);
		bBuscarMedidor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				FragmentManager fm = getFragmentManager();

				DialogBuscarMedidores dlgBuscarMedidores = new DialogBuscarMedidores(
						OnNegocio.ACCION_MODIFICAR, rutaActiva,
						R.layout.activity_buscar_medidores);
				dlgBuscarMedidores.setRetainInstance(true);
				dlgBuscarMedidores.setActividadLlamada(actividad);
				dlgBuscarMedidores.show(fm, "Buscar Medidores");

			}
		});
		bBuscarUnidad = (Button) rootView.findViewById(id.bBuscarUnidad);
		bBuscarUnidad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				FragmentManager fm = getFragmentManager();

				DialogBuscarUnidad dlgBuscarUnidad = new DialogBuscarUnidad(
						OnNegocio.ACCION_MODIFICAR, rutaActiva,
						R.layout.dlg_buscar_persona);
				dlgBuscarUnidad.setRetainInstance(true);
				dlgBuscarUnidad.setActividadLlamada(actividad);
				dlgBuscarUnidad.show(fm, "Buscar Unidad");

			}
		});
			tvEstado = (TextView) rootView.findViewById(id.tvEstado);
		cargarHito();

		return rootView;
	}

	private void cargarHito() {

		tvDomicilio.setText(hito.getDomicilio());
		tvDatosComplementario.setText(hito.getDatosComplementario());
		tvIdMedidor.setText(hito.getMedidor().getIdMedidor().toString() + ""
				+ "       SEC[" + hito.getOrden().toString() + "]");
		tvUnidad.setText(hito.getCliente().getIdCliente().toString());
		tvRazonSocial.setText(hito.getCliente().getRazonSocial());
		actualizarEstado();

	}

	public void actualizarEstado() {
		String estado = "";
		if (hito.getEstado().equals(1)) {
			tvIdMedidor.setBackgroundColor(Color.GREEN);
			estado = "Leido";
		} else if (hito.getEstado().equals(2)) {
			tvIdMedidor.setBackgroundColor(Color.BLUE);
			estado = "Transmitido";
		} else {
			estado = "Pendiente";
		}
		tvEstado.setText(estado);
	}

}
