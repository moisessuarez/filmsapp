package com.example.filmsapp.datos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.filmsapp.modelo.Genero;
import com.example.filmsapp.modelo.Pelicula;


public class PeliculasBD extends SQLiteOpenHelper implements RepositorioPeliculas {

    Context contexto;

    public PeliculasBD(Context contexto) {
        super(contexto, "peliculas.db", null, 1);
        this.contexto = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE peliculas (_id INTEGER PRIMARY KEY AUTOINCREMENT, nombrePelicula TEXT, director TEXT, sinopsis TEXT, " + "paisOrigen TEXT, genero INTEGER,anioLanzamiento INTEGER, fechaCreacion BIGINIT) ");
        db.execSQL("INSERT INTO peliculas VALUES (null, 'La guerra de las galaxias I' ,'George Lucas', 'La trama ....', 'Estados Unidos'," + Genero.CIENCIA_FICCION.ordinal() + ",1999," + System.currentTimeMillis() + ") ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public Pelicula elemento(int id) {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM peliculas WHERE _id = " + id, null);
        try {
            if (cursor.moveToNext()) {
                Log.d("TAG", "cursor pelicula " + cursor);
                return extraePelicula(cursor);
            } else throw new SQLException("Error al buscar la pelicula con _id =" + id);
        } catch (Exception e) {
            Log.d("TAG", "error elemnto lugares db exec " + e.getMessage());
            throw e;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override
    public int nuevo() {
        int _id = -1;
        Pelicula pelicula = new Pelicula();
        getWritableDatabase().execSQL("INSERT INTO peliculas (nombrePelicula, director, sinopsis, paisOrigen, genero, anioLanzamiento, fechaCreacion) " + " VALUES('','','','', " + pelicula.getTipoGenero().ordinal() + ",''," + pelicula.getFechaCreacion() + " ) ");
        Cursor c = getReadableDatabase().rawQuery("SELECT _id FROM peliculas WHERE fechaCreacion = " + pelicula.getFechaCreacion(), null);
        if (c.moveToNext()) _id = c.getInt(0);
        c.close();
        Log.d("TAG", "nueva peli DB " + _id + " " + pelicula.getTipoGenero().getTexto());
        return _id;
    }

    @Override
    public void borrar(int id) {
        Log.d("tag", "eliminar " + id);
        getWritableDatabase().execSQL("DELETE FROM peliculas WHERE _id = " + id);
    }

    @Override
    public int tamanio() {
        return 0;
    }

    @Override
    public void actualiza(int id, Pelicula movie) {
        getWritableDatabase().execSQL("UPDATE peliculas SET" + " nombrePelicula = '" + movie.getNombrePelicula() + "', " + " director = '" + movie.getDirector() + "'," + " sinopsis = '" + movie.getSinopsis() + "'," + " paisOrigen = '" + movie.getPaisOrigen() + "'," + " genero = " + movie.getTipoGenero().ordinal() + ", anioLanzamiento = " + movie.getAnioLanzamiento() + " WHERE _id = " + id);
    }

    public static Pelicula extraePelicula(Cursor cursor) {
        Pelicula pelicula = new Pelicula();
        pelicula.setNombrePelicula(cursor.getString(1));
        pelicula.setDirector(cursor.getString(2));
        pelicula.setSinopsis(cursor.getString(3));
        pelicula.setPaisOrigen(cursor.getString(4));
        pelicula.setTipoGenero(Genero.values()[cursor.getInt(5)]);
        pelicula.setAnioLanzamiento(cursor.getInt(6));
        return pelicula;
    }

    public Cursor extraeCursor() {
        String consulta = "SELECT * FROM peliculas";
        SQLiteDatabase bd = getReadableDatabase();
        return bd.rawQuery(consulta, null);
    }
}