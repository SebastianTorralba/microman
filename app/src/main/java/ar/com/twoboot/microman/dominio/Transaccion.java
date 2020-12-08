package ar.com.twoboot.microman.dominio;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class Transaccion {
	protected String Nombretabla;
	public static SQLiteDatabase baseDatos;
	private String nombreDB = "/mnt/sdcard/db_microman.db";

	public Transaccion() {
		if (this.baseDatos != null)
			conectarDB();
	}

	private void readObject(ObjectInputStream paramObjectInputStream)
			throws IOException, ClassNotFoundException {
		paramObjectInputStream.defaultReadObject();
	}

	private void writeObject(ObjectOutputStream paramObjectOutputStream)
			throws IOException {
		paramObjectOutputStream.defaultWriteObject();
	}

	public final int conectarDB() {
		// TODO: implement
		File extStore = Environment.getExternalStorageDirectory();
	//	nombreDB=extStore.getAbsolutePath()+"/db_microman.db";
		if (baseDatos == null) {
			baseDatos = SQLiteDatabase.openDatabase(nombreDB, null,
					SQLiteDatabase.OPEN_READWRITE);
			if (baseDatos.isOpen()) {
				return 0;
			} else {
				return -1;
			}
		} else {
			
			return -2;
		}
	}

	public final int delete(String paramString) {
		this.baseDatos.beginTransaction();
		this.baseDatos.rawQuery("delete from " + paramString, null);
		this.baseDatos.setTransactionSuccessful();
		Log.i("SQLSERVER", "BORRAR");
		return 0;
	}

	protected void finalize() {
	}

	public final SQLiteDatabase getBaseDatos() {
		return null;
	}

	public final long getid(String paramString) {
		long l = 0L;
		Cursor localCursor = this.baseDatos.rawQuery(
				"select max(id_formulario) from formulario ", null);
		if (localCursor.moveToFirst())
			do
				l = 1L + localCursor.getInt(0);
			while (localCursor.moveToNext());
		localCursor.close();
		return l;
	}

	public final long insert(String paramString,
			ContentValues paramContentValues) {
		long l = 0L;
		this.baseDatos.isOpen();
		try {
			l = this.baseDatos.insertOrThrow(paramString, null,
					paramContentValues);
			Log.i("SQLSERVER", "INSERT SQLite");
			return l;
		} catch (SQLException localSQLException) {
			Log.e("dalus", localSQLException.getMessage());
		}
		return l;
	}

	public final void setBaseDatos(SQLiteDatabase paramSQLiteDatabase) {
	}
}

/*
 * Location: C:\Documents and Settings\Administrador.TWOBOOT.000\Mis
 * documentos\android\MicroMan_dex2jar.jar Qualified Name:
 * ar.com.twoboot.microman.dominio.Transaccion JD-Core Version: 0.6.0
 */