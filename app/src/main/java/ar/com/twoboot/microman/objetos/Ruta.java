/***********************************************************************
 * Module:  Ruta.java
 * Author:  Administrador
 * Purpose: Defines the Class Ruta
 ***********************************************************************/

package ar.com.twoboot.microman.objetos;

import java.util.ArrayList;
import java.util.Iterator;

/** @pdOid c191a0be-3696-4fad-a42b-ce208de18bef */
public class Ruta extends DalusObject {
	/**
	 * 
	 */
	public final static Integer HITOS_LEIDOS=1;
	public final static Integer HITOS_NO_LEIDOS=0;
	public final static Integer HITOS_TRANSMITIDOS=2;	
	public final static Integer HITOS_TODOS=99;
	public final static Integer HITOS_TODOS_LEIDOS=90;
	private Integer hitosVisitados;
	private static final long serialVersionUID = 1L;
	/** @pdOid b4b8207b-f353-4595-bfab-4ebd46d9f420 */
	private java.lang.String codRuta;
	/** @pdOid cb0f3fcc-ac4b-45c8-89b6-305aefef57d8 */
	private java.lang.String ruta;
	private java.lang.String tipo;
	
	/** @pdOid ce76ecdd-49e0-46af-9aa4-d7309baf81ef */
	private Integer cantidadClientes;
	/** @pdOid c2cebb4e-1e9e-477e-8c87-fa34d8f296f4 */
	private String zona;
	private String turno;
	private Integer estado;
	/** @pdOid f1e206f2-e0d3-4468-926d-ef23403facb7 */
	private java.util.Date fechaCarga;
	private Hito hitoSeleccionado;
	/**
	 * @pdRoleInfo migr=no name=Hito assc=reference1 coll=java.util.List
	 *             mult=0..* type=Composition
	 */
	public java.util.List<Hito> hito;
	/**
	 * @pdRoleInfo migr=no name=Usuario assc=reference3 mult=1..1
	 *             type=Composition side=A
	 */
	public Usuario usuario;

	/** @pdOid 280d4d49-18c2-4534-80bf-f967d70cdb7f */
	public java.lang.String getCodRuta() {
		return codRuta;
	}

	/**
	 * @param newCodRuta
	 * @pdOid 32e49bf5-d354-4ef0-83e7-d921b2fb0731
	 */
	public void setCodRuta(java.lang.String newCodRuta) {
		codRuta = newCodRuta;
	}

	/** @pdOid 4cfd2e4a-a93e-4535-bd31-89ba5201fc79 */
	public java.lang.String getRuta() {
		return ruta;
	}

	/**
	 * @param newRuta
	 * @pdOid bee20ad5-4c8c-4dc3-a879-132e624aa66e
	 */
	public void setRuta(java.lang.String newRuta) {
		ruta = newRuta;
	}

	/** @pdOid 5b4f4986-693a-4895-826a-733632ee0c14 */
	public Integer getCantidadClientes() {
		return cantidadClientes;
	}

	/**
	 * @param newCantidadClientes
	 * @pdOid cf912084-f099-4f85-baa9-19e144fa673c
	 */
	public void setCantidadClientes(Integer newCantidadClientes) {
		cantidadClientes = newCantidadClientes;
	}

	/** @pdOid 030ebec3-a0df-4b5d-a36f-129388a0347b */
	public Integer getEstado() {
		return estado;
	}

	/**
	 * @param newEstado
	 * @pdOid efe891cc-ad8d-4334-80dd-11ca31cf8cda
	 */
	public void setEstado(Integer newEstado) {
		estado = newEstado;
	}

	/** @pdOid b0050fa0-6a7c-463e-90b1-b656684adaa7 */
	public java.util.Date getFechaCarga() {
		return fechaCarga;
	}

	/**
	 * @param newFechaCarga
	 * @pdOid 9781b919-81fe-4128-a2b2-0c515eb60d4b
	 */
	public void setFechaCarga(java.util.Date newFechaCarga) {
		fechaCarga = newFechaCarga;
	}

	/** @pdGenerated default getter */
	public java.util.List<Hito> getHito() {
		if (hito == null)
			hito = new java.util.ArrayList<Hito>();
		return hito;
	}

	/** @pdGenerated default iterator getter */
	public java.util.Iterator getIteratorHito() {
		if (hito == null)
			hito = new java.util.ArrayList<Hito>();
		return hito.iterator();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @pdGenerated default setter
	 * @param newHito
	 */
	public void setHito(java.util.List<Hito> newHito) {
		removeAllHito();
		for (java.util.Iterator iter = newHito.iterator(); iter.hasNext();)
			addHito((Hito) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newHito
	 */
	public void addHito(Hito newHito) {
		if (newHito == null)
			return;
		if (this.hito == null)
			this.hito = new java.util.ArrayList<Hito>();
		if (!this.hito.contains(newHito)) {
			this.hito.add(newHito);
			newHito.setRuta(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldHito
	 */
	public void removeHito(Hito oldHito) {
		if (oldHito == null)
			return;
		if (this.hito != null)
			if (this.hito.contains(oldHito)) {
				this.hito.remove(oldHito);
				oldHito.setRuta((Ruta) null);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllHito() {
		if (hito != null) {
			Hito oldHito;
			for (java.util.Iterator iter = getIteratorHito(); iter.hasNext();) {
				oldHito = (Hito) iter.next();
				iter.remove();
				oldHito.setRuta((Ruta) null);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codRuta == null) ? 0 : codRuta.hashCode());
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
		Ruta other = (Ruta) obj;
		if (codRuta == null) {
			if (other.codRuta != null)
				return false;
		} else if (!codRuta.equals(other.codRuta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return codRuta + " [" + ruta + "]";
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}
	public ArrayList<String> extraerMedidores(){
		ArrayList<String> alMedidores=new ArrayList<String>();
		for (Iterator iterator = this.getIteratorHito(); iterator.hasNext();) {
			String string = ((Hito) iterator.next()).getMedidor().getIdMedidor();
			alMedidores.add(string);
		}
		return alMedidores;
	}
	public ArrayList<String> extraerPersona(){
		ArrayList<String> alMedidores=new ArrayList<String>();
		for (Iterator iterator = this.getIteratorHito(); iterator.hasNext();) {
			Hito hito=(Hito) iterator.next();
			String string = hito.getCliente().getIdCliente().toString();
			string +=" " + hito.getCliente().getRazonSocial();
			alMedidores.add(string);
		}
		return alMedidores;
	}
	public ArrayList<String> extraerDomicilio(){
		ArrayList<String> alMedidores=new ArrayList<String>();
		for (Iterator iterator = this.getIteratorHito(); iterator.hasNext();) {
			Hito hito=(Hito) iterator.next();
			String string = hito.getMedidor().getIdMedidor(); 
			string +=" "+hito.getDomicilio();
			alMedidores.add(string);
		}
		return alMedidores;
	}
	public Hito getHitoSeleccionado() {
		return hitoSeleccionado;
	}

	public void setHitoSeleccionado(Hito hitoSeleccionado) {
		this.hitoSeleccionado = hitoSeleccionado;
	}
	public Integer buscarSiguienteSinLectura(Integer pivot){
		Integer pos=-1;
		
		ArrayList<Hito> hitos=(ArrayList<Hito>) this.getHito();
		for (int i = pivot; i < hitos.size(); i++) {
			Hito hitoAux = hitos.get(i);
			if(hitoAux.getEstado()==0){
				pos=i;
				return pos;
			}
		}
		return pos;
	}
	public Integer buscarAnteriorSinLectura(Integer pivot){
		Integer pos=-1;
		ArrayList<Hito> hitos=(ArrayList<Hito>) this.getHito();
		
		for (int i = pivot; i >=0; i--) {
			Hito hitoAux = hitos.get(i);
			if(hitoAux.getEstado()==0){
				pos=i;
				return pos;
			}
		}
		return pos;
	}

	public Integer getHitosVisitados() {
		return hitosVisitados;
	}

	public void setHitosVisitados(Integer hitosVisitados) {
		this.hitosVisitados = hitosVisitados;
	}

	public java.lang.String getTipo() {
		return tipo;
	}

	public void setTipo(java.lang.String tipo) {
		this.tipo = tipo;
	}

}