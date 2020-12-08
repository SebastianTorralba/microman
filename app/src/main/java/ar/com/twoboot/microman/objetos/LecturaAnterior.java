package ar.com.twoboot.microman.objetos;

import java.util.Date;

public class LecturaAnterior extends DalusObject {

	public LecturaAnterior() {
		// TODO Auto-generated constructor stub
	}

	private Hito hito;
	private Cliente cliente;
	private Medidor medidor;
	private Integer secuencial;
	private Date fecha;
	private Integer consumo;
	private Integer lectura;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Medidor getMedidor() {
		return medidor;
	}

	public void setMedidor(Medidor medidor) {
		this.medidor = medidor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getConsumo() {
		return consumo;
	}

	public void setConsumo(Integer consumo) {
		this.consumo = consumo;
	}

	public Integer getLectura() {
		return lectura;
	}

	public void setLectura(Integer lectura) {
		this.lectura = lectura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((medidor == null) ? 0 : medidor.hashCode());
		result = prime * result
				+ ((secuencial == null) ? 0 : secuencial.hashCode());
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
		LecturaAnterior other = (LecturaAnterior) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (medidor == null) {
			if (other.medidor != null)
				return false;
		} else if (!medidor.equals(other.medidor))
			return false;
		if (secuencial == null) {
			if (other.secuencial != null)
				return false;
		} else if (!secuencial.equals(other.secuencial))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LecturaAnterior [consumo=" + consumo + "]";
	}

	public Hito getHito() {
		return hito;
	}

	public void setHito(Hito hito) {
		this.hito = hito;
	}

	public Integer getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(Integer secuencial) {
		this.secuencial = secuencial;
	}

}
