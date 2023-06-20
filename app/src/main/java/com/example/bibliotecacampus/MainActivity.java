package com.example.bibliotecacampus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.example.bibliotecacampus.controllers.LibroBD;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private static final int REQUEST_IMAGE_CAPTURE = 300;

    private Button escanearButton;
    private Button buscarButton;

    LibroBD libroBD;
    Context context;
    EditText txtIsbn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        escanearButton = findViewById(R.id.escanearButton);
        buscarButton = findViewById(R.id.buscarButton);
        txtIsbn = findViewById(R.id.txtIsbn);
        escanearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();

            }
        });

        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtienen las referencias a los elementos del diseño XML
                LinearLayout panelLinearLayout = findViewById(R.id.panelLinearLayout);

                panelLinearLayout.removeAllViews();

                String isbn = txtIsbn.getText().toString();
                Libro libro = buscarLibroPorISBN(isbn);
                if(libro != null){

                    // Crea dinámicamente los elementos de texto para mostrar la información del libro
                    TextView isbnTextView = new TextView(MainActivity.this);
                    isbnTextView.setText("ISBN: " + libro.getIsbn());
                    panelLinearLayout.addView(isbnTextView);

                    TextView tituloTextView = new TextView(MainActivity.this);
                    tituloTextView.setText("Título: " + libro.getTitulo());
                    panelLinearLayout.addView(tituloTextView);

                    TextView noDePaginasTextView = new TextView(MainActivity.this);
                    noDePaginasTextView.setText("Número de páginas: " + libro.getNoDePaginas());
                    panelLinearLayout.addView(noDePaginasTextView);

                    TextView editorialTextView = new TextView(MainActivity.this);
                    editorialTextView.setText("Editorial: " + libro.getEditorial());
                    panelLinearLayout.addView(editorialTextView);

                    TextView autoresTextView = new TextView(MainActivity.this);
                    autoresTextView.setText("Autores: " + libro.getAutores());
                    panelLinearLayout.addView(autoresTextView);

                    ImageView portadaImageView = new ImageView(MainActivity.this);
                    String nombreRecurso = libro.getPortada();
                    int idRecurso = getResources().getIdentifier(nombreRecurso, "drawable", getPackageName());
                    portadaImageView.setImageResource(idRecurso);
                    panelLinearLayout.addView(portadaImageView);

                    Button editarButton = new Button(MainActivity.this);
                    editarButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.editar,0,0,0);
                    panelLinearLayout.addView(editarButton);

                    editarButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Bundle bolsa = new Bundle();
                            bolsa.putString("isbn", libro.getIsbn());
                            bolsa.putString("titulo", libro.getTitulo());
                            bolsa.putInt("noDePaginas",libro.getNoDePaginas());
                            bolsa.putString("portada", libro.getPortada());
                            bolsa.putString("editorial", libro.getEditorial());
                            bolsa.putString("autores", libro.getAutores());

                            Intent i = new Intent( context, agregarLibro.class );
                            i.putExtras(bolsa);
                            startActivity(i);
                        }
                    });

                    Button eliminarButton = new Button(MainActivity.this);
                    eliminarButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.eliminar,0,0,0);
                    panelLinearLayout.addView(eliminarButton);
                    eliminarButton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                        libroBD = new LibroBD(context, "libro.db",null,1);
                        libroBD.eliminar(libro.getIsbn());
                            Toast.makeText(context, "Libro eliminado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(context, "No existe ese libro", Toast.LENGTH_LONG).show();
                }
            }

            private Libro buscarLibroPorISBN(String isbn) {
                libroBD = new LibroBD(context, "Libro.db",null,1);
                Libro libro = libroBD.getLibro(isbn);

                return libro;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
      getMenuInflater().inflate(R.menu.mymenu, menu);
      return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(MainActivity.this, agregarLibro.class);
        startActivity(intent);
        return true;
    }


    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

}
