package com.example.borja.ejercicio1;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{
    private Button botonEnviar;
    private EditText txtNombre;
    private RadioGroup radioGroupSexo;
    private String sexo;
    private TextView msjEdad;
    private RadioButton radio1, radio2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        getViews();
        setController();
    }

    private void getViews() {
        txtNombre = (EditText) findViewById(R.id.editTextNom);
        radioGroupSexo = (RadioGroup) findViewById(R.id.radioGroup);
        radio1 = (RadioButton) findViewById(R.id.radioButton);
        radio2 = (RadioButton) findViewById(R.id.radioButton2);
        botonEnviar = (Button)findViewById(R.id.buttonEnviar);
        msjEdad = (TextView) findViewById(R.id.msjEdad);
    }

    private void setController() {
        botonEnviar.setOnClickListener(this);
        radioGroupSexo.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if(sexoIsSelected() && nombreIsOk()){
            // Creamos un nuevo intent
            Intent intent = new Intent(this, Pantalla2.class);
            // Obtenemos la información que necesitamos pasar a la siguiente actividad
            String nombre = txtNombre.getText().toString();
            // Añadimos los parametros al intent
            intent.putExtra("nombre", nombre);
            intent.putExtra("sexo", this.sexo);
            // Iniciamos la actividad pasandole la intención con los parametros guardados
            startActivityForResult(intent,1);
        } else if (!nombreIsOk()) {
            Toast.makeText(getBaseContext(), "Introduzca un nombre válido", Toast.LENGTH_SHORT).show();
        } else if (!sexoIsSelected()) {
            Toast.makeText(getBaseContext(), "Seleccione sexo masculino o femenino", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean nombreIsOk() {
        return txtNombre.getText().toString().trim().length() > 0 ? true : false;
    }

    private boolean sexoIsSelected() {
        return radioGroupSexo.getCheckedRadioButtonId() !=  -1 ? true : false;
    }

   @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
       // Obtenemos el id del radio button seleccionado
       int selectedId = group.getCheckedRadioButtonId();
       // Encontramos el radio a partir del id anterior
       RadioButton radio = (RadioButton) findViewById(selectedId);
       // Seteamos el valor de la variable que pasaremos a Pantalla2
       sexo = (String) radio.getText();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK){
                showMessage(data);
                disableOptions();
            }
    }

    public void showMessage(Intent intent){
        Bundle extras = intent.getExtras();
        int edad = (int)extras.get("edad");// obtengo la edad
        this.msjEdad.setText(prepareMessage(edad));
    }

    private String prepareMessage(int edad) {
        if ( edad>=18 && edad<25) {
            return "Ya eres mayor de edad";
        } else if (edad>=25 && edad<=35){
            return "Estas en la flor de la vida";
        } else if (edad>35){
            return "ai, ai, ai...";
        }
        return "";
    }

    private void disableOptions() {
        radio1.setEnabled(false);
        radio2.setEnabled(false);
        txtNombre.setEnabled(false);
        botonEnviar.setEnabled(false);
    }
}