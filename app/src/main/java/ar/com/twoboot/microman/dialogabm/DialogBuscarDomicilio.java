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
public class DialogBuscarDomicilio extends DialogBaseAbm {

	Ruta ruta;
	private AutoCompleteTextView actvDomicilio;
	MicroManArrayAdapter<String> adapter;
	public DialogBuscarDomicilio(int accion, DalusObject objeto, int layout) {
		super(accion, objeto, layout);
		titulo = "Buscar Domicilio";
		ruta = (Ruta) objeto;
	}

	@Override
	protected void inicializacion() {
		actvDomicilio = (AutoCompleteTextView) rootView
				.findViewById(R.id.actvDomicilio);
		if (ruta != null) {
			adapter = new MicroManArrayAdapter<String>(
					getActivity(), android.R.layout.select_dialog_item,
					ruta.extraerDomicilio());
			actvDomicilio.setThreshold(1);
			actvDomicilio.setAdapter(adapter);
			actvDomicilio.addTextChangedListener(new TextWatcher() {

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
		String UnidadPersona = actvDomicilio.getText().toString();
		int pos = ruta.extraerDomicilio().indexOf(UnidadPersona);
		if (pos >= 0) {
			act.irAHito(pos);
			return true;
		}
		return false;
	}


}
