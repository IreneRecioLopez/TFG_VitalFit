package com.tfg.vitalfit.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.adapter.AlergiasAdapter;
import com.tfg.vitalfit.adapter.ObservacionesAdapter;
import com.tfg.vitalfit.adapter.OperacionesAdapter;
import com.tfg.vitalfit.entity.service.Alergias;
import com.tfg.vitalfit.entity.service.Observaciones;
import com.tfg.vitalfit.entity.service.Operaciones;
import com.tfg.vitalfit.entity.service.Usuario;

import java.util.List;

public class OtrosDatosPacienteActivity extends AppCompatActivity {

    private RecyclerView recyclerAlergias, recyclerOperaciones, recyclerOtrasObservaciones;
    private AlergiasAdapter alergiasAdapter;
    private OperacionesAdapter operacionesAdapter;
    private ObservacionesAdapter observacionesAdapter;
    private AutoCompleteTextView dropdownTipoDatos;
    private TextView noHayAlergias, noHayOperaciones, noHayOtrasObservaciones;
    private Toolbar toolbar;
    private LinearLayout alergiasLayout, operacionesLayout, otrasObservacionesLayout;
    private String tipoDato;
    private Usuario paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otros_datos_paciente);
        this.initRecyclers();
        this.init();
    }

    private void initRecyclers(){
        recyclerAlergias = findViewById(R.id.recyclerAlergias);
        recyclerAlergias.setLayoutManager(new LinearLayoutManager(this));
        recyclerOperaciones = findViewById(R.id.recyclerOperaciones);
        recyclerOperaciones.setLayoutManager(new LinearLayoutManager(this));
        recyclerOtrasObservaciones = findViewById(R.id.recyclerOtrasObservaciones);
        recyclerOtrasObservaciones.setLayoutManager(new LinearLayoutManager(this));
    }

    private void init(){
        toolbar = findViewById(R.id.toolbarOtrosDatosPaciente);
        alergiasLayout = findViewById(R.id.layoutAlergias);
        operacionesLayout = findViewById(R.id.layoutOperaciones);
        otrasObservacionesLayout = findViewById(R.id.layoutOtrasObservaciones);
        noHayAlergias = findViewById(R.id.noHayAlergias);
        noHayOperaciones = findViewById(R.id.noHayOperaciones);
        noHayOtrasObservaciones = findViewById(R.id.noHayObservaciones);
        dropdownTipoDatos = findViewById(R.id.dropdownTiposDato);

        setSupportActionBar(toolbar);

        // Habilitar el botón de regreso en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        obtenerDatosPaciente();

        // Adapter para otros tipoos de datos
        String[] tipoDatos = getResources().getStringArray(R.array.otrosDatos);
        ArrayAdapter<String> adapterOtrosDatos = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, tipoDatos
        );
        dropdownTipoDatos.setAdapter(adapterOtrosDatos);
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
                    initAlergias();
                }else if(tipoDato.equals("Operaciones")){
                    alergiasLayout.setVisibility(View.GONE);
                    operacionesLayout.setVisibility(View.VISIBLE);
                    otrasObservacionesLayout.setVisibility(View.GONE);
                    initOperaciones();
                }else if(tipoDato.equals("Otras Observaciones")){
                    alergiasLayout.setVisibility(View.GONE);
                    operacionesLayout.setVisibility(View.GONE);
                    otrasObservacionesLayout.setVisibility(View.VISIBLE);
                    initOtrasObservaciones();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initAlergias(){
        if(paciente != null){
            List<Alergias> alergias = paciente.getPaciente().getAlergias();
            if(alergias.isEmpty()){
                noHayAlergias.setVisibility(View.VISIBLE);
            }
            alergiasAdapter = new AlergiasAdapter(this, alergias);
            recyclerAlergias.setAdapter(alergiasAdapter);
        }
    }

    private void initOperaciones(){
        if(paciente != null){
            List<Operaciones> operaciones = paciente.getPaciente().getOperaciones();
            if(operaciones.isEmpty()){
                noHayOperaciones.setVisibility(View.VISIBLE);
            }
            operacionesAdapter = new OperacionesAdapter(this, operaciones);
            recyclerOperaciones.setAdapter(operacionesAdapter);
        }
    }

    private void initOtrasObservaciones(){
        if(paciente != null){
            List<Observaciones> observaciones = paciente.getPaciente().getObservaciones();
            if(observaciones.isEmpty()){
                noHayOtrasObservaciones.setVisibility(View.VISIBLE);
            }
            Log.d("Operaciones paciente", observaciones.toString());
            observacionesAdapter = new ObservacionesAdapter(this, observaciones);
            recyclerOtrasObservaciones.setAdapter(observacionesAdapter);
        }
    }

    private void obtenerDatosPaciente(){
        paciente = (Usuario) getIntent().getSerializableExtra("paciente");
        Log.d("Paciente recibido otros datos", paciente.toString());
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