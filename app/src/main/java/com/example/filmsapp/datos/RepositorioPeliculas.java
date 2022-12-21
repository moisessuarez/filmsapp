package com.example.filmsapp.datos;

import com.example.filmsapp.modelo.Pelicula;

public interface RepositorioPeliculas {

    Pelicula elemento(int id); //Devuelve el elemento dado su id
    int nuevo(); //Añade un elemento en blanco y devuelve su id
    void borrar(int id); //Elimina el elemento con el id indicado
    int tamanio(); //Devuelve el número de elementos
    void actualiza(int id, Pelicula movie); //Reemplaza un elemento
}
