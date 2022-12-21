package com.example.filmsapp.casos_uso;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.example.filmsapp.Aplicacion;
import com.example.filmsapp.datos.PeliculasBD;
import com.example.filmsapp.modelo.Pelicula;
import com.example.filmsapp.vista.AdaptadorPeliculasDB;
import com.example.filmsapp.vista.EdicionPeliculaActivity;
import com.example.filmsapp.vista.VistaPeliculaActivity;

public class CasosUsoPelicula {
    private Activity activity;
    private PeliculasBD peliculas;
    private AdaptadorPeliculasDB adaptador;

    public CasosUsoPelicula(Activity activity, PeliculasBD peliculas, AdaptadorPeliculasDB adaptador) {
        this.activity = activity;
        this.peliculas = peliculas;
        this.adaptador = adaptador;
    }

    public void mostrar(int pos) {
        Intent mostrar = new Intent(activity, VistaPeliculaActivity.class);
        mostrar.putExtra("pos", pos);
        activity.startActivity(mostrar);
    }

    public void editar(int pos, int codigoSolicitado) {
        Intent intent_ed_lugar = new Intent(activity, EdicionPeliculaActivity.class);
        intent_ed_lugar.putExtra("pos", pos);
        activity.startActivityForResult(intent_ed_lugar, codigoSolicitado);
    }

    public void borrar(final int id) {
        new AlertDialog.Builder(activity).setTitle("Borrado de pelicula").setMessage("¿Seguro de eliminar pelicula?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                peliculas.borrar(id);
                adaptador = ((Aplicacion) activity.getApplicationContext()).adaptador;
                adaptador.setCursor(peliculas.extraeCursor());
                adaptador.notifyDataSetChanged();
                activity.finish();
            }
        }).setNegativeButton("Cancelar", null).show();
    }

    public void guardar(int id, Pelicula nuevaPelicula) {
        peliculas.actualiza(id, nuevaPelicula);
        adaptador.setCursor(peliculas.extraeCursor());
        adaptador.notifyDataSetChanged();
    }

    public void nuevo() {
        int id = peliculas.nuevo();
        Log.d("TAG", "id nueva peli " + id);
        Intent nuevaPeli = new Intent(activity, EdicionPeliculaActivity.class);
        nuevaPeli.putExtra("_id", id);
        activity.startActivity(nuevaPeli);
    }
}