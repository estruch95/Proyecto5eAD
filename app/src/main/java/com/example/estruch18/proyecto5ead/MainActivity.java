package com.example.estruch18.proyecto5ead;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView lvEstudiantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvEstudiantes = (ListView)findViewById(R.id.lvEstudiantes);

        //Ejecución de métodos
        mostrarEstudiantes();
    }

    public void mostrarEstudiantes(){

        String URL = "content://com.example.estruch18.proyecto5ead/Estudiante/2";
        Uri estudiantes = Uri.parse(URL);
        Cursor cursor = getContentResolver().query(estudiantes, null, null, null, null);

        Toast.makeText(getApplicationContext(), "Cursor "+cursor.getCount(), Toast.LENGTH_LONG).show();

        if(cursor != null && cursor.moveToFirst()){
            int indice_nombre = cursor.getColumnIndex(CustomContentProvider.NOMBRE);
            String[] values = new String[cursor.getCount()];
            do{
                String name = cursor.getString(indice_nombre);
                values[cursor.getPosition()] = cursor.getPosition()+ " ("+name+")";
            }while (cursor.moveToNext());

            ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, values);
            lvEstudiantes.setAdapter(adaptador);
        }
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
}
