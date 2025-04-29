package com.tfg.vitalfit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.Security;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.HospitalViewModel;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class RegistroMedicoNutricionistaActivity extends AppCompatActivity {

    private HospitalViewModel hViewModel;
    private UsuarioViewModel uViewModel;
    private Toolbar toolbar;
    private AutoCompleteTextView dropdownProvincia, dropdownHospital;
    private Button btnGuardarDatos;
    private EditText edtName, edtApellido1, edtApellido2, edtDNI, edtTlf, edtPassword, edtPasswordVal;
    private TextInputLayout txtInputName, txtInputApellido1, txtInputDNI, txtInputTlf,
                            txtInputPassword, txtInputPasswordVal, txtInputProvincia, txtInputHospital;


    private String hospital, provincia;

    String rol = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medico_nutricionista);
        this.initViewModel();
        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        rol = extra.getString("ROL");
        this.init();
        //this.spinners();
    }

    private void initViewModel(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        uViewModel = vmp.get(UsuarioViewModel.class);
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
        txtInputName = findViewById(R.id.txtInputNameM);
        txtInputApellido1 = findViewById(R.id.txtInputPrimerApellidoM);
        txtInputDNI = findViewById(R.id.txtInputDNIM);
        txtInputTlf = findViewById(R.id.txtInputTelefonoM);
        txtInputPassword = findViewById(R.id.txtInputPasswordM);
        txtInputPasswordVal = findViewById(R.id.txtInputPasswordValM);
        txtInputProvincia = findViewById(R.id.txtInputProvinciaM);
        txtInputHospital = findViewById(R.id.txtInputHospitalM);
        //AutoCompleteTextView
        dropdownProvincia = findViewById(R.id.dropdownProvinciaM);
        dropdownHospital = findViewById(R.id.dropdownHospitalM);

        // Habilitar el botón de regreso en el Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Registrar " + rol);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Selección de provincias
        String[] provincias = getResources().getStringArray(R.array.provincias);
        ArrayAdapter arrayProvincias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, provincias);
        dropdownProvincia.setAdapter(arrayProvincias);

        btnGuardarDatos.setOnClickListener(v -> {
            this.guardarDatos();
        });
        ///ONCHANGE LISTENER A LOS EDITEXT
        editTextListeners();
    }

    private void guardarDatos(){
        if(validar()){
            try{
                if (hospital.equals("Otro")) {
                    hViewModel.hospitalPorNombre(hospital).observe(this, hospital -> {
                        if(hospital != null){
                            guardarUsuarioConHospital(hospital);
                        }else{
                            ToastMessage.Invalido(this, "No se ha encontrado el hospital");
                        }
                    });
                }else{
                    hViewModel.hospitalPorNombreYProvincia(hospital, provincia).observe(this, hospital -> {
                        if (hospital != null) {
                            guardarUsuarioConHospital(hospital);
                        } else {
                            ToastMessage.Invalido(this,"No se ha encontrado el hospital.");
                        }
                    });
                }
            }catch (Exception e){
                ToastMessage.Invalido(this, "Se ha producido un error " + e.getMessage());
                Log.e("ERROR EXCEPTION", e.getMessage() + " " + e.getStackTrace(), e);
            }
        }else{
            ToastMessage.Invalido(this, "Por favor, complete todos los campos del formulario.");
        }
    }

    private void guardarUsuarioConHospital(Hospital hospital){
        Usuario u = new Usuario();
        try{
            u.setNombre(edtName.getText().toString());
            u.setApellido1(edtApellido1.getText().toString());
            u.setApellido2(edtApellido2.getText().toString());
            u.setDni(edtDNI.getText().toString());
            u.setTelefono(edtTlf.getText().toString());
            u.setProvincia(dropdownProvincia.getText().toString());
            u.setContrasena(Security.encriptar(edtPassword.getText().toString()));
            u.setRol(rol);

            this.uViewModel.save(u).observe(this, uResponse -> {
                if(uResponse.getRpta() == 1){
                    this.uViewModel.asociarUsuarioHospital(u.getDni(), hospital).observe(this, response -> {
                       if(response.getRpta() == 1){
                           ToastMessage.Correcto(this, "Su información se ha guardado con éxito.");
                           startActivity(new Intent(this, MainActivity.class));
                       }else{
                           ToastMessage.Invalido(this, "Se ha producido un erro al guardar los datos.");
                       }
                    });
                }
            });
        }catch (Exception e){
            ToastMessage.Invalido(this, "Se ha producido un error " + e.getMessage());
            Log.e("ERROR EXCEPTION", e.getMessage(), e);
        }
    }

    private boolean validar(){
        boolean val = true;
        String nombre, primerApellido, segundoApellido, dni, telefono, dropProvincia, dropHospital, password, passwordVal;
        nombre = edtName.getText().toString();
        primerApellido = edtApellido1.getText().toString();
        segundoApellido = edtApellido2.getText().toString();
        dni = edtDNI.getText().toString();
        telefono = edtTlf.getText().toString();
        dropProvincia = provincia;
        dropHospital = hospital;
        password = edtPassword.getText().toString();
        passwordVal = edtPasswordVal.getText().toString();

        if(nombre.isEmpty()){
            txtInputName.setError("Ingresar nombres");
            return false;
        }else{
            txtInputName.setErrorEnabled(false);
        }
        if(primerApellido.isEmpty()){
            txtInputApellido1.setError("Ingresa su primer apellido");
            return false;
        }else{
            txtInputApellido1.setErrorEnabled(false);
        }
        if(dni.isEmpty()){
            txtInputDNI.setError("Ingrese su DNI");
            return false;
        }else{
            txtInputDNI.setErrorEnabled(false);
        }
        if(telefono.isEmpty()){
            txtInputTlf.setError("Introduzca su tlf");
            return false;
        }else{
            txtInputTlf.setErrorEnabled(false);
        }
        if(dropProvincia.isEmpty()){
            txtInputProvincia.setError("Selecciona una provincia");
            return false;
        }else{
            txtInputProvincia.setErrorEnabled(false);
        }
        if(dropHospital.isEmpty()){
            txtInputHospital.setError("Selecciona un hospital");
            return false;
        }else{
            txtInputHospital.setErrorEnabled(false);
        }
        if(password.isEmpty()){
            txtInputPassword.setError("Introduzca una contraseña");
            return false;
        }else{
            txtInputPassword.setErrorEnabled(false);
        }
        if(passwordVal.isEmpty()){
            txtInputPasswordVal.setError("Introduzca la contraseña para validar");
            return false;
        }else{
            txtInputPassword.setErrorEnabled(false);
        }
        if(!password.equals(passwordVal)){
            txtInputPasswordVal.setError("No es igual a la contraseña");
            return false;
        }
        return val;

    }

    private void obtenerHospitalesPorProvincia(String provincia) {
        hViewModel.hospitalPorProvincia(provincia).observe(this, new Observer<List<Hospital>>(){
            @Override
            public void onChanged(List<Hospital> hospitales){
                if(hospitales != null){
                    Log.d("Hospitales", "Numero de hospitales: " + hospitales.size());
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
        nombresHospitales.add(0, "Otro");
        ArrayAdapter<String> arrayHospitales = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresHospitales);
        dropdownHospital.setAdapter(arrayHospitales);
    }

    private void editTextListeners(){
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputName.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtApellido1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputApellido1.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /*edtApellido2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputApellido2.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        edtDNI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputDNI.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtTlf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputTlf.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPasswordVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputPasswordVal.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dropdownProvincia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                provincia = s.toString();
                obtenerHospitalesPorProvincia(provincia);
                txtInputProvincia.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        dropdownHospital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hospital = s.toString();
                txtInputHospital.setErrorEnabled(false);
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