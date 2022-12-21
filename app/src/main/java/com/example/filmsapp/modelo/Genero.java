package com.example.filmsapp.modelo;

import com.example.filmsapp.R;

public enum Genero {
    ACCION("Accion", R.drawable.accion), TERROR("Terror", R.drawable.terror), COMEDIA("Comedia", R.drawable.comedia), CIENCIA_FICCION("Ciencia Ficción", R.drawable.cienciaficcion), AVENTURAS("Aventura", R.drawable.aventuras);
    private final String texto;
    private final int imagen;

    Genero(String texto, int recurso) {
        this.texto = texto;
        this.imagen = recurso;
    }

    public String getTexto() {
        return texto;
    }

    public int getImagen() {
        return imagen;
    }

    public static String[] getNombres() {
        String[] resultado = new String[Genero.values().length];
        for (Genero tipo : Genero.values()) {
            resultado[tipo.ordinal()] = tipo.texto;
        }
        return resultado;
    }
}