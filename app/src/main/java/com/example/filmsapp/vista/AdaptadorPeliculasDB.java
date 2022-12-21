package com.example.filmsapp.vista;

import android.database.Cursor;
import com.example.filmsapp.datos.PeliculasBD;
import com.example.filmsapp.datos.RepositorioPeliculas;
import com.example.filmsapp.modelo.Pelicula;

public class AdaptadorPeliculasDB extends AdaptadorPeliculas{
    protected Cursor cursor;

    public AdaptadorPeliculasDB(RepositorioPeliculas peliculas, Cursor cursor) {
        super(peliculas);
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public Pelicula posicionDB(int posicion) {
        cursor.moveToPosition(posicion);
        return PeliculasBD.extraePelicula(cursor);
    }

    public int idPosicion(int posicion) {
        cursor.moveToPosition(posicion);
        return cursor.getInt(0);
    }

    public int posicionId(int id) {
        int pos = 0;
        while (pos < getItemCount() && idPosicion(pos) != id) pos++;
        if (pos >= getItemCount()) return -1;
        else return pos;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) { //
        // super.onBindViewHolder(holder, posicion);
        Pelicula pelicula= posicionDB(posicion);
        holder.personaliza(pelicula);
        holder.itemView.setTag(new Integer(posicion));
    }
    @Override
    public int getItemCount() { return cursor.getCount();
    }
}