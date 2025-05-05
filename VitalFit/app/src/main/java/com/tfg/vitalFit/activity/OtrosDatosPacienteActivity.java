package com.tfg.vitalfit.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tfg.vitalfit.R;

public class OtrosDatosPacienteActivity extends AppCompatActivity {

    private AutoCompleteTextView dropdownTipoDatos;
    private Toolbar toolbar;
    private LinearLayout alergiasLayout, operacionesLayout, otrasObservacionesLayout;
    private String tipoDato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otros_datos_paciente);
        this.initViewModel();
        this.init();

    }

    private void initViewModel(){

    }

    private void init(){
        toolbar = findViewById(R.id.toolbarOtrosDatosPaciente);
        alergiasLayout = findViewById(R.id.layoutAlergias);
        operacionesLayout = findViewById(R.id.layoutOperaciones);
        otrasObservacionesLayout = findViewById(R.id.layoutOtrasObservaciones);
        dropdownTipoDatos = findViewById(R.id.dropdownTiposDato);

        setSupportActionBar(toolbar);

        // Habilitar el botón de regreso en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Adapter para provincias
        String[] tipoDatos = getResources().getStringArray(R.array.otrosDatos);
        ArrayAdapter<String> adapterProvincia = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, tipoDatos
        );
        dropdownTipoDatos.setAdapter(adapterProvincia);

        dropdownTipoDatos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tipoDato = s.toString();
                if(tipoDato.equals("Alergias")){
                    alergiasLayout.setVisibility(View.VISIBLE);
                    operacionesLayout.setVisibility(View.GONE);
                    otrasObservacionesLayout.setVisibility(View.GONE);
                }else if(tipoDato.equals("Operaciones")){
                    alergiasLayout.setVisibility(View.GONE);
                    operacionesLayout.setVisibility(View.VISIBLE);
                    otrasObservacionesLayout.setVisibility(View.GONE);
                }else if(tipoDato.equals("Otras Observaciones")){
                    alergiasLayout.setVisibility(View.GONE);
                    operacionesLayout.setVisibility(View.GONE);
                    otrasObservacionesLayout.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // Capturar el clic en el botón de regreso
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // Este es el ID del botón de navegación
            onBackPressed(); // Regresa a la pantalla anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}