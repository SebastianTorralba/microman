/***********************************************************************
 * Module:  Observacion.java
 * Author:  Administrador
 * Purpose: Defines the Class Observacion
 ***********************************************************************/

package ar.com.twoboot.microman.objetos;

/** @pdOid 4115d69b-9722-4426-ba73-2a11b0593fb6 */
public class Observacion extends DalusObject {
	/** @pdOid 602da933-9d5f-4de9-9bea-447083dca143 */
	private java.lang.String codObservacion;
	public final static String CONSUMO_MEDIDO = "MEDIDO";
	public final static String CONSUMO_ESTIMADO = "ESTIMADO";
	public final static String CONSUMO_PROMEDIO = "PROMEDIO";
	public final static String CONSUMO_NINGUNO = "NINGUNO";
	public final static Integer PORCENTAJE_FUERA_RANGO = 40;
	public final static Integer ESTIMADO = 30;

	public java.lang.String getCodObservacion() {
		return codObservacion;
	}

	public void setCodObservacion(java.lang.String codObservacion) {
		this.codObservacion = codObservacion;
	}

	/** @pdOid 9df3b155-2eca-4b08-aa97-afe017b7cc65 */
	private java.lang.String observacion;

	/** @pdOid b10c51af-c56e-436b-8a72-d1ccd0242b3b */
	public java.lang.String getObservacion() {
		return observacion;
	}

	/**
	 * @param newObservacion
	 * @pdOid 3f3e9f9e-e5ab-4c02-a5b5-f26f3f51f2b9
	 */
	public void setObservacion(java.lang.String newObservacion) {
		observacion = newObservacion;
	}

	private String tipoMedicion;

	public String getTipoMedicion() {
		return tipoMedicion;
	}

	public void setTipoMedicion(String tipoMedicion) {
		this.tipoMedicion = tipoMedicion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codObservacion == null) ? 0 : codObservacion.hashCode());
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
		Observacion other = (Observacion) obj;
		if (codObservacion == null) {
			if (other.codObservacion != null)
				return false;
		} else if (!codObservacion.equals(other.codObservacion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return codObservacion + " " + observacion;
	}

}