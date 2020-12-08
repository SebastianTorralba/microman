package ar.com.twoboot.microman.items;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.TextView;
import ar.com.twoboot.microman.R;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.objetos.Hito;

public class ItemMedidor extends ItemListAdapter {
	private TextView tvUnidad;
	private TextView tvIdMedidor;
	private TextView tvRazonSocial;

	public ItemMedidor(Activity paramActivity, ArrayList paramArrayList) {
		super(paramActivity, paramArrayList);
	}

	public void inicializarVistaItem(DalusObject paramDalusObject) {
		this.vi = ((LayoutInflater) this.activity
				.getSystemService("layout_inflater")).inflate(
				R.layout.item_medidor, null);
		Hito localHito = (Hito) paramDalusObject;
		this.tvIdMedidor = ((TextView) this.vi.findViewById(R.id.tvIdMedidor));
		this.tvUnidad = ((TextView) this.vi.findViewById(R.id.tvUnidad));
		this.tvRazonSocial = ((TextView) this.vi
				.findViewById(R.id.tvRazonSocial));
		this.tvIdMedidor.setText(localHito.getMedidor().getIdMedidor()
				.toString());
		this.tvUnidad.setText(localHito.getCliente().getIdCliente().toString());
		this.tvRazonSocial.setText(localHito.getCliente().getRazonSocial());
	}
}

/*
 * Location: C:\Documents and Settings\Administrador.TWOBOOT.000\Mis
 * documentos\android\MicroMan_dex2jar.jar Qualified Name:
 * ar.com.twoboot.microman.items.ItemMedidor JD-Core Version: 0.6.0
 */