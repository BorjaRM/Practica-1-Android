package com.example.borja.ejercicio1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Pantalla2 extends AppCompatActivity implements View.OnClickListener{
    private TextView msjSaludo;
    private EditText txtEdad;
    private Button botonContinuar,botonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);

        getViews();
        getInfoFromIntent();
        setController();
    }

    private void getViews() {
        msjSaludo = (TextView) findViewById(R.id.textSaludo);
        txtEdad = (EditText) findViewById(R.id.editTextEdad);
        botonContinuar = (Button)findViewById(R.id.buttonContinuar);
        botonCancelar = (Button)findViewById(R.id.buttonCancelar);
    }

    private void getInfoFromIntent() {
        // Obtiene los datos de la Activity Principal y muestra un mensaje por pantalla
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) { // comprobamos si el intent contiene datos
            String nombre = (String)extras.get("nombre");// obtengo el nombre
            String sexo = (String)extras.get("sexo");// obtengo el sexo

            setMsjSaludo(nombre);
        }
    }

    public void setMsjSaludo (String nombre){
        msjSaludo.setText("Hola "+nombre+", indicame los siguientes datos:");
    }

    private void setController() {
        botonContinuar.setOnClickListener(this);
        botonCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Comprobamos si el usuario ha hecho click en continuar o en cancelar
        if (v.getId() == botonContinuar.getId()) {
            if(edadIsOk()) {
                continuar();
                finish();
            }
        } else {
            cancelar();
            finish();
        }
    }

    // edadIsOk comprueba si el campo edad ha sido rellenado y si contiene un numero
    private boolean edadIsOk() {
        String edadAux  = txtEdad.getText().toString().trim();
        if (!edadIsEmpty(edadAux)) {
            try {
                int edad = Integer.parseInt(edadAux);
                if (edadIsPositive(edad))
                    return true;
                else
                    Toast.makeText(getBaseContext(), "Edad debe ser un valor númerico y positivo", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException nfe){
                Toast.makeText(getBaseContext(), "Edad debe ser un valor númerico", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else
            Toast.makeText(getBaseContext(), "Introduzca su edad", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean edadIsEmpty(String edad) {
        return edad.length() > 0 ? false : true;
    }

    private boolean edadIsPositive(int edad) {
        return edad > 0;
    }

    public void continuar() {
        // Obtenemos la información que necesitamos pasar a la siguiente actividad
        int edad = Integer.parseInt(txtEdad.getText().toString());
        // Añadimos los parametros al intent
        Intent i = getIntent();
        i.putExtra("edad", edad);
        // Iniciamos la actividad pasandole el intent con los parametros guardados
       setResult(RESULT_OK,i);
    }

    public void cancelar() {
        setResult(RESULT_CANCELED);
    }
}
