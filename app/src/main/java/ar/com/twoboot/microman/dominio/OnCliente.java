package ar.com.twoboot.microman.dominio;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import ar.com.twoboot.microman.objetos.Cliente;

public class OnCliente extends OnNegocio implements Transaccionable {

	public OnCliente(Transaccion paramTransaccion) {
		super(paramTransaccion);
		// TODO Auto-generated constructor stub
	}

	private Cliente cliente;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente extraer(Integer IdCliente) {
		String select = "select * from Cliente where idCliente=" + IdCliente;
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, null);
		if (localCursor.moveToFirst()) {
			cliente = new Cliente();
			cliente.setIdCliente(quitarNull(localCursor.getInt(0)));
			cliente.setRazonSocial(quitarNull(localCursor.getString(1)));
		}
		localCursor.close();

		return cliente;
	}

	public boolean existe(Integer IdCliente) {
		boolean existe=false;
		String select = "select count(*) from Cliente where idCliente=" + IdCliente;
		Cursor localCursor = getTransaccion().baseDatos.rawQuery(select, null);
		if (localCursor.moveToFirst()) {
			int i=localCursor.getInt(0);
			if(i>0){
				existe=true;
			}else{
				existe=false;
			}
			
		}
		localCursor.close();

		return existe;
	}

	@Override
	public int actualizar() {
		long rtn = 0;

		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("idCliente", cliente.getIdCliente());
			valores.put("RazonSocial", cliente.getRazonSocial());
			whereString = "idCLiente=" + cliente.getIdCliente();
			rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores,
					whereString, null);
			if (rtn > 0) {
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i("MicroMan", "ACTUALIZANDO " + rtn);
			} else {
				// getTransaccion().baseDatos.
			}
			getTransaccion().baseDatos.endTransaction();

		} catch (SQLException e) {
			// TODO: handle exception
			Log.e("MicroMan", e.getMessage());
		}
		return (int) rtn;
	}

	@Override
	public int eliminar() {
		long rtn = 0;

		try {
			whereString = "idCLiente=" + cliente.getIdCliente();
			rtn = getTransaccion().baseDatos.delete(getNombreTabla(),
					whereString, null);
			if (rtn > 0) {
				Log.i("MicroMan", "BORRANDO " + rtn);
			} else {
				// getTransaccion().baseDatos.
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			Log.e("MicroMan", e.getMessage());
		}
		return (int) rtn;
	}

	@Override
	public int insertar() {
		long rtn = 0;

		try {
			getTransaccion().baseDatos.beginTransaction();
			valores.put("idCliente", cliente.getIdCliente());
			valores.put("RazonSocial", cliente.getRazonSocial());
			whereString = "idCLiente=" + cliente.getIdCliente();
			rtn = getTransaccion().baseDatos.update(getNombreTabla(), valores,
					whereString, null);
			if (rtn > 0) {
				getTransaccion().baseDatos.setTransactionSuccessful();
				Log.i("MicroMan", "ACTUALIZANDO " + rtn);
			} else {
				// getTransaccion().baseDatos.
			}
			getTransaccion().baseDatos.endTransaction();

		} catch (SQLException e) {
			// TODO: handle exception
			Log.e("MicroMan", e.getMessage());
		}
		return (int) rtn;
	}

	@Override
	public int validar() {
		// TODO Auto-generated method stub
		return 0;
	}
}