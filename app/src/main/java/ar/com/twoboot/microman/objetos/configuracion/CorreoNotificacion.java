package ar.com.twoboot.microman.objetos.configuracion;

import ar.com.twoboot.microman.objetos.DalusObject;

public class CorreoNotificacion extends DalusObject {
	private String correoElectronico;
	private String alias;
	public CorreoNotificacion(){}	
	public CorreoNotificacion(String correoElectronico, String alias) {
		super();
		this.correoElectronico = correoElectronico;
		this.alias = alias;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
}
