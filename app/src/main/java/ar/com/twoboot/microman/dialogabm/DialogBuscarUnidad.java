package ar.com.twoboot.microman.dialogabm;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import ar.com.twoboot.microman.ActHitos;
import ar.com.twoboot.microman.R;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.objetos.Ruta;
import ar.com.twoboot.microman.util.MicroManArrayAdapter;

@SuppressLint("ValidFragment")
public class DialogBuscarUnidad extends DialogBaseAbm {

	Ruta ruta;
	private AutoCompleteTextView actvUnidad;
	MicroManArrayAdapter<String> adapter;
	public DialogBuscarUnidad(int accion, DalusObject objeto, int layout) {
		super(accion, objeto, layout);
		titulo = "Buscar Unidad";
		ruta = (Ruta) objeto;
	}

	@Override
	protected void inicializacion() {
		actvUnidad = (AutoCompleteTextView) rootView
				.findViewById(R.id.actvUnidad);
		if (ruta != null) {
			adapter = new MicroManArrayAdapter<String>(
					getActivity(), android.R.layout.select_dialog_item,
					ruta.extraerPersona());
			actvUnidad.setThreshold(1);
			actvUnidad.setAdapter(adapter);
			actvUnidad.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

					// call your adapter here
					adapter.getFilter().filter(s.toString());

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
				}

				@Override
				public void afterTextChanged(Editable s) {
				}

				});
		}

	}

	@Override
	public boolean validar() {
		ActHitos act = (ActHitos) actividadLlamada;
		String UnidadPersona = actvUnidad.getText().toString();
		int pos = ruta.extraerPersona().indexOf(UnidadPersona);
		if (pos >= 0) {
			act.irAHito(pos);
			return true;
		}
		return false;
	}


}
