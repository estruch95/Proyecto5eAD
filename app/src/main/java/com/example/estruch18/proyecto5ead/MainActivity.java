package com.example.estruch18.proyecto5ead;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvEstudiantes;
    private Button btnEstudiadntes, btnProfesores, btnInsertarEstudiante;

    private MyDbAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ejecución de métodos
        declaracionViews();

        adapter = new MyDbAdapter(this);
        adapter.open();
        /*
        adapter.insertarEstudiante("Ivan", 20, "DAM", 2, 10);
        adapter.insertarEstudiante("Pedro", 30, "DAW", 1, 10);
        adapter.insertarEstudiante("Juanmi", 40, "DAM", 1, 10);
        adapter.insertarEstudiante("David", 23, "DAM", 2, 8);
        adapter.insertarEstudiante("Adrian", 26, "DAM", 1, 7);
        */
    }

    public void mostrarEstudiantes(){
        ArrayAdapter<String> adaptador = null;
        String[] values = null;

        String URL = "content://com.example.estruch18.proyecto5ead/Estudiante";
        Uri estudiantes = Uri.parse(URL);
        Cursor cursor = getContentResolver().query(estudiantes, null, null, null, null);

        //Toast.makeText(getApplicationContext(), "Cursor "+cursor.getCount(), Toast.LENGTH_LONG).show();

        if(cursor != null && cursor.moveToFirst()){

            int indice_nombre = cursor.getColumnIndex(CustomContentProvider.NOMBRE);
            values = new String[cursor.getCount()];

            do{
                //Obtenemos el nombre
                String name = cursor.getString(indice_nombre);
                //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                values[cursor.getPosition()] = cursor.getPosition()+ " ("+name+")";
            }while (cursor.moveToNext());

            adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, values);
            lvEstudiantes.setAdapter(adaptador);
        }
        cursor.close();
    }

    public void mostrarProfesores(){
        ArrayAdapter<String> adaptador = null;
        String[] values = null;

        String URL = "content://com.example.estruch18.proyecto5ead/Profesor";
        Uri profesores = Uri.parse(URL);
        Cursor cursor = getContentResolver().query(profesores, null, null, null, null);

        //Toast.makeText(getApplicationContext(), "Cursor "+cursor.getCount(), Toast.LENGTH_LONG).show();

        if(cursor != null && cursor.moveToFirst()){

            int indice_nombre = cursor.getColumnIndex(CustomContentProvider.NOMBRE);
            values = new String[cursor.getCount()];

            do{
                //Obtenemos el nombre
                String name = cursor.getString(indice_nombre);
                //Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                values[cursor.getPosition()] = cursor.getPosition()+ " ("+name+")";
            }while (cursor.moveToNext());

            adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, values);
            lvEstudiantes.setAdapter(adaptador);
        }
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void declaracionViews(){
        lvEstudiantes = (ListView)findViewById(R.id.lvEstudiantes);
        btnEstudiadntes = (Button)findViewById(R.id.btnEstudiantes);
        btnProfesores = (Button)findViewById(R.id.btnProfesores);
        btnInsertarEstudiante = (Button)findViewById(R.id.btnInsertarEstudiante);
    }

    public void actionBtnEstudiantes(View v){
        mostrarEstudiantes();
    }

    public void actionBtnProfesores(View v){
        mostrarProfesores();
    }

    public void actionBtnInsertarEstudiante(View v){
        Intent actEstudiante = new Intent(getApplicationContext(), InsertarEstudiante.class);
        startActivity(actEstudiante);
    }
}
