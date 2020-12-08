package ar.com.twoboot.microman.objetos;

public class TurnoRuta extends DalusObject {

	public TurnoRuta() {
		// TODO Auto-generated constructor stub
	}

	private String turno;
	private String codRuta;
	private String ruta;
	private String tipo;

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getCodRuta() {
		return codRuta;
	}

	public void setCodRuta(String codRuta) {
		this.codRuta = codRuta;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
