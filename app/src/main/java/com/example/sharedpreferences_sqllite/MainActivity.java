package com.example.sharedpreferences_sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextNombre = findViewById(R.id.edit_text_nombre);
        EditText editTextApellido = findViewById(R.id.edit_text_apellido);
        EditText editTextContacto = findViewById(R.id.edit_text_contacto);
        Button botonGuardar = findViewById(R.id.boton_guardar);
        Button botonBuscar = findViewById(R.id.boton_Buscar);


        SharedPreferences sharedPreferences = getSharedPreferences("MisDatos", MODE_PRIVATE);
        String nombre = sharedPreferences.getString("nombre", "");
        String apellido = sharedPreferences.getString("apellido", "");
        String contacto = sharedPreferences.getString("contacto", "");

        editTextNombre.setText(nombre);
        editTextApellido.setText(apellido);
        editTextContacto.setText(contacto);
        DBHelper databaseHelper = new DBHelper(this);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de texto
                String nombre = editTextNombre.getText().toString();
                String apellido = editTextApellido.getText().toString();
                String contacto = editTextContacto.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nombre", nombre);
                editor.putString("apellido", apellido);
                editor.putString("contacto", contacto);
                editor.apply();


                // Guardar los valores en la base de datos
                databaseHelper.insertarDatos(nombre, apellido, contacto);

                Toast.makeText(MainActivity.this, "Datos guardados", Toast.LENGTH_SHORT).show();
            }
    });

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos de texto
                String nombre = editTextNombre.getText().toString();
                String apellido = editTextApellido.getText().toString();
                String contacto = editTextContacto.getText().toString();


                if (databaseHelper.buscarDatos(nombre, apellido, contacto)){
                    Toast.makeText(MainActivity.this, "Datos encontrados", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Datos no encontrados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }}
