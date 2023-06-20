package com.example.bibliotecacampus.controllers;

import com.example.bibliotecacampus.Libro;

import java.util.List;

public interface ILibroBD {

    Libro getLibro(String isbn);//devuelve el libro segun su isbn
    List<Libro> getLibros();//devuelve una lista con todos los libros
    void agregar(Libro libro);//añade un libro
    void actualizar(String isbn,Libro libro);//actualiza un libro

    void eliminar(String isbn);//elimina un libro segun su isbn
}
