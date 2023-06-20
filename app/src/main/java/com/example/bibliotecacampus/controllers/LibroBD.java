package com.example.bibliotecacampus.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.bibliotecacampus.Libro;

import java.util.ArrayList;
import java.util.List;

public class LibroBD extends SQLiteOpenHelper implements ILibroBD {
    Context contexto;
    private List<Libro> Libros = new ArrayList<>();

    public LibroBD(@Nullable Context context, @Nullable String name,
                   @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.contexto = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Libro (" +
                "isbn TEXT PRIMARY KEY, "+
                "titulo TEXT, " +
                "noDePaginas INTEGER, " +
                "portada TEXT, " +
                "editorial TEXT," +
                "autores TEXT )";
        db.execSQL( sql );
        String insert = "INSERT INTO Libro (isbn, titulo, noDePaginas, portada, editorial, autores) " +
                "VALUES ('202034567', " +
                "'El llano en llamas'," +
                " 50, " +
                "'llano.jpg', " +
                "'LIMUSA'," +
                " 'Juan Rulfo')";
        db.execSQL(insert);
        insert = "INSERT INTO Libro (isbn, titulo, noDePaginas, portada, editorial, autores) " +
                "VALUES ('9786075621678', " +
                "'Hotel California'," +
                " 280, " +
                "'hotel.jpeg', " +
                "'Harper Collins'," +
                " 'Ramon Valdes')";
        db.execSQL(insert);
        insert = "INSERT INTO Libro (isbn, titulo, noDePaginas, portada, editorial, autores) " +
                "VALUES ('9786070783128', " +
                "'Ultimos dias en berlin'," +
                " 640, " +
                "'berlin.jpg', " +
                "'Planeta Mexico'," +
                " 'Paloma Sanchez-Garnica')";
        db.execSQL(insert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public Libro getLibro(String isbn) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM libro WHERE isbn='" + isbn + "'";
        Cursor cursor = database.rawQuery(sql, null);
        try{
            if( cursor.moveToNext())
                return extraerLibro( cursor);
            else 
                return null;
        } catch(Exception e){
            Log.d("TAG", "Error elemento(isbn) LibroBD" + e.getMessage());
            throw e;
        }finally{
            if( cursor != null ) cursor.close();
        }
       
    }

    private Libro extraerLibro(Cursor cursor) {
        Libro libro = new Libro();
        libro.setIsbn( cursor.getString(0));
        libro.setTitulo( cursor.getString(1));
        libro.setNoDePaginas( cursor.getInt(2));
        libro.setPortada( cursor.getString(3));
        libro.setEditorial( cursor.getString(4));
        libro.setAutores( cursor.getString(5));
        return libro;
    }

    @Override
    public List<Libro> getLibros() {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM libro ORDER BY titulo ASC";
        Cursor cursor = database.rawQuery(sql, null);
        if( cursor.moveToFirst()){
            do{
               Libros.add(
                    new Libro(cursor.getString(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5))

               );
            }while(cursor.moveToNext() );
        }
        cursor.close();
        return Libros;

    }

    @Override
    public void agregar(Libro libro) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isbn",libro.getIsbn());
        values.put("titulo",libro.getTitulo());
        values.put("noDePaginas",libro.getNoDePaginas());
        values.put("portada",libro.getPortada());
        values.put("editorial",libro.getEditorial());
        values.put("autores",libro.getAutores());
        database.insert("libro",null,values);
    }

    @Override
    public void actualizar(String isbn, Libro libro) {
        SQLiteDatabase database = getWritableDatabase();
        String[] parametros = {isbn};

        ContentValues values = new ContentValues();
        values.put("isbn",libro.getIsbn());
        values.put("titulo",libro.getTitulo());
        values.put("noDePaginas",libro.getNoDePaginas());
        values.put("portada",libro.getPortada());
        values.put("editorial",libro.getEditorial());
        values.put("autores",libro.getAutores());
        database.update("libro",values,"isbn=?",parametros);
    }

    @Override
    public void eliminar(String isbn) {
       SQLiteDatabase database = getWritableDatabase();
       String[] parametros = {isbn};

       database.delete("libro", "isbn=?",parametros);
    }
}
