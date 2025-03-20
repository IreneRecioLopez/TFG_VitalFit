package com.tfg.vitalfit.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.viewModel.HospitalViewModel;
import com.tfg.vitalfit.viewModel.NutricionistaViewModel;

import java.util.ArrayList;
import java.util.List;

public class RegistroNutricionistaActivity extends AppCompatActivity {

    private HospitalViewModel hViewModel;
    private NutricionistaViewModel nViewModel;
    private Toolbar toolbar;
    private AutoCompleteTextView dropdownProvincia, dropdownHospital;
    private Button btnGuardarDatos;
    private EditText edtName, edtApellido1, edtApellido2, edtDNI, edtTlf, edtPassword, edtPasswordVal;
    private TextInputLayout txtInputName, txtInputApellido1, txtInputApellido2, txtInputDNI, txtInputTlf,
            txtInputPassword, txtInputPasswordVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_nutricionista);
        this.initViewModel();
        this.init();
    }

    private void initViewModel(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        nViewModel = vmp.get(NutricionistaViewModel.class);
        hViewModel = vmp.get(HospitalViewModel.class);
    }

    private void init(){
        toolbar = findViewById(R.id.toolbarRegNutricionista);
        btnGuardarDatos = findViewById(R.id.btnGuardarDatosN);
        edtName = findViewById(R.id.edtNameN);
        edtApellido1 = findViewById(R.id.edtPrimerApellidoN);
        edtApellido2 = findViewById(R.id.edtSegundoApellidoN);
        edtDNI = findViewById(R.id.edtDNIN);
        edtTlf = findViewById(R.id.edtTelefonoN);
        edtPassword = findViewById(R.id.edtPasswordN);
        edtPasswordVal = findViewById(R.id.edtPasswordValN);
        //TextInputLayout
        txtInputName = findViewById(R.id.txtInputNameN);
        txtInputApellido1 = findViewById(R.id.txtInputPrimerApellidoN);
        txtInputApellido2 = findViewById(R.id.txtInputSegundoApellidoN);
        txtInputDNI = findViewById(R.id.txtInputDNIN);
        txtInputTlf = findViewById(R.id.txtInputTelefonoN);
        txtInputPassword = findViewById(R.id.txtInputPasswordN);
        txtInputPasswordVal = findViewById(R.id.txtInputPasswordValN);
        //AutoCompleteTextView
        dropdownProvincia = findViewById(R.id.dropdownProvinciaN);
        dropdownHospital = findViewById(R.id.dropdownHospitalN);
        // Habilitar el botón de regreso en el Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Selección de provincias
        String[] provincias = getResources().getStringArray(R.array.provincias);
        ArrayAdapter arrayProvincias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, provincias);
        dropdownProvincia.setAdapter(arrayProvincias);
        dropdownProvincia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String provincia = s.toString();
                obtenerHospitalesPorProvincia(provincia);
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

    private void obtenerHospitalesPorProvincia(String provincia) {
        hViewModel.hospitalPorProvincia(provincia).observe(this, new Observer<List<Hospital>>(){
            @Override
            public void onChanged(List<Hospital> hospitales){
                if(hospitales != null){
                    listaNombresHospitales(hospitales);
                }
            }
        });
    }

    private void listaNombresHospitales(List<Hospital> hospitales){
        List<String> nombresHospitales = new ArrayList<>();
        for(Hospital hospital: hospitales){
            nombresHospitales.add(hospital.getNombre());
        }
        ArrayAdapter<String> arrayHospitales = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresHospitales);
        dropdownHospital.setAdapter(arrayHospitales);
    }
}