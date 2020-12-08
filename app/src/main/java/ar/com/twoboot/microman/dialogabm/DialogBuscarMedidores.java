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
public class DialogBuscarMedidores extends DialogBaseAbm {
	Ruta ruta;
	private AutoCompleteTextView actvIdMedidor;
	MicroManArrayAdapter<String> adapter;
	public DialogBuscarMedidores(int accion, DalusObject objeto, int layout) {
		super(accion, objeto, layout);
		titulo = "Buscar Medidores";
		ruta = (Ruta) objeto;
	}

	@Override
	protected void inicializacion() {
		actvIdMedidor = (AutoCompleteTextView) rootView
				.findViewById(R.id.actvMedidor);
		if (ruta != null) {
			adapter = new MicroManArrayAdapter<String>(
					getActivity(), android.R.layout.select_dialog_item,
					ruta.extraerMedidores());
			actvIdMedidor.setThreshold(1);
			actvIdMedidor.setAdapter(adapter);
			actvIdMedidor.addTextChangedListener(new TextWatcher() {

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
		String idMedidor = actvIdMedidor.getText().toString();
		int pos = ruta.extraerMedidores().indexOf(idMedidor);
		if (pos >= 0) {
			act.irAHito(pos);
			return true;
		}
		return false;
	}

}
