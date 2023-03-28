package com.example.sharedpreferences_sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // Nombre de la base de datos
    private static final String DATABASE_NAME = "MisDatosDB.db";

    // Versión de la base de datos
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla
    private static final String TABLE_NAME = "MisDatos";

    // Nombres de las columnas de la tabla
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_APELLIDO = "apellido";
    private static final String COLUMN_CONTACTO = "contacto";

    // Sentencia SQL para crear la tabla
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NOMBRE + " TEXT,"
            + COLUMN_APELLIDO + " TEXT,"
            + COLUMN_CONTACTO + " TEXT)";

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar la tabla si existe y volver a crearla
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insertar un nuevo registro en la tabla
    public boolean insertarDatos(String nombre, String apellido, String contacto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_APELLIDO, apellido);
        values.put(COLUMN_CONTACTO, contacto);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Obtener todos los registros de la tabla
    public boolean buscarDatos(String nombre, String apellido, String contacto) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_ID,
                COLUMN_NOMBRE,
                COLUMN_APELLIDO,
                COLUMN_CONTACTO
        };
        String selection = COLUMN_NOMBRE + " = ? AND " + COLUMN_APELLIDO + " = ? AND " + COLUMN_CONTACTO + " = ?";
        String[] selectionArgs = {nombre, apellido, contacto};
        Cursor cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}


