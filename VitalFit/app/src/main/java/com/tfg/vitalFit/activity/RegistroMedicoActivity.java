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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.tfg.vitalfit.entity.service.Medico;
import com.tfg.vitalfit.entity.service.Paciente;
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
                            txtInputPassword, txtInputPasswordVal, txtInputProvincia, txtInputHospital;

    private String hospital, provincia;
    private Hospital hospitalAsignado = new Hospital();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_medico);
        this.initViewModel();
        this.init();
        //this.spinners();
    }

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
        txtInputName = findViewById(R.id.txtInputNameM);
        txtInputApellido1 = findViewById(R.id.txtInputPrimerApellidoM);
        txtInputApellido2 = findViewById(R.id.txtInputSegundoApellidoM);
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

    // Capturar el clic en el botón de regreso
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // Este es el ID del botón de navegación
            onBackPressed(); // Regresa a la pantalla anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void obtenerHospitalPorNombreYProvincia(String nombre, String provincia){

        hViewModel.hospitalPorNombreYProvincia(nombre, provincia).observe(this, new Observer<Hospital>() {
            @Override
            public void onChanged(Hospital hospital) {
                if(hospital != null) {
                    hospitalAsignado = getHospitalAsignado(hospital);
                    Log.e("Hospital obtenido", hospital.getNombre());
                    Log.e("Hospital asignado", hospitalAsignado.getNombre());

                    continuarConHospital();
                }
            }
        });
    }

    private Hospital getHospitalAsignado(Hospital hospital){
        return hospital;
    }

    // Método para continuar el flujo cuando el hospital ya se asignó
    private void continuarConHospital() {
        if (hospitalAsignado != null) {
            Log.e("Proceso", "Hospital confirmado: " + hospitalAsignado.getNombre());
        } else {
            toastInvalido("Error inesperado: hospitalAsignado es null.");
        }
    }

    public void guardarMedico(Medico m){
        this.mViewModel.save(m).observe(this, mResponse -> {
            if (mResponse.getRpta() == 1) {
                this.mViewModel.asociarMedicoHospital(m.getDni(), hospitalAsignado).observe(this, response -> {
                    Log.e("Respuesta", "Rpta: " + response.getRpta());
                    if (response.getRpta() == 1) {
                        toastCorrecto("Su información ha sido guardada con éxito.");
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        toastInvalido("No se ha podido asociar bien al hospital");
                    }
                });
            } else {
                toastInvalido("No se han podido guardar los datos. Intentelo de nuevo.");
            }
        });
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
        ArrayAdapter<String> arrayHospitales = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresHospitales);
        dropdownHospital.setAdapter(arrayHospitales);
    }


    private void guardarDatos(){
        Medico m;
        if(validar()){
            m = new Medico();
            try{
                m.setNombre(edtName.getText().toString());
                m.setApellido1(edtApellido1.getText().toString());
                m.setApellido2(edtApellido2.getText().toString());
                m.setDNI(edtDNI.getText().toString());
                m.setTelefono(edtTlf.getText().toString());
                m.setContrasena(edtPassword.getText().toString());
                obtenerHospitalPorNombreYProvincia(hospital, provincia);
                Long idHospital = hospitalAsignado.getIdHospital();

                guardarMedico(m);

            }catch (Exception e){
                toastInvalido("Se ha producido un error " + e.getMessage());
                Log.e("ERROR EXCEPTION", e.getMessage() + " " + e.getStackTrace(), e);
            }
        }else{
            toastInvalido("Por favor, complete todos los campos del formulario.");
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

    public void toastCorrecto(String msg){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastOk);
        txtMensaje.setText(msg);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void toastInvalido(String msg){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_bad, (ViewGroup) findViewById(R.id.ll_custom_toast_bad));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastBad);
        txtMensaje.setText(msg);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
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


}