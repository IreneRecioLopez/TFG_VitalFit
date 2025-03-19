package com.tfg.vitalfit.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.viewModel.HospitalViewModel;
import com.tfg.vitalfit.viewModel.MedicoViewModel;

import java.util.ArrayList;
import java.util.List;

public class RegistroMedicoActivity extends AppCompatActivity {

    private HospitalViewModel hViewModel;
    private MedicoViewModel mViewModel;
    private Toolbar toolbar;
    private AutoCompleteTextView dropdownProvincia, dropdownHospital;
    private Button btnGuardarDatos;
    private EditText edtName, edtApellido1, edtApellido2, edtDNI, edtTlf, edtPassword, edtPasswordVal;
    private TextInputLayout txtInputName, txtInputApellido1, txtInputApellido2, txtInputDNI, txtInputTlf,
                            txtInputPassword, txtInputPasswordVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medico);
        this.initViewModel();
        this.init();
        //this.spinners();
    }

    /*private void spinners(){
        //Lista de provincias
        String[] provincias = getResources().getStringArray(R.array.provincias);
        ArrayAdapter arrayProvincias = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, provincias);
        dropdownProvincia.setAdapter(arrayProvincias);

    }*/

    private void initViewModel(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        mViewModel = vmp.get(MedicoViewModel.class);
        hViewModel = vmp.get(HospitalViewModel.class);
    }

    private void init(){
        toolbar = findViewById(R.id.toolbarRegMedico);
        btnGuardarDatos = findViewById(R.id.btnGuardarDatosM);
        edtName = findViewById(R.id.edtNameM);
        edtApellido1 = findViewById(R.id.edtPrimerApellidoM);
        edtApellido2 = findViewById(R.id.edtSegundoApellidoM);
        edtDNI = findViewById(R.id.edtDNIM);
        edtTlf = findViewById(R.id.edtTelefonoM);
        edtPassword = findViewById(R.id.edtPasswordM);
        edtPasswordVal = findViewById(R.id.edtPasswordValM);
        //TextInputLayout
        txtInputName = findViewById(R.id.txtInputNameP);
        txtInputApellido1 = findViewById(R.id.txtInputPrimerApellidoM);
        txtInputApellido2 = findViewById(R.id.txtInputSegundoApellidoM);
        txtInputDNI = findViewById(R.id.txtInputDNIM);
        txtInputTlf = findViewById(R.id.txtInputTelefonoM);
        txtInputPassword = findViewById(R.id.txtInputPasswordM);
        txtInputPasswordVal = findViewById(R.id.txtInputPasswordValM);
        //AutoCompleteTextView
        dropdownProvincia = findViewById(R.id.dropdownProvinciaM);
        dropdownHospital = findViewById(R.id.dropdownHospitalM);

        // Habilitar el botón de regreso en el Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Selección de provincias
        String[] provincias = getResources().getStringArray(R.array.provincias);
        ArrayAdapter arrayProvincias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, provincias);
        dropdownProvincia.setAdapter(arrayProvincias);
        Log.d("Provincia elegia", "aqui paro");

        dropdownProvincia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String provincia = s.toString();
                Log.d("Provincia elegia",  provincia);
                obtenerHospitalesPorProvincia(provincia);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //obtener hospitales de la provincia elegida
        /*dropdownProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String provincia = (String) parent.getItemAtPosition(position);
                Log.d("Provincia elegia",  provincia);
                obtenerHospitalesPorProvincia(provincia);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */
        /*btnGuardarDatos.setOnClickListener(v -> {
            this.guardarDatos();
        });*/

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
                    Log.d("Hospitales", "Número de hospitales: " + hospitales.size());

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