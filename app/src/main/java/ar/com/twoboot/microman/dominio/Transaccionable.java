package ar.com.twoboot.microman.dominio;

public abstract interface Transaccionable {
	public abstract int actualizar();

	public abstract int eliminar();

	public abstract int insertar();

	public abstract int validar();
}

/*
 * Location: C:\Documents and Settings\Administrador.TWOBOOT.000\Mis
 * documentos\android\MicroMan_dex2jar.jar Qualified Name:
 * ar.com.twoboot.microman.dominio.Transaccionable JD-Core Version: 0.6.0
 */