package com.example.estruch18.proyecto5ead;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by estruch18 on 10/1/16.
 */
public class CustomContentProvider extends ContentProvider{
    //Atributos de la clase
    static final String PROVIDER_NAME = "com.example.estruch18.proyecto5ead";
    static final String URL = "content://"+PROVIDER_NAME+"/Estudiante";
    static final Uri CONTENT_URI = Uri.parse(URL);

    //Campos de la bbdd
    static final String NOMBRE = "nombre";
    static final String EDAD = "edad";
    static final String CICLO = "ciclo";
    static final String CURSO = "curso";
    static final String NOTAMEDIA = "notaMedia";
    static final String DESPACHO = "despacho";

    MyDbHelper dbHelper;

    //CONSTANTES USADAS PARA EL CONTENT URI
    static final int ESTUDIANTES = 1;
    static final int ESTUDIANTES_NOMBRE = 2;

    //MAPEO DE PATRONES DE CONTENT URI A LOS VALORES ARRIBA DEFINIDOS
    static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "Estudiante", ESTUDIANTES);
        uriMatcher.addURI(PROVIDER_NAME, "Estudiante/#", ESTUDIANTES_NOMBRE);
    }

    //DECLARACIONES BBDD
    private SQLiteDatabase instituto_database;
    static final String BBDD_NAME = "Instituto.db";
    static final String BBDD_TABLA_PROFESORES = "Profesor";
    static final String BBDD_TABLA_ESTUDIANTES = "Estudiante";
    static final int BBDD_VERSION = 1;
    static final String CREAR_TABLA_PROFESORES = "CREATE TABLE "+BBDD_TABLA_PROFESORES
            +" ( id         integer     primary key autoincrement, " +
            "    nombre     text        not null," +
            "    edad       integer     not null," +
            "    ciclo      text        not null," +
            "    curso      integer     not null," +
            "    despacho   text        not null);";

    static final String CREAR_TABLA_ESTUDIANTES = "CREATE TABLE "+BBDD_TABLA_ESTUDIANTES
            +" ( id         integer     primary key autoincrement, " +
            "    nombre     text        not null," +
            "    edad       integer     not null," +
            "    ciclo      text        not null," +
            "    curso      integer     not null," +
            "    notaMedia  float      not null);";

    static final String ELIMINAR_TABLA_PROFESORES = "DROP TABLE IF EXISTS "+BBDD_TABLA_PROFESORES+";";
    static final String ELIMINAR_TABLA_ESTUDIANTES = "DROP TABLE IF EXISTS "+BBDD_TABLA_ESTUDIANTES+";";


    @Override
    public boolean onCreate() {
        dbHelper = new MyDbHelper(getContext());
        instituto_database = dbHelper.getWritableDatabase();

        if(instituto_database == null){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(BBDD_TABLA_ESTUDIANTES);

        switch (uriMatcher.match(uri)){
            case ESTUDIANTES:
                break;
            case ESTUDIANTES_NOMBRE:
                queryBuilder.appendWhere(NOMBRE+" = "+uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }

        return queryBuilder.query(instituto_database, null, null, null, null, null, null);
    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)){
            case ESTUDIANTES:
                return "vnd.android.cursor.dir/vnd.com.example.estruch18.proyecto5ead";
            case ESTUDIANTES_NOMBRE:
                return "vnd.android.cursor.item/vnd.com.example.estruch18.proyecto5ead";
            default:
                throw new IllegalArgumentException("Unsupported URI "+uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri _uri = uri;
        return _uri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 1;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 1;
    }


    //CLASE MYDBHELPER
    private static class MyDbHelper extends SQLiteOpenHelper {

        public MyDbHelper (Context context){
            super(context, BBDD_NAME, null, BBDD_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREAR_TABLA_ESTUDIANTES);
            db.execSQL(CREAR_TABLA_PROFESORES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(MyDbHelper.class.getName(),
                    "Upgrading database from version " + oldVersion +
                            " to " + newVersion +
                            ". Old data will be destroyed");
            db.execSQL(ELIMINAR_TABLA_ESTUDIANTES);
            db.execSQL(ELIMINAR_TABLA_PROFESORES);
            onCreate(db);
        }

    }
}
