package ar.com.twoboot.microman.dialogabm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.twoboot.microman.FrgDomicilios;
import ar.com.twoboot.microman.MicroMan;
import ar.com.twoboot.microman.R.id;
import ar.com.twoboot.microman.dominio.OnHito;
import ar.com.twoboot.microman.dominio.OnObservacion;
import ar.com.twoboot.microman.dominio.OnRuta;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.objetos.Hito;
import ar.com.twoboot.microman.objetos.Observacion;
import ar.com.twoboot.microman.util.CoderLog;
import ar.com.twoboot.microman.util.GpsCapturable;
import ar.com.twoboot.microman.util.GpsLog;
import ar.com.twoboot.microman.util.ImagenCoDec;
import ar.com.twoboot.microman.util.Util;

@SuppressLint("ValidFragment")
public class DialogHitoLectura extends DialogBaseAbm implements GpsCapturable {
	private Boolean falloCamara = false;
	private Spinner spObservacion;
	private EditText etLecturaActual;
	private EditText etObservaciones;
	private CheckBox cbLecturaCero;
	private Button bGps;
	private TextView tvGpsLatitud;
	private TextView tvGpsLongitud;
	private Button bFoto;
	private int fotonumero = 0;
	private Button bFotoComplementaria;
	private Button bVerFoto1;
	private Button bVerFoto2;
	//private GpsLog gps;
	private Hito hito;
	private Integer consumo = 0;
	private OnHito oHito;
	Boolean resultado;
	Boolean lecturaCero = false;


	public void setGps(GpsLog gps) {
		//this.gps = gps;
	}

	private Bitmap bmFoto;

	private Bitmap bmFoto2;
	private int lecturaActual;
	private Uri outputFileUri;

	public DialogHitoLectura(int accion, DalusObject objeto, int layout) {
		super(accion, objeto, layout);
		hito=(Hito) objeto;
		titulo = "Lectura: " + hito.getMedidor().getIdMedidor();
		oHito = new OnHito(mTrans);
		oHito.setHito(hito);		// TODO Auto-generated constructor stub
	}

	@Override
	protected void inicializacion() {

	//	gps=new GpsLog(context, actividadLlamada);
		spObservacion = (Spinner) rootView.findViewById(id.spObservacion);
		final List<Observacion> observaciones = (List<Observacion>) new OnObservacion(
				mTrans).getListado();
		ArrayAdapter<Observacion> apobervaciones = new ArrayAdapter<Observacion>(
				rootView.getContext(), android.R.layout.simple_spinner_item,
				observaciones);
		apobervaciones
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spObservacion.setAdapter(apobervaciones);
		etLecturaActual = (EditText) rootView.findViewById(id.etLecturaActual);
		etObservaciones = (EditText) rootView.findViewById(id.etObservaciones);
		cbLecturaCero = (CheckBox) rootView.findViewById(id.cbLecturaCero);
		cbLecturaCero.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton view, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					lecturaCero = true;
					Util.posicionEnSpinner(spObservacion, observaciones.get(1));
					etObservaciones.setText("Lectura Declarada en CERO");
					spObservacion.setEnabled(false);
					etObservaciones.setEnabled(false);
					etLecturaActual.setEnabled(false);
				} else {
					Util.posicionEnSpinner(spObservacion, observaciones.get(0));
					etObservaciones.setText("");
					lecturaCero = false;
					spObservacion.setEnabled(true);
					etObservaciones.setEnabled(true);
					etLecturaActual.setEnabled(true);
				}
			}
		});
		etLecturaActual
				.setOnEditorActionListener(new EditText.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO Auto-generated method stub
						if (actionId == EditorInfo.IME_ACTION_SEARCH
								|| actionId == EditorInfo.IME_ACTION_DONE
								|| event.getAction() == KeyEvent.ACTION_DOWN
								&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
							// the user is done typing.
							return true; // consume.

						}
						return false;
					}
				});
		bGps = (Button) rootView.findViewById(id.bGps);
		tvGpsLatitud = (TextView) rootView.findViewById(id.tvGpsLatitud);
		tvGpsLongitud = (TextView) rootView.findViewById(id.tvGpsLongitud);
		bFoto = (Button) rootView.findViewById(id.bFoto);

		bFotoComplementaria = (Button) rootView
				.findViewById(id.bFotoComplementaria);
		bVerFoto1=(Button) rootView.findViewById(id.bVerFoto1);

		bVerFoto2=(Button) rootView.findViewById(id.bVerFoto2);
		bGps.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				coordenadasGps();
			}
		});
		bFoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				fotonumero = 1;
				tomarFoto();
			}
		});
		bFotoComplementaria.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				fotonumero = 2;
				tomarFoto();
			}
		});
		bVerFoto1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (bmFoto != null) {
					mostrarImagen(bmFoto);
				} else {
					mostrarAdvertencia("No hay Foto");
				}
			}
		});
		bVerFoto2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (bmFoto2 != null) {
					mostrarImagen(bmFoto2);
				} else {
					mostrarAdvertencia("No hay Foto");
				}
			}
		});
		coordenadasGps();
	}

	@Override
	protected void popular() {
		if (hito != null) {

			if (hito.getIntentos() == 3) {
				mostrarAdvertenciaToast("Ha llegado al Maximo de Intentos");
				this.dismiss();
			}
			Util.posicionEnSpinner(spObservacion, hito.getObservacion());
			if (!hito.getObservacion().getTipoMedicion()
					.equals(Observacion.CONSUMO_ESTIMADO)) {
				etLecturaActual.setText(hito.getLecturaActual().toString());
			}
			if (hito.getObservacion().getTipoMedicion()
					.equals(Observacion.CONSUMO_PROMEDIO)) {
				etLecturaActual.setText("0");
			}
			
			tvGpsLatitud.setText(hito.getGpslatitud().toString());
			tvGpsLongitud.setText(hito.getGpslongitud().toString());
			oHito.extraerFoto(hito.getRuta(), hito.getOrden());
			if (oHito.getFoto() != null) {
				bmFoto = (Bitmap) ImagenCoDec.getImage(oHito.getFoto());
			}
			oHito.extraerFotoComplementaria(hito.getRuta(), hito.getOrden());
			if (oHito.getFotoComplementaria() != null) {
				bmFoto2 = (Bitmap) ImagenCoDec.getImage(oHito
						.getFotoComplementaria());
			}
			
			if (etLecturaActual.getText().length()>0){
				etLecturaActual.setSelection(0,
						etLecturaActual.getText().length() - 1);
				}
				etLecturaActual.selectAll();
				
		}

	}

	@Override
	public DalusObject consolidar() {
		// TODO Auto-generated method stub
		hito.setObservacion((Observacion) spObservacion.getSelectedItem());
		hito.setLecturaActual(lecturaActual);
		hito.setGpslatitud(Double.valueOf(tvGpsLatitud.getText().toString()));
		hito.setGpslongitud(Double.valueOf(tvGpsLongitud.getText().toString()));
		if (bmFoto != null) {
			// byte[] tempFoto = ImagenCoDec.getBytes(mImageBitmap);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bmFoto.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			try {
				bos.flush();

				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			oHito.setFoto(bos.toByteArray());
		}
		if (bmFoto2 != null) {
			// byte[] tempFoto = ImagenCoDec.getBytes(mImageBitmap);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bmFoto2.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			try {
				bos.flush();

				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			oHito.setFotoComplementaria(bos.toByteArray());
		}

		hito.setObservaciones(etObservaciones.getText().toString());
		return hito;
	}

	@Override
	public boolean validar() {
		Hito hitoValidar = (Hito) hito.clone();

		StringBuilder mensaje=new StringBuilder();
		boolean mostrarMensaje=false;
		lecturaActual = 0;
		boolean tomarFoto = false;
		Observacion obs = (Observacion) spObservacion.getSelectedItem();
		if (obs.getTipoMedicion().equals(Observacion.CONSUMO_NINGUNO)) {
			mostrarAdvertenciaToast("Indique la Observacion de la Medicion");
			return false;
		}

		if (obs.getTipoMedicion().equals(Observacion.CONSUMO_MEDIDO)) {
			lecturaActual = Integer.parseInt(etLecturaActual.getText()
					.toString());
			if (lecturaActual < 0) {
				return false;
			} else {
				if (lecturaCero) {
					etLecturaActual.setText("0");
					hito.setLecturaActual(0);
				}
			}
			if (lecturaActual == 0) {
				if (!lecturaCero) {
					mostrarAdvertenciaToast("Debe Marcar que la Lectura es CERO");
					return false;
				}
			}
			consumo = oHito.calcularConsumo(lecturaActual);
			if (!obs.getCodObservacion().equals("00")) {
				tomarFoto = true;
			}
		
			if (oHito.fueraDeRango(consumo)) {
				mensaje.append("Consumo Fuera de Rango\n");
				hito.setFueraRango("S");
				mostrarMensaje=true;
				tomarFoto = true;
			} else {
				hito.setFueraRango("N");
				tomarFoto = false;
			}
			hito.setTipoLectura("M");
			if (consumo>=MicroMan.sistema.getConsumoElevado()){
				mensaje.append("Consumo Elevado\n");
				tomarFoto = true;
				mostrarMensaje=true;
			}
			if(mostrarMensaje){
				mostrarAdvertencia(mensaje.toString());
			}
			
		}
		if (obs.getTipoMedicion().equals(Observacion.CONSUMO_ESTIMADO)) {
			//consumo=Observacion.ESTIMADO;
			consumo=MicroMan.sistema.getCpm();
			lecturaActual=0;
			tomarFoto = true;
			hito.setTipoLectura("E");
		}
		if (obs.getTipoMedicion().equals(Observacion.CONSUMO_PROMEDIO)) {
			consumo=oHito.calcularPromedioConsumo().intValue();
			lecturaActual = hito.getLecturaAnterior() + consumo;
			tomarFoto = true;
			hito.setTipoLectura("E");
		}
		if (!falloCamara) {
			if (tomarFoto) {
				if (bmFoto == null && bmFoto2 == null) {
					mostrarAdvertenciaToast("Debe tomar Fotografia del Medidor");
					return false;
				}
			}
		} else {
			etObservaciones.setText("Error En Camara");
			return false;
		}
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int grabar(DalusObject objeto) {
		int rtn;
		CoderLog.log(hito, "Grabando", "Inicio");
		hito.setConsumo(consumo);		
		hito.setLecturaActual(lecturaActual);
		hito.setEstado(1);
		hito.setOrdenEfectivo(oHito.calcularOrdenEfectivo());
		hito.setFechaCarga(new Date());
		hito.setIntentos(hito.getIntentos() + 1);
		rtn = oHito.actualizar();
		if (rtn > 0) {
			CoderLog.log(hito, "Grabando", "OK");
			mostrarAdvertenciaToast("Datos Grabados");
		} else {
			CoderLog.log(hito, "Grabando", "Error");
			CoderLog.log(hito, "Grabando", "Consumo:" + hito.getConsumo());
			CoderLog.log(hito, "Grabando", "Codigo:"
					+ hito.getObservacion().getCodObservacion());
			oHito.reset();
			CoderLog.log(hito, oHito.getSqlError());
			mostrarAdvertencia("Error al Grabar: " + oHito.getSqlError());
		}

		OnRuta oRuta = new OnRuta(mTrans);
		oRuta.setRuta(hito.getRuta());
		if (oRuta.marcarSiTerminada()) {
			mostrarAdvertenciaToast("Ruta Terminada");
		}
		FrgDomicilios frg = (FrgDomicilios) getTargetFragment();
		frg.actualizarEstado();
		return 0;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK) {
			File file = getTempFile(getActivity());
			try {
				if (fotonumero == 1) {
					bmFoto = Media.getBitmap(
							getActivity().getContentResolver(),
							Uri.fromFile(file));
					bmFoto = Bitmap.createScaledBitmap(bmFoto, 800, 600, true);
				
				} else {
					bmFoto2 = Media.getBitmap(getActivity()
							.getContentResolver(), Uri.fromFile(file));
					bmFoto2 = Bitmap
							.createScaledBitmap(bmFoto2, 800, 600, true);
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			falloCamara = false;
			CoderLog.log(hito, "Camara", "Fallo");
		} else {
			CoderLog.log(hito, "Camara", "Fallo");
			falloCamara = true;
		}

	}

	private File getTempFile(Context context) {
		// it will return /sdcard/image.tmp
		final File path = new File(Environment.getExternalStorageDirectory(),
				context.getPackageName());
		if (!path.exists()) {
			path.mkdir();
		}

		return new File(path, "image.tmp");
	}

	private void tomarFoto() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		try {
			if(Build.VERSION.SDK_INT>=24){
				Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
				m.invoke(null);
			}
			File file = getTempFile(getActivity());
			outputFileUri = Uri.fromFile(file);
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			startActivityForResult(takePictureIntent, 1);
		} catch (Exception e){
			mostrarAdvertencia(e.getMessage());
		}
	}
	
	public void coordenadasGps() {
		Double latitude = 0d;//gps.getLatitud();
		Double longitude = 0d;//gps.getLongitud();
		if (latitude != null) {
			tvGpsLatitud.setText(latitude.toString());
		} else {
			Toast.makeText(getActivity(), "Intente de Nuevo", Toast.LENGTH_LONG);
		}
		if (longitude != null) {

			tvGpsLongitud.setText(longitude.toString());
		} else {
			Toast.makeText(getActivity(), "Intente de Nuevo", Toast.LENGTH_LONG);
		}

	}
}
