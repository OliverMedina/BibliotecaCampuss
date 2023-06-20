package com.example.bibliotecacampus;

public class Libro {
    private String isbn;
    private String titulo;
    private int noDePaginas;
    private String portada;
    private String editorial;
    private String autores;

    public Libro(){

    }
    public Libro(String isbn, String titulo, int noDePaginas, String portada, String editorial, String autores) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.noDePaginas = noDePaginas;
        this.portada = portada;
        this.editorial = editorial;
        this.autores = autores;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNoDePaginas() {
        return noDePaginas;
    }

    public void setNoDePaginas(int noDePaginas) {
        this.noDePaginas = noDePaginas;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }
}
