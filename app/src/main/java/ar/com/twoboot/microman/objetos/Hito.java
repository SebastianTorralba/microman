/***********************************************************************
 * Module:  Hito.java
 * Author:  Administrador
 * Purpose: Defines the Class Hito
 ***********************************************************************/

package ar.com.twoboot.microman.objetos;

import java.util.ArrayList;

import android.util.Log;
import ar.com.twoboot.microman.util.Util;

/** @pdOid 67beb99b-a17a-40af-80b8-bb80d953e5db */
public class Hito extends DalusObject implements Cloneable {
	/** @pdOid 73f6eb62-d7ba-4d21-9e7f-28e3ec6b0b2a */
	private Integer orden;
	private Integer ordenEfectivo;
	
	public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            Log.e(Util.APP," no se puede duplicar");
        }
        return obj;
    }
	public Integer getOrdenEfectivo() {
		return ordenEfectivo;
	}

	public void setOrdenEfectivo(Integer ordenEfectivo) {
		this.ordenEfectivo = ordenEfectivo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		if (this.cliente == null || !this.cliente.equals(cliente)) {
			if (this.cliente != null) {
				Cliente oldCliente = this.cliente;
				this.cliente = null;
			}
			if (cliente != null) {
				this.cliente = cliente;
				this.cliente.setHito(this);
			}
		}

	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	/** @pdOid b628a491-2ecb-4f73-8f2e-f071358afbb6 */
	private java.lang.String domicilio;
	/** @pdOid 46749c4f-3712-4479-9d7b-984f49a8fa28 */
	private Cliente cliente;
	private Medidor medidor;
	private Integer lecturaActual;
	/** @pdOid 90215099-7c64-4f0d-83f2-f73a9bd09c24 */
	private Integer lecturaAnterior;
	/** @pdOid 3c4224bb-f324-484c-87ee-ddd97257383a */
	private Integer consumo;
	/** @pdOid a35eebfa-138c-4a91-8be4-04c13a8da33d */
	private byte[] foto;
	private byte[] fotoComplementaria;
	
	/** @pdOid d9fb72a5-a6e4-467a-9f91-dd6fc817fa29 */
	private Double gpslongitud;
	/** @pdOid 6b98cebb-2eed-419f-9796-a804336c2d8e */
	private Double gpslatitud;
	/** @pdOid afce4bf9-f0c9-42e5-bd3f-0024cdd45c43 */
	private java.lang.String datosComplementario;
	/** @pdOid 599d5b6c-a6dc-413d-9a82-ef3e9f336dbd */
	private Integer estado;
	/** @pdOid 15ecfdf4-0367-4203-93fe-3931d5c95a56 */
	private java.util.Date fechaCarga;
	private Integer intentos;
	private String fueraRango;
	
	public byte[] getFotoComplementaria() {
		return fotoComplementaria;
	}

	public void setFotoComplementaria(byte[] fotoComplementaria) {
		this.fotoComplementaria = fotoComplementaria;
	}

	public String getFueraRango() {
		return fueraRango;
	}

	public void setFueraRango(String fueraRango) {
		this.fueraRango = fueraRango;
	}

	public Integer getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(Integer secuencial) {
		this.secuencial = secuencial;
	}

	public String getTipoLectura() {
		return tipoLectura;
	}

	public void setTipoLectura(String tipoLectura) {
		this.tipoLectura = tipoLectura;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	private Integer secuencial;
	private String tipoLectura;
	private String observaciones;
	
	private ArrayList<LecturaAnterior> lecturasAnteriores;

	public ArrayList<LecturaAnterior> getLecturasAnteriores() {
		return lecturasAnteriores;
	}

	public void setLecturasAnteriores(
			ArrayList<LecturaAnterior> lecturasAnteriores) {
		this.lecturasAnteriores = lecturasAnteriores;
	}

	/** @pdRoleInfo migr=no name=Ruta assc=reference1 mult=1..1 side=A */
	public Ruta ruta;
	/**
	 * @pdRoleInfo migr=no name=Usuario assc=reference2 mult=1..1
	 *             type=Composition side=A
	 */
	public Usuario usuario;
	/**
	 * @pdRoleInfo migr=no name=Observacion assc=reference4 mult=1..1
	 *             type=Composition side=A
	 */
	public Observacion observacion;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Observacion getObservacion() {
		return observacion;
	}

	public void setObservacion(Observacion observacion) {
		this.observacion = observacion;
	}

	/** @pdOid 7cfcb877-f6b6-4969-8cf3-2109026cbbe6 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param newOrden
	 * @pdOid 7bf4306f-a192-4bc8-b254-b32453d6b495
	 */
	public void setOrden(Integer newOrden) {
		orden = newOrden;
	}

	/** @pdOid 5131d469-f477-4724-ae1b-dbb86389fbe1 */
	public java.lang.String getDomicilio() {
		return domicilio;
	}

	/**
	 * @param newDomicilio
	 * @pdOid 57ac5b60-8a54-41d7-8c71-ecad711a4c62
	 */
	public void setDomicilio(java.lang.String newDomicilio) {
		domicilio = newDomicilio;
	}

	/** @pdOid b4bc3529-1c71-45ef-86ff-977c07ac7462 */

	/**
	 * @param newUnidad
	 * @pdOid 8ecb44e2-7dec-41be-a895-54a2127370fe
	 */

	/** @pdOid 0f638237-3c92-427f-8bd1-ec2afb160346 */

	/**
	 * @param newRazonSocial
	 * @pdOid bc4da5c8-991b-4b05-a3cf-2385fbdd17f0
	 */

	/** @pdOid 1b9c9f67-e431-4838-b35c-849b4ceab261 */
	/**
	 * @param newIdMedidor
	 * @pdOid e112ce75-83ad-4b34-853d-a0b78c867905
	 */

	/** @pdOid 7a8f6821-f967-4ce1-9557-e909b1242287 */
	public Integer getLecturaActual() {
		return lecturaActual;
	}

	/**
	 * @param newLecturaActual
	 * @pdOid 81219936-7bff-4532-9251-def89d4b8aba
	 */
	public void setLecturaActual(Integer newLecturaActual) {
		lecturaActual = newLecturaActual;
	}

	/** @pdOid dc5fe2f2-bc09-4ecd-9651-73688a6cdfd1 */
	public Integer getLecturaAnterior() {
		return lecturaAnterior;
	}

	/**
	 * @param newLecturaAnterior
	 * @pdOid a02232fb-626a-40de-8e4d-e1088b760a51
	 */
	public void setLecturaAnterior(Integer newLecturaAnterior) {
		lecturaAnterior = newLecturaAnterior;
	}

	/** @pdOid e1f20331-178a-4fc0-8e2a-e1fd0d481b08 */
	public Integer getConsumo() {
		return consumo;
	}

	/**
	 * @param newConsumo
	 * @pdOid 4bc47aa0-e528-4f05-b0df-c16bcf66f221
	 */
	public void setConsumo(Integer newConsumo) {
		consumo = newConsumo;
	}

	public Double getGpslongitud() {
		return gpslongitud;
	}

	public void setGpslongitud(Double gpslongitud) {
		this.gpslongitud = gpslongitud;
	}

	public Double getGpslatitud() {
		return gpslatitud;
	}

	public void setGpslatitud(Double gpslatitud) {
		this.gpslatitud = gpslatitud;
	}

	/** @pdOid dc2b213b-c3d6-4ecf-8ecf-accec42a429b */
	public java.lang.String getDatosComplementario() {
		return datosComplementario;
	}

	/**
	 * @param newDatosComplementario
	 * @pdOid 6e2723f8-50f0-473d-ae42-a8d549913d01
	 */
	public void setDatosComplementario(java.lang.String newDatosComplementario) {
		datosComplementario = newDatosComplementario;
	}

	/** @pdOid a1cb24ff-01e4-40cd-a1b1-2ca2da155914 */
	public Integer getEstado() {
		return estado;
	}

	/**
	 * @param newEstado
	 * @pdOid 53372934-5ed7-412e-8b7d-a7f4c2cc1057
	 */
	public void setEstado(Integer newEstado) {
		estado = newEstado;
	}

	/** @pdOid 79254143-d606-4868-beaf-c1728379f370 */
	public java.util.Date getFechaCarga() {
		return fechaCarga;
	}

	/**
	 * @param newFechaCarga
	 * @pdOid b29c5a15-a4d4-4b7e-a8f9-684d3cbfea05
	 */
	public void setFechaCarga(java.util.Date newFechaCarga) {
		fechaCarga = newFechaCarga;
	}

	/** @pdGenerated default parent getter */
	public Ruta getRuta() {
		return ruta;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newRuta
	 */
	public void setRuta(Ruta newRuta) {
		if (this.ruta == null || !this.ruta.equals(newRuta)) {
			if (this.ruta != null) {
				Ruta oldRuta = this.ruta;
				this.ruta = null;
				oldRuta.removeHito(this);
			}
			if (newRuta != null) {
				this.ruta = newRuta;
				this.ruta.addHito(this);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
		result = prime * result + ((ruta == null) ? 0 : ruta.hashCode());
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
		Hito other = (Hito) obj;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		if (ruta == null) {
			if (other.ruta != null)
				return false;
		} else if (!ruta.equals(other.ruta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		
		return "Hito [orden=" + orden + ", domicilio=" + domicilio + "]";
	}
	public String toLog(){
		String texto="";
		texto=this.getCliente().getIdCliente()+"|"+this.getMedidor().getIdMedidor()+"|"
				+this.getObservacion().getCodObservacion()+"|"
				+this.lecturaActual+"|"+this.consumo+"|"+this.fueraRango+"|"
				+this.observaciones;
		return texto;
	}
	public Medidor getMedidor() {
		return medidor;
	}

	public void setMedidor(Medidor medidor) {
		if (this.medidor == null || !this.medidor.equals(medidor)) {
			if (this.medidor != null) {
				Medidor oldMedidor = this.medidor;
				this.medidor = null;
			}
			if (medidor != null) {
				this.medidor = medidor;
				this.medidor.setHito(this);
			}
		}
	}

	public Integer getIntentos() {
		return intentos;
	}

	public void setIntentos(Integer intentos) {
		this.intentos = intentos;
	}

}