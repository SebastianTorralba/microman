package ar.com.twoboot.microman.objetos;

import java.util.Date;

public class Medidor extends DalusObject {

	public Medidor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return idMedidor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idMedidor == null) ? 0 : idMedidor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medidor other = (Medidor) obj;
		if (idMedidor == null) {
			if (other.idMedidor != null)
				return false;
		} else if (!idMedidor.equals(other.idMedidor))
			return false;
		return true;
	}

	public java.lang.String getIdMedidor() {
		return idMedidor;
	}

	public void setIdMedidor(java.lang.String idMedidor) {
		this.idMedidor = idMedidor;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Date getFechaInstalacion() {
		return fechaInstalacion;
	}

	public void setFechaInstalacion(Date fechaInstalacion) {
		this.fechaInstalacion = fechaInstalacion;
	}

	public Hito getHito() {
		return hito;
	}

	public void setHito(Hito hito) {
		this.hito = hito;
	}

	private Hito hito;

	private java.lang.String idMedidor;
	/** @pdOid fe98b042-ac02-4a7f-ae8b-32b8ff81ab88 */
	private String modelo;
	private Date fechaInstalacion;
}
