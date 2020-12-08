package ar.com.twoboot.microman.dominio.sincronizacion;

import ar.com.twoboot.microman.objetos.Ruta;

public final class Config {
	private Integer idUsuario;
	private String lecturista;
	private String password;
	private String dbServerUrl;
	private String dbServerInstancia;
	private String dbServerBaseDatos;
	private String ftpUrl;
	private Ruta ruta;
	private Integer orden;
	private String baseDatos;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Config [Id=" + idUsuario + ", password=" + password
				+ ", sqlServerUrl=" + dbServerUrl + ", sqlServerInstancia="
				+ dbServerInstancia + ", ftpUrl=" + ftpUrl + "]";
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLecturista() {
		return lecturista;
	}

	public void setLecturista(String lecturista) {
		this.lecturista = lecturista;
	}

	public String getDbServerUrl() {
		return dbServerUrl;
	}

	public void setDbServerUrl(String dbServerUrl) {
		this.dbServerUrl = dbServerUrl;
	}

	public String getDbServerInstancia() {
		return dbServerInstancia;
	}

	public void setDbServerInstancia(String dbServerInstancia) {
		this.dbServerInstancia = dbServerInstancia;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public final void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the sqlServerUrl
	 */
	public final String getFtpUrl() {
		return ftpUrl;
	}

	/**
	 * @param ftpUrl
	 *            the ftpUrl to set
	 */
	public final void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}

	public Ruta getRuta() {
		return ruta;
	}

	public void setRuta(Ruta ruta) {
		this.ruta = ruta;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getDbServerBaseDatos() {
		return dbServerBaseDatos;
	}

	public void setDbServerBaseDatos(String dbServerBaseDatos) {
		this.dbServerBaseDatos = dbServerBaseDatos;
	}

	public String getBaseDatos() {
		return baseDatos;
	}

	public void setBaseDatos(String baseDatos) {
		this.baseDatos = baseDatos;
	}
}