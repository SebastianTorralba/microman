package ar.com.twoboot.microman.dialogabm;

import android.annotation.SuppressLint;
import android.widget.EditText;
import android.widget.Spinner;
import ar.com.twoboot.microman.MicroMan;
import ar.com.twoboot.microman.R.id;
import ar.com.twoboot.microman.dominio.OnConfig;
import ar.com.twoboot.microman.dominio.OnUsuario;
import ar.com.twoboot.microman.objetos.DalusObject;
import ar.com.twoboot.microman.objetos.Usuario;

@SuppressLint("ValidFragment")
public class DialogLogin extends DialogBaseAbm {

	private Usuario usuario;
	private OnUsuario oUsuario;
	private EditText etUsuario;
	private EditText etClave;
	private OnConfig oConfig;
	private Spinner spBaseDatos;
	public DialogLogin(int accion, DalusObject objeto, int layout) {
		super(accion, objeto, layout);
		usuario = (Usuario) objeto;
		oConfig= new OnConfig(mTrans);
		oUsuario = new OnUsuario(mTrans);
		titulo = "Login";
	}

	@Override
	public DalusObject consolidar() {
		// TODO Auto-generated method stub
		return super.consolidar();
	}
	
	@Override
	public Boolean cancelar() {
		actividadLlamada.finish();
		return true;
	}

	@Override
	protected void inicializacion() {
		etUsuario=(EditText) rootView.findViewById(id.etUsuario);
		etClave=(EditText) rootView.findViewById(id.etClave);
		spBaseDatos=(Spinner) rootView.findViewById(id.spBaseDatos);
	}

	@Override
	public boolean validar() {
		Boolean resultado=true;
		String baseDatos;
		resultado=oUsuario.autorizarUsuario(etUsuario.getText().toString(), etClave.getText().toString());
		if (!resultado){
			mostrarAdvertencia("Usuario o Clave Incorrecta");
		}
		else
		{
			baseDatos=(String) spBaseDatos.getSelectedItem();
			if(baseDatos!=null){
				oConfig.actualizarBaseDatos(baseDatos);
			}else{
				mostrarAdvertencia("Seleccione Localidad");
			}
		}
		return resultado;
	}

	@Override
	public int grabar(DalusObject objeto) {
		usuario=oUsuario.extraer(etUsuario.getText().toString(), etClave.getText().toString());	
		((MicroMan) actividadLlamada).actualizar();
		
		return 0;
	}
	
}
