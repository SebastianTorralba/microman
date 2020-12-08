/***********************************************************************
 * Module:  Usuario.java
 * Author:  Administrador
 * Purpose: Defines the Class Usuario
 ***********************************************************************/

package ar.com.twoboot.microman.objetos;

/** @pdOid b16f8e64-e734-4b92-8a4f-dae40dcd07fb */
public class Usuario extends DalusObject {
	/** @pdOid 0cd03073-efca-4aa1-80e2-088c58bceb35 */
	private Integer idUsuario;
	/** @pdOid fabcb33b-fba9-4579-9da5-dcd5c9df78ee */
	private java.lang.String usuario;
	/** @pdOid 1581461e-1e54-4efd-aec9-2d47e7d1a015 */
	private java.lang.String clave;
	
	private String imei;
	private String apellidoNombre;
	private String tipo;
	private String telefono;
	private String hash;
	
	
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getApellidoNombre() {
		return apellidoNombre;
	}

	public void setApellidoNombre(String apellidoNombre) {
		this.apellidoNombre = apellidoNombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	/** @pdOid 7e6d76f9-f642-4f4a-a674-f76938c593e0 */
	private Integer estado;
	/** @pdOid 8060226e-547e-48b2-bda1-cc4812ddd75a */
	private java.util.Date fechaCarga;

	/** @pdOid fb64569f-8b8a-455d-86ac-3ced0ba0bbb9 */
	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param newIdUsuario
	 * @pdOid 4e700be4-7bf0-475f-9709-8afb8e89e96a
	 */
	public void setIdUsuario(Integer newIdUsuario) {
		idUsuario = newIdUsuario;
	}

	/** @pdOid 57b475c9-23ca-405f-80dd-a94b7c3747d7 */
	public java.lang.String getUsuario() {
		return usuario;
	}

	/**
	 * @param newUsuario
	 * @pdOid b7d7fde2-c96b-4d8f-8cf1-a4d41ce84a31
	 */
	public void setUsuario(java.lang.String newUsuario) {
		usuario = newUsuario;
	}

	/** @pdOid 5f29e9fa-3433-474d-8da4-e13b2821d54d */
	public java.lang.String getClave() {
		return clave;
	}

	/**
	 * @param newClave
	 * @pdOid 42a394e2-30c1-40aa-a767-60b658ea8f35
	 */
	public void setClave(java.lang.String newClave) {
		clave = newClave;
	}

	/** @pdOid 810dc32d-7a84-4386-a3bd-1f9e07328b37 */
	public Integer getEstado() {
		return estado;
	}

	/**
	 * @param newEstado
	 * @pdOid 824c32ae-613d-43ed-85eb-5c29a2a8e45a
	 */
	public void setEstado(Integer newEstado) {
		estado = newEstado;
	}

	/** @pdOid 9241d586-b55a-4cf8-acc1-29a2e09dcbdc */
	public java.util.Date getFechaCarga() {
		return fechaCarga;
	}

	/**
	 * @param newFechaCarga
	 * @pdOid aa650a43-6e6b-4230-adad-7fc38add39f6
	 */
	public void setFechaCarga(java.util.Date newFechaCarga) {
		fechaCarga = newFechaCarga;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + "]";
	}

}