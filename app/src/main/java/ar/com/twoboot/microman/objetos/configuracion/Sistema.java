package ar.com.twoboot.microman.objetos.configuracion;

import java.util.Date;

import ar.com.twoboot.microman.objetos.DalusObject;

public class Sistema extends DalusObject {
	private String version;
	private Date fechaActual; 
	private Integer cpm;
	private Integer consumoElevado;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getFechaActual() {
		return fechaActual;
	}
	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}
	public Integer getCpm() {
		return cpm;
	}
	public void setCpm(Integer cpm) {
		this.cpm = cpm;
	}
	public Integer getConsumoElevado() {
		return consumoElevado;
	}
	public void setConsumoElevado(Integer consumoElevado) {
		this.consumoElevado = consumoElevado;
	}
	
}
