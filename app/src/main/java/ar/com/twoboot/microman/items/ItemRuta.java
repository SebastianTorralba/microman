package ar.com.twoboot.microman.items;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.TextView;
import ar.com.twoboot.microman.R;
import ar.com.twoboot.microman.R.id;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.objetos.Ruta;

public class ItemRuta extends ItemListAdapter {
	private TextView tvcodRuta;
	private TextView tvRutaNombre;
	private TextView tvAvance;
	private TextView tvTipo;
	public ItemRuta(Activity paramActivity, ArrayList paramArrayList) {
		super(paramActivity, paramArrayList);
	}

	public void inicializarVistaItem(DalusObject paramDalusObject) {
		this.vi = ((LayoutInflater) this.activity
				.getSystemService("layout_inflater")).inflate(
				R.layout.item_ruta, null);
		Ruta ruta = (Ruta) paramDalusObject;
		tvcodRuta = (TextView) vi.findViewById(id.tvCodRuta);
		tvRutaNombre = (TextView) vi.findViewById(id.tvRutaNombre);
		tvAvance=(TextView) vi.findViewById(id.tvAvance);
		tvTipo=(TextView) vi.findViewById(id.tvTipo);
		
		try {
			tvcodRuta.setText(ruta.getCodRuta());
			tvRutaNombre.setText(ruta.getRuta());
			String avance = ruta.getHitosVisitados() + "/"
					+ ruta.getCantidadClientes();
			tvAvance.setText(avance);
			if(!ruta.getTipo().equals("R")){
				tvTipo.setText("");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}

/*
 * Location: C:\Documents and Settings\Administrador.TWOBOOT.000\Mis
 * documentos\android\MicroMan_dex2jar.jar Qualified Name:
 * ar.com.twoboot.microman.items.ItemRuta JD-Core Version: 0.6.0
 */