package ar.com.twoboot.microman.items;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.TextView;
import ar.com.twoboot.microman.R;
import ar.com.twoboot.microman.R.id;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.objetos.Hito;

public class ItemHito extends ItemListAdapter {
	private TextView tvIdMedidor;
	private TextView tvDomicilio;
	public ItemHito(Activity paramActivity, ArrayList paramArrayList) {
		super(paramActivity, paramArrayList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void inicializarVistaItem(DalusObject paramDalusObject) {
		this.vi = ((LayoutInflater) this.activity
				.getSystemService("layout_inflater")).inflate(
				R.layout.item_hito, null);
		Hito hito = (Hito) paramDalusObject;
		tvIdMedidor=(TextView) vi.findViewById(id.tvIdMedidor);
		tvIdMedidor=(TextView) vi.findViewById(id.tvIdMedidor);
		tvDomicilio.setText(hito.getDomicilio().toString());
		tvIdMedidor.setText(hito.getMedidor().getIdMedidor().toString());
	}

}
