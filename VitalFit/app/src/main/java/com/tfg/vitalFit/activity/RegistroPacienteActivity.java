package com.tfg.vitalfit.activity;

import android.app.DatePickerDialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.entity.service.Pesos;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.viewModel.HospitalViewModel;
import com.tfg.vitalfit.viewModel.PacienteViewModel;
import com.tfg.vitalfit.viewModel.PesosViewModel;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegistroPacienteActivity extends AppCompatActivity {

    private PacienteViewModel pViewModel;
    private UsuarioViewModel uViewModel;
    private HospitalViewModel hViewModel;
    private PesosViewModel pesosViewModel;
    private Toolbar toolbar;
    private EditText edtName, edtApellido1, edtApellido2, edtDNI, edtNSS, edtTlf, edtDireccion,
                        edtPeso, edtAltura, edtPassword, edtPasswordVal, edtFechaNacimiento, edtCP;
    private Button btnGuardarDatos;
    private TextInputLayout txtInputName, txtInputApellido1, txtInputApellido2, txtInputDNI, txtInputNSS,
                            txtInputTlf, txtInputFechaNacimiento,txtInputProvincia, txtInputCP, txtInputDireccion, txtInputPeso,
                            txtInputAltura, txtInputHospital, txtInputPassword, txtInputPasswordVal;
    private CheckBox chkVegetariana, chkVegana;
    private AutoCompleteTextView dropdownProvincia, dropdownHospital;

    private String hospital, provincia, fechaNacimiento;
    private Hospital hospitalAsignado = new Hospital();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);
        this.initViewModel();
        this.init();
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        pViewModel = vmp.get(PacienteViewModel.class);
        hViewModel = vmp.get(HospitalViewModel.class);
        uViewModel = vmp.get(UsuarioViewModel.class);
        pesosViewModel = vmp.get(PesosViewModel.class);
    }

    private void init(){
        //inicializar variables de la vista
        initVariablesView();

        // Habilitar el botón de regreso en el Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //seleccion fecha de nacimiento
        edtFechaNacimiento.setOnClickListener(v -> mostrarCalendario());

        //Selección de provincias
        String[] provincias = getResources().getStringArray(R.array.provincias);
        ArrayAdapter arrayProvincias = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, provincias);
        dropdownProvincia.setAdapter(arrayProvincias);

        btnGuardarDatos.setOnClickListener(v -> {
            Log.e("Boton guardar", "Voy a guardar los datos");
            this.guardarDatos();
        });
        ///ONCHANGE LISTENER A LOS EDITEXT
        editTextListeners();
    }

    private void guardarDatos() {
        if(validar()){
            try {
                hViewModel.hospitalPorNombreYProvincia(hospital, provincia).observe(this, hospital -> {
                    if (hospital != null) {
                        guardarPacienteConHospital(hospital);
                    } else {
                        toastInvalido("No se ha encontrado el hospital.");
                    }
                });
            } catch (Exception e){
                toastInvalido("Error: " + e.getMessage());
                Log.e("ERROR", e.getMessage(), e);
            }
        }
    }

    private void guardarPacienteConHospital(Hospital hospital){
        Paciente p = new Paciente();
        try {
            p.setDNI(edtDNI.getText().toString());
            p.setNumSeguridadSocial(edtNSS.getText().toString());
            p.setFechaNacimiento(convertirFecha(edtFechaNacimiento.getText().toString()));
            p.setProvincia(dropdownProvincia.getText().toString());
            p.setCP(edtCP.getText().toString());
            p.setDireccion(edtDireccion.getText().toString());
            Double altura = Double.parseDouble(edtAltura.getText().toString());
            p.setAltura(altura);
            Double peso = Double.parseDouble(edtPeso.getText().toString());
            p.setPesoActual(peso);
            Double imc = peso / (altura * altura);
            p.setImc(imc);
            p.setVegana(chkVegana.isChecked() ? 1 : 0);
            p.setVegetariana(chkVegetariana.isChecked() ? 1 : 0);

            this.pViewModel.save(p).observe(this, pResponse -> {
                if(pResponse.getRpta() == 1){
                    Usuario u = new Usuario();
                    u.setNombre(edtName.getText().toString());
                    u.setApellido1(edtApellido1.getText().toString());
                    u.setApellido2(edtApellido2.getText().toString());
                    u.setDNI(edtDNI.getText().toString());
                    u.setTelefono(edtTlf.getText().toString());
                    u.setContrasena(edtPassword.getText().toString());
                    u.setRol("Paciente");
                    u.setPaciente(p);

                    this.uViewModel.save(u).observe(this, uResponse -> {
                        if(uResponse.getRpta() == 1){
                            this.uViewModel.asociarUsuarioHospital(u.getDni(), hospital).observe(this, response -> {
                                if (response.getRpta() == 1) {
                                    Pesos pesoObj = new Pesos();
                                    pesoObj.setPeso(p.getPesoActual());
                                    pesoObj.setPaciente(new Paciente(p.getDNI()));
                                    pesoObj.setFecha(obtenerFechaActual());

                                    this.pesosViewModel.save(pesoObj).observe(this, pesoResponse -> {
                                        if(pesoResponse.getRpta() == 1){
                                            toastCorrecto("Su información ha sido guardada con éxito.");
                                            startActivity(new Intent(this, MainActivity.class));
                                        } else {
                                            toastInvalido("No se ha podido guardar bien el peso");
                                        }
                                    });
                                } else {
                                    toastInvalido("No se ha podido asociar bien al hospital");
                                }
                            });
                        } else {
                            toastInvalido("No se ha podido guardar bien el usuario");
                        }
                    });
                } else {
                    toastInvalido("No se ha podido guardar el paciente");
                }
            });
        } catch (Exception e){
            toastInvalido("Se ha producido un error " + e.getMessage());
            Log.e("ERROR EXCEPTION", e.getMessage(), e);
        }
    }

    private boolean validar(){
        boolean val = true;
        String nombre, primerApellido, segundoApellido, dni, nss, telefono, fechaNaciemiento, cp, dropProvincia, dropHospital,
                peso, altura, direccion, password, passwordVal;
        nombre = edtName.getText().toString();
        primerApellido = edtApellido1.getText().toString();
        segundoApellido = edtApellido2.getText().toString();
        dni = edtDNI.getText().toString();
        nss = edtNSS.getText().toString();
        telefono = edtTlf.getText().toString();
        fechaNaciemiento = edtFechaNacimiento.getText().toString();
        dropProvincia = provincia;
        cp = edtCP.getText().toString();
        direccion = edtDireccion.getText().toString();
        peso = edtPeso.getText().toString();
        altura = edtAltura.getText().toString();
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
        if(nss.isEmpty()){
            txtInputNSS.setError("Ingrese su NSS");
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
        if(fechaNaciemiento.isEmpty()){
            txtInputDNI.setError("Ingrese su fecha de nacimiento");
            return false;
        }else{
            txtInputFechaNacimiento.setErrorEnabled(false);
        }
        if(dropProvincia.isEmpty()){
            txtInputProvincia.setError("Selecciona una provincia");
            return false;
        }else{
            txtInputProvincia.setErrorEnabled(false);
        }
        if(cp.isEmpty()){
            txtInputDNI.setError("Ingrese su codigo postal");
            return false;
        }else{
            txtInputCP.setErrorEnabled(false);
        }
        if(dropHospital.isEmpty()){
            txtInputHospital.setError("Selecciona un hospital");
            return false;
        }else{
            txtInputHospital.setErrorEnabled(false);
        }
        if(peso.isEmpty()){
            txtInputDNI.setError("Ingrese su peso");
            return false;
        }else{
            txtInputPeso.setErrorEnabled(false);
        }
        if(altura.isEmpty()){
            txtInputDNI.setError("Ingrese su altura");
            return false;
        }else{
            txtInputAltura.setErrorEnabled(false);
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
        ArrayAdapter<String> arrayHospitales = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresHospitales);
        dropdownHospital.setAdapter(arrayHospitales);
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

    // Método para continuar el flujo cuando el hospital ya se asignó
    private void continuarConHospital() {
        if (hospitalAsignado != null) {
            Log.e("Proceso", "Hospital confirmado: " + hospitalAsignado.getNombre());
        } else {
            toastInvalido("Error inesperado: hospitalAsignado es null.");
        }
    }

    private Hospital getHospitalAsignado(Hospital hospital){
        return hospital;
    }

    private String convertirFecha(String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = formatoEntrada.parse(fecha);
            return formatoSalida.format(date);
        } catch (ParseException e) {
            Log.e("ErrorFecha", "Formato incorrecto: " + fecha);
            return null;
        }
    }

    private String obtenerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void mostrarCalendario(){
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    edtFechaNacimiento.setText(fechaSeleccionada);
                },
                anio, mes, dia
        );

        datePickerDialog.show();
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
        edtNSS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputNSS.setErrorEnabled(false);
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
        edtFechaNacimiento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fechaNacimiento = s.toString();
                txtInputFechaNacimiento.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtCP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputCP.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtDireccion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputDireccion.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPeso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputPeso.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtAltura.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputAltura.setErrorEnabled(false);
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

    private void initVariablesView(){
        toolbar = findViewById(R.id.toolbarRegPaciente);
        btnGuardarDatos = findViewById(R.id.btnGuardarDatosP);
        edtName = findViewById(R.id.edtNameP);
        edtApellido1 = findViewById(R.id.edtPrimerApellidoP);
        edtApellido2 = findViewById(R.id.edtSegundoApellidoP);
        edtDNI = findViewById(R.id.edtDNIP);
        edtNSS = findViewById(R.id.edtNSSP);
        edtTlf = findViewById(R.id.edtTelefonoP);
        edtFechaNacimiento = findViewById(R.id.edtFechaNacimientoP);
        edtCP = findViewById(R.id.edtCPP);
        edtDireccion = findViewById(R.id.edtDireccionP);
        edtPeso = findViewById(R.id.edtPesoP);
        edtAltura = findViewById(R.id.edtAlturaP);
        edtPassword = findViewById(R.id.edtPasswordP);
        edtPasswordVal = findViewById(R.id.edtPasswordValP);

        //TextInputLayout
        txtInputName = findViewById(R.id.txtInputNameP);
        txtInputApellido1 = findViewById(R.id.txtInputPrimerApellidoP);
        txtInputApellido2 = findViewById(R.id.txtInputSegundoApellidoP);
        txtInputDNI = findViewById(R.id.txtInputDNIP);
        txtInputNSS = findViewById(R.id.txtInputNSSP);
        txtInputTlf = findViewById(R.id.txtInputTelefonoP);
        txtInputFechaNacimiento = findViewById(R.id.txtInputFechaNacimientoP);
        txtInputProvincia = findViewById(R.id.txtInputProvinciaP);
        txtInputHospital = findViewById(R.id.txtInputHospitalP);
        txtInputCP = findViewById(R.id.txtInputCPP);
        txtInputDireccion = findViewById(R.id.txtInputDireccionP);
        txtInputPeso = findViewById(R.id.txtInputPesoP);
        txtInputAltura = findViewById(R.id.txtInputAlturaP);
        txtInputPassword = findViewById(R.id.txtInputPasswordP);
        txtInputPasswordVal = findViewById(R.id.txtInputPasswordValP);

        //CheckBox
        chkVegetariana = findViewById(R.id.chkVegetariana);
        chkVegana = findViewById(R.id.chkVegana);

        //AutoCompleteTextView
        dropdownProvincia = findViewById(R.id.dropdownProvinciaP);
        dropdownHospital = findViewById(R.id.dropdownHospitalP);
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