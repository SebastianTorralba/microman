package ar.com.twoboot.microman.objetos;

public class Cliente extends DalusObject {

	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	private Hito hito;
	private Integer idCliente;
	/** @pdOid 2a17f71a-21a8-4e52-8f56-11ff1a7548ef */
	private java.lang.String razonSocial;

	/** @pdOid 1c365ec1-c90e-4922-9e22-f55665d78e51 */
	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public java.lang.String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(java.lang.String razonSocial) {
		this.razonSocial = razonSocial;
	}

	@Override
	public String toString() {
		return idCliente.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCliente == null) ? 0 : idCliente.hashCode());
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
		Cliente other = (Cliente) obj;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		return true;
	}

	public Hito getHito() {
		return hito;
	}

	public void setHito(Hito hito) {
		this.hito = hito;
	}

}
