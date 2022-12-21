package com.example.filmsapp.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.filmsapp.Aplicacion;
import com.example.filmsapp.R;
import com.example.filmsapp.casos_uso.CasosUsoPelicula;
import com.example.filmsapp.datos.PeliculasBD;
import com.example.filmsapp.modelo.Pelicula;

public class VistaPeliculaActivity extends AppCompatActivity {
    private PeliculasBD peliculas;
    private AdaptadorPeliculasDB adaptador;
    private CasosUsoPelicula usoPelicula;
    private int pos, _id = -1;
    private Pelicula pelicula;
    private TextView txtNombre, txtDirector, txtSinopsis, txtAnio, txtGenero, txtPais;
    private ImageView logoGenero;
    final static int RESULTADO_EDITAR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_pelicula);
        peliculas = ((Aplicacion) getApplication()).peliculas;
        adaptador = ((Aplicacion) getApplication()).adaptador;
        usoPelicula = new CasosUsoPelicula(this, peliculas, adaptador);
        Bundle extras = getIntent().getExtras();
        if (extras != null) pos = extras.getInt("pos", 0);
        else pos = 0;
        _id = adaptador.idPosicion(pos);
        pelicula = adaptador.posicionDB(pos);//peliculas.elemento(pos);
        actualizaVistas();
    }
    public void actualizaVistas() {
        txtNombre = findViewById(R.id.nombrePelicula);
        txtNombre.setText(pelicula.getNombrePelicula());
        txtDirector = findViewById(R.id.txtdirector);
        txtDirector.setText(pelicula.getDirector());
        txtSinopsis = findViewById(R.id.sinopsis);
        txtSinopsis.setText(pelicula.getSinopsis());
        txtAnio=findViewById(R.id.anioEstreno);
        txtAnio.setText(Integer.toString(pelicula.getAnioLanzamiento()));
        logoGenero = findViewById(R.id.logo_tipo);
        logoGenero.setImageResource(pelicula.getTipoGenero().getImagen());
        txtGenero=findViewById(R.id.generoPelicula);
        txtGenero.setText(pelicula.getTipoGenero().getTexto());
        txtPais = findViewById(R.id.paisOrigen);
        txtPais.setText(pelicula.getPaisOrigen());
    }
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vista,menu);
        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_editar: usoPelicula.editar(pos,RESULTADO_EDITAR);
                return true;
            case R.id.accion_borrar: int id = adaptador.idPosicion(pos);
                usoPelicula.borrar(id);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULTADO_EDITAR) {
            pelicula = peliculas.elemento(_id);
            pos = adaptador.posicionId(_id);
            actualizaVistas();
            findViewById(R.id.scrollView1).invalidate();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
