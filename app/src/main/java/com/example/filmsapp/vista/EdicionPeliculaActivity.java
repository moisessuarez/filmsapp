package com.example.filmsapp.vista;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.filmsapp.Aplicacion;
import com.example.filmsapp.R;
import com.example.filmsapp.casos_uso.CasosUsoPelicula;
import com.example.filmsapp.datos.PeliculasBD;
import com.example.filmsapp.modelo.Genero;
import com.example.filmsapp.modelo.Pelicula;
import com.google.android.material.textfield.TextInputEditText;

public class EdicionPeliculaActivity extends AppCompatActivity {
    private PeliculasBD peliculas;
    private AdaptadorPeliculasDB adaptador;
    private CasosUsoPelicula usoPelicula;
    private int pos, _id;
    private Pelicula pelicula;
    private TextInputEditText nombrePelicula, directorPelicula, paisOrigen, anioEstreno, sinopsis;
    private Spinner genero;
    private Toast msnToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_pelicula);
        peliculas = ((Aplicacion) getApplication()).peliculas;
        adaptador = ((Aplicacion) getApplication()).adaptador;
        usoPelicula = new CasosUsoPelicula(this, peliculas, adaptador);
        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("pos", -1);
        _id = extras.getInt("_id", -1);
        if (_id != -1) {
            setTitle("Nueva Película");
            pelicula = peliculas.elemento(_id);
        } else
            pelicula = adaptador.posicionDB(pos); //peliculas.elemento(pos);
        actualizaVistas();
    }   public void actualizaVistas(){
        nombrePelicula = findViewById(R.id.inputEditTextnombrePelicula);
        nombrePelicula.setText(pelicula.getNombrePelicula());
        directorPelicula = findViewById(R.id.inputEditTextDirector);
        directorPelicula.setText(pelicula.getDirector());
        paisOrigen = findViewById(R.id.inputEditTextOrigen);
        paisOrigen.setText(pelicula.getPaisOrigen());
        anioEstreno = findViewById(R.id.inputEditTextAnio);
        anioEstreno.setText(Integer.toString(pelicula.getAnioLanzamiento()));
        sinopsis = findViewById(R.id.inputEditTextSinopsis);
        sinopsis.setText(pelicula.getSinopsis());
        genero = findViewById(R.id.spinnerGenero);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Genero.getNombres());
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genero.setAdapter(adaptador);
        genero.setSelection(pelicula.getTipoGenero().ordinal());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edicion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_guardar:
                pelicula.setNombrePelicula(nombrePelicula.getText().toString());
                pelicula.setTipoGenero(Genero.values()[genero.getSelectedItemPosition()]);
                pelicula.setDirector(directorPelicula.getText().toString());
                pelicula.setAnioLanzamiento(Integer.parseInt(anioEstreno.getText().toString()));
                pelicula.setSinopsis(sinopsis.getText().toString());
                pelicula.setPaisOrigen(paisOrigen.getText().toString());
                msnToast = Toast.makeText(getApplicationContext(), "Cambios guardados exitosamente", Toast.LENGTH_LONG);
                msnToast.setGravity(Gravity.CENTER, 0, 0);
                msnToast.show();
                if (_id == -1) _id = adaptador.idPosicion(pos);
                usoPelicula.guardar(_id, pelicula);
                finish();
                return true;
            case R.id.accion_cancelar: //
                msnToast = Toast.makeText(getApplicationContext(),"Canceló no hay nueva película",Toast.LENGTH_LONG);
                msnToast.setGravity(Gravity.CENTER,0,0); msnToast.show();
                if (_id!=-1)peliculas.borrar(_id); finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}