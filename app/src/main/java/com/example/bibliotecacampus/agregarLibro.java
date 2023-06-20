package com.example.bibliotecacampus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bibliotecacampus.controllers.LibroBD;

public class agregarLibro extends AppCompatActivity {

    Context context;
    EditText txtISBN;
    EditText txtTitulo;
    EditText txtNoPaginas;
    EditText txtPortada;
    EditText txtEditorial;
    EditText txtAutores;
    Bundle bolsa;
    LibroBD libroBD;
    Libro libro;
    Button butonagregar;
    Button buttonguardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_libro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        butonagregar = findViewById(R.id.btnAgregar);
        buttonguardar = findViewById(R.id.btnGuardar);
       buttonguardar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           guardar();
           }
       });


        butonagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               agregar();
            }
        });
       init();
    }


    private void init() {
        context = getApplicationContext();
        txtISBN = findViewById(R.id.txtISBN);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtNoPaginas = findViewById(R.id.txtNoPaginas);
        txtPortada = findViewById(R.id.txtPortada);
        txtEditorial = findViewById(R.id.txtEditorial);
        txtAutores = findViewById(R.id.txtAutores);

        Intent i = getIntent();
        bolsa = i.getExtras();
        String isbn = bolsa.getString("isbn");
        if (isbn != "") {
            txtISBN.setText(bolsa.getString("isbn"));
            txtTitulo.setText(bolsa.getString("titulo"));
            txtNoPaginas.setText(String.valueOf(bolsa.getInt("noDePaginas")));
            txtPortada.setText(bolsa.getString("portada"));
            txtEditorial.setText(bolsa.getString("editorial"));
            txtAutores.setText(bolsa.getString("autores"));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void limpiarCampos() {
        txtISBN.setText("");
        txtTitulo.setText("");
        txtNoPaginas.setText("");
        txtPortada.setText("");
        txtEditorial.setText("");
        txtAutores.setText("");
    }

    private Libro llenarLibro() {
        Libro libro = new Libro();
        String i = txtISBN.getText().toString();
        String t = txtTitulo.getText().toString();
        int n = Integer.valueOf(txtNoPaginas.getText().toString());
        String p = txtPortada.getText().toString();
        String e = txtEditorial.getText().toString();
        String a = txtAutores.getText().toString();

        libro.setIsbn(i);
        libro.setTitulo(t);
        libro.setNoDePaginas(n);
        libro.setPortada(p);
        libro.setEditorial(e);
        libro.setAutores(a);

        return libro;
    }

    private void agregar() {
        libroBD = new LibroBD(context, "libroBD.db", null, 1);
        Libro libro = llenarLibro();
        if (libro.getIsbn() != "") {
            libroBD.agregar(libro);
            Toast.makeText(context, "Libro agregado", Toast.LENGTH_LONG).show();
        }
    }

    private void guardar() {
        libroBD = new LibroBD(context, "libroBD.db", null, 1);
        Libro libro = llenarLibro();
        if (libro.getIsbn() != "") {
            libroBD.actualizar(libro.getIsbn(), libro);
            Toast.makeText(context, "Libro actualizado", Toast.LENGTH_LONG).show();
        }

    }



}
