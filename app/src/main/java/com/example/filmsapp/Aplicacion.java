package com.example.filmsapp;

import android.app.Application;

import com.example.filmsapp.datos.PeliculasBD;
import com.example.filmsapp.vista.AdaptadorPeliculasDB;

public class Aplicacion extends Application {
    public PeliculasBD peliculas;
    public AdaptadorPeliculasDB adaptador;

    @Override
    public void onCreate() {
        super.onCreate();
        peliculas = new PeliculasBD(this);
        adaptador = new AdaptadorPeliculasDB(peliculas, peliculas.extraeCursor());
    }
}
