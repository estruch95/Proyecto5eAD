package com.example.estruch18.proyecto5ead;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertarEstudiante extends AppCompatActivity {

    private EditText nombre, edad, ciclo, curso, notaMedia;
    private Button btnAddEstudiante;

    private MyDbAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_estudiante);

        //Métodos
        declaracionViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insertar_estudiante, menu);
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
        nombre = (EditText)findViewById(R.id.et_Nombre);
        edad = (EditText)findViewById(R.id.et_Edad);
        ciclo = (EditText)findViewById(R.id.et_Ciclo);
        curso = (EditText)findViewById(R.id.et_Curso);
        notaMedia = (EditText)findViewById(R.id.et_NotaMedia);
        btnAddEstudiante = (Button)findViewById(R.id.btnAddEstudiante);
    }

    public void actionBtnAddEstudiante(View v){
        adapter = new MyDbAdapter(this);
        adapter.open();

        if(nombre.getText().toString().length()!= 0 && edad.getText().toString().length()!=0 && ciclo.getText().toString().length()!=0 && curso.getText().toString().length()!=0 && notaMedia.getText().toString().length()!=0){
            adapter.insertarEstudiante(nombre.getText().toString(), Integer.parseInt(edad.getText().toString()), ciclo.getText().toString(), Integer.parseInt(curso.getText().toString()), Integer.parseInt(notaMedia.getText().toString()));
            Toast.makeText(getApplicationContext(), "Estudiante insertado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }

    }
}
