package ar.com.twoboot.microman.items;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.TextView;
import ar.com.twoboot.microman.R;
import ar.com.twoboot.microman.R.id;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.objetos.TurnoRuta;

public class ItemTurnoRuta extends ItemListAdapter {
	private TextView tvCodRuta;
	private TextView tvRuta;
	private TextView tvTurno;
	private TurnoRuta turnoRuta;

	public ItemTurnoRuta(Activity paramActivity, ArrayList paramArrayList) {
		super(paramActivity, paramArrayList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void inicializarVistaItem(DalusObject paramDalusObject) {
		// TODO Auto-generated method stub
		this.vi = ((LayoutInflater) this.activity
				.getSystemService("layout_inflater")).inflate(
				R.layout.item_turno_ruta, null);

		turnoRuta = (TurnoRuta) paramDalusObject;
		tvCodRuta = (TextView) vi.findViewById(id.tvCodRuta);
		tvRuta = (TextView) vi.findViewById(id.tvRuta);
		tvTurno = (TextView) vi.findViewById(id.tvTurno);
		tvCodRuta.setText(turnoRuta.getCodRuta());
		tvRuta.setText(turnoRuta.getRuta());
		tvTurno.setText(turnoRuta.getTurno());

	}

}
