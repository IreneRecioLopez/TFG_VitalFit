package com.tfg.vitalfit.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Alergia;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Observacion;
import com.tfg.vitalfit.entity.service.Operacion;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.entity.service.Peso;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.Fecha;
import com.tfg.vitalfit.utils.Security;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.AlergiasViewModel;
import com.tfg.vitalfit.viewModel.HospitalViewModel;
import com.tfg.vitalfit.viewModel.ObservacionesViewModel;
import com.tfg.vitalfit.viewModel.OperacionesViewModel;
import com.tfg.vitalfit.viewModel.PacienteViewModel;
import com.tfg.vitalfit.viewModel.PesosViewModel;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegistroPacienteActivity extends AppCompatActivity {

    private PacienteViewModel pViewModel;
    private UsuarioViewModel uViewModel;
    private HospitalViewModel hViewModel;
    private PesosViewModel pesosViewModel;
    private AlergiasViewModel alergiasViewModel;
    private OperacionesViewModel operacionesViewModel;
    private ObservacionesViewModel observacionesViewModel;

    private LinearLayout layoutOperaciones;
    private Toolbar toolbar;
    private EditText edtName, edtApellido1, edtApellido2, edtDNI, edtNSS, edtTlf, edtDireccion,
                        edtPeso, edtAltura, edtPassword, edtPasswordVal, edtFechaNacimiento, edtCP,
                        edtAlergiaAlimentaria, edtAlergiaMedicinal;
    private Button btnGuardarDatos, btnAgregarOperacion;
    private TextInputLayout txtInputName, txtInputApellido1, txtInputDNI, txtInputNSS,
                            txtInputTlf, txtInputFechaNacimiento,txtInputProvincia, txtInputCP, txtInputDireccion, txtInputPeso,
                            txtInputAltura, txtInputHospital, txtInputMedico, txtInputPassword, txtInputPasswordVal;
    private CheckBox chkVegetariana, chkVegana;
    private AutoCompleteTextView dropdownProvincia, dropdownHospital, dropdownMedico;

    private String hospital, provincia, fechaNacimiento, medico;
    List<String> nombresHospitales, nombreMedicos, dniMedicos;
    List<Long> idHospitales;


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
        alergiasViewModel = vmp.get(AlergiasViewModel.class);
        operacionesViewModel = vmp.get(OperacionesViewModel.class);
        observacionesViewModel = vmp.get(ObservacionesViewModel.class);
    }

    private void init(){
        //inicializar variables de la vista
        initVariablesView();

        // Habilitar el botón de regreso en el Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dropdownHospital.setEnabled(false);
        dropdownMedico.setEnabled(false);

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

        btnAgregarOperacion.setOnClickListener(v -> {
            agregarOperacion();
        });
        ///ONCHANGE LISTENER A LOS EDITEXT
        editTextListeners();
    }

    private void initVariablesView(){
        layoutOperaciones = findViewById(R.id.layout_operaciones);
        toolbar = findViewById(R.id.toolbarRegPaciente);
        btnGuardarDatos = findViewById(R.id.btnGuardarDatosP);
        btnAgregarOperacion = findViewById(R.id.btnAgregarOperacion);
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
        edtAlergiaAlimentaria = findViewById(R.id.edtAlergiasAlimentariasP);
        edtAlergiaMedicinal = findViewById(R.id.edtAlergiasMedicinalesP);

        //TextInputLayout
        txtInputName = findViewById(R.id.txtInputNameP);
        txtInputApellido1 = findViewById(R.id.txtInputPrimerApellidoP);
        txtInputDNI = findViewById(R.id.txtInputDNIP);
        txtInputNSS = findViewById(R.id.txtInputNSSP);
        txtInputTlf = findViewById(R.id.txtInputTelefonoP);
        txtInputFechaNacimiento = findViewById(R.id.txtInputFechaNacimientoP);
        txtInputProvincia = findViewById(R.id.txtInputProvinciaP);
        txtInputHospital = findViewById(R.id.txtInputHospitalP);
        txtInputMedico = findViewById(R.id.txtInputMedicoP);
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
        dropdownMedico = findViewById(R.id.dropdownMedicoP);
    }

    private void guardarDatos() {
        if(validar()){
            Long idHospital;
            String dniMedico;
            int indexHospital = nombresHospitales.indexOf(hospital);
            if(indexHospital != -1){
                idHospital = idHospitales.get(indexHospital);
                int indexMedico = nombreMedicos.indexOf(medico);
                if(indexMedico != -1){
                    dniMedico = dniMedicos.get(indexMedico);
                } else {
                    dniMedico = "";
                }

                hViewModel.hospitalPorId(idHospital).observe(this, hospital -> {
                    if(hospital != null){
                        uViewModel.getUsuarioByDni(dniMedico).observe(this, medico -> {
                            if(medico != null){
                                guardarPacienteConHospitalYMedico(hospital, medico);
                            }
                        });
                    }else{
                        ToastMessage.Invalido(this, "No se ha encontrado el hospital");
                    }
                });
            }
        }else{
            ToastMessage.Invalido(this, "Rellena todos los datos necesarios");
        }
    }

    private void guardarPacienteConHospitalYMedico(Hospital hospital, Usuario medico){
        Paciente p = new Paciente();
        try {
            p.setDni(edtDNI.getText().toString());
            p.setNumSeguridadSocial(edtNSS.getText().toString());
            p.setFechaNacimiento(Fecha.registrarFecha(edtFechaNacimiento.getText().toString()));
            p.setCP(edtCP.getText().toString());
            p.setDireccion(edtDireccion.getText().toString());
            Double altura = Double.parseDouble(edtAltura.getText().toString());
            p.setAltura(altura);
            Double peso = Double.parseDouble(edtPeso.getText().toString());
            p.setPesoActual(peso);
            Double imc = peso / (altura * altura);
            imc = Math.round(imc * 1000.0) / 1000.0;
            p.setImc(imc);

            this.pViewModel.save(p).observe(this, pResponse -> {
                if(pResponse.getRpta() == 1){
                    Usuario u = new Usuario();
                    u.setNombre(edtName.getText().toString());
                    u.setApellido1(edtApellido1.getText().toString());
                    u.setApellido2(edtApellido2.getText().toString());
                    u.setDni(edtDNI.getText().toString());
                    u.setTelefono(edtTlf.getText().toString());
                    u.setProvincia(dropdownProvincia.getText().toString());
                    try {
                        u.setContrasena(Security.encriptar(edtPassword.getText().toString()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    u.setRol("Paciente");
                    u.setPaciente(p);

                    this.uViewModel.save(u).observe(this, uResponse -> {
                        if(uResponse.getRpta() == 1){
                            this.uViewModel.asociarUsuarioHospital(u.getDni(), hospital).observe(this, response -> {
                                if (response.getRpta() == 1) {
                                    this.uViewModel.asociarPacienteMedico(u.getDni(), medico).observe(this, pmResponse -> {
                                        if(pmResponse.getRpta() == 1){
                                            Peso pesoObj = new Peso();
                                            pesoObj.setPeso(p.getPesoActual());
                                            pesoObj.setPaciente(new Paciente(p.getDni()));
                                            pesoObj.setFecha(Fecha.obtenerFechaActual());

                                            this.pesosViewModel.save(pesoObj).observe(this, pesoResponse -> {
                                                if(pesoResponse.getRpta() == 1){
                                                    //Codigo nuevo para alergias y operaciones
                                                    if(!edtAlergiaAlimentaria.getText().toString().isEmpty()){
                                                        String[] alergiasAlimentarias;
                                                        alergiasAlimentarias = edtAlergiaAlimentaria.getText().toString().split(",");
                                                        for (String alergia: alergiasAlimentarias) {
                                                            Alergia a = new Alergia();
                                                            String nombre = alergia.trim();
                                                            Log.d("ALERGIA NOMBRE", nombre);
                                                            a.setAlergia(nombre);
                                                            a.setTipo("Alimentaria");
                                                            a.setPaciente(new Paciente(p.getDni()));
                                                            alergiasViewModel.save(a).observe(this, alergiaResponse -> {
                                                                if(alergiaResponse.getRpta() != 1){
                                                                    ToastMessage.Invalido(this, "Error al guardar la alergia");
                                                                }
                                                            });
                                                        }
                                                    }
                                                    if(!edtAlergiaMedicinal.getText().toString().isEmpty()){
                                                        String[] alergiasMedicinales;
                                                        alergiasMedicinales = edtAlergiaMedicinal.getText().toString().split(",");
                                                        for (String alergia: alergiasMedicinales) {
                                                            Alergia a = new Alergia();
                                                            String nombre = alergia.trim();
                                                            Log.d("ALERGIA NOMBRE", nombre);
                                                            a.setAlergia(nombre);
                                                            a.setTipo("Medicinal");
                                                            a.setPaciente(new Paciente(p.getDni()));
                                                            alergiasViewModel.save(a).observe(this, alergiaResponse -> {
                                                                if(alergiaResponse.getRpta() != 1){
                                                                    ToastMessage.Invalido(this, "Error al guardar la alergia");
                                                                }
                                                            });
                                                        }
                                                    }

                                                    List<Operacion> listaOperaciones = new ArrayList<>();

                                                    for (int i = 0; i < layoutOperaciones.getChildCount(); i++) {
                                                        View itemOperacion = layoutOperaciones.getChildAt(i);

                                                        TextInputEditText edtOperacionNombre = itemOperacion.findViewById(R.id.edtNombreOperacion);
                                                        TextInputEditText edtOperacionFecha = itemOperacion.findViewById(R.id.edtFechaOperacion);

                                                        if (edtOperacionNombre == null || edtOperacionFecha == null) continue;

                                                        String nombre = edtOperacionNombre.getText().toString().trim();
                                                        String fecha = Fecha.registrarFecha(edtOperacionFecha.getText().toString());

                                                        if (!nombre.isEmpty()) {
                                                            if(fecha.isEmpty()){
                                                                fecha = " ";
                                                            }
                                                            Operacion op = new Operacion();
                                                            op.setPaciente(new Paciente(p.getDni()));
                                                            op.setNombre(nombre);
                                                            op.setFecha(fecha);

                                                            //Crear el Viewmodel
                                                            operacionesViewModel.save(op).observe(this, operacionesResponse -> {
                                                                if(operacionesResponse.getRpta() != 1){
                                                                    ToastMessage.Invalido(this, "Error al guardar la operacion");
                                                                }
                                                            });
                                                        }
                                                    }
                                                    if(chkVegana.isChecked()){
                                                        Observacion ob = new Observacion();
                                                        ob.setPaciente(new Paciente(p.getDni()));
                                                        ob.setObservacion("Vegana");
                                                        observacionesViewModel.save(ob).observe(this, observacionesResponse -> {
                                                            if(observacionesResponse.getRpta() != 1){
                                                                ToastMessage.Invalido(this, "Error al guardar la observación");
                                                            }
                                                        });
                                                    }
                                                    if(chkVegetariana.isChecked()){
                                                        Observacion ob = new Observacion();
                                                        ob.setObservacion("Vegetariana");
                                                        ob.setPaciente(new Paciente(p.getDni()));
                                                        observacionesViewModel.save(ob).observe(this, observacionesResponse -> {
                                                            if(observacionesResponse.getRpta() != 1){
                                                                ToastMessage.Invalido(this, "Error al guardar la observación");
                                                            }
                                                        });
                                                    }
                                                    ToastMessage.Correcto(this, "Su información ha sido guardada con éxito.");
                                                    startActivity(new Intent(this, MainActivity.class));
                                                } else {
                                                    ToastMessage.Invalido(this, "No se ha podido guardar bien el peso");
                                                }
                                            });
                                        } else{
                                            ToastMessage.Invalido(this, "No se ha podido asociar bien al medico");
                                        }
                                    });
                                } else {
                                    ToastMessage.Invalido(this, "No se ha podido asociar bien al hospital");
                                }
                            });
                        } else {
                            ToastMessage.Invalido(this, "No se ha podido guardar bien el usuario");
                        }
                    });
                } else {
                    ToastMessage.Invalido(this, "No se ha podido guardar el paciente");
                }
            });
        } catch (Exception e){
            ToastMessage.Invalido(this, "Se ha producido un error " + e.getMessage());
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
                    listaNombresHospitales(hospitales);
                }
            }
        });
    }

    private void listaNombresHospitales(List<Hospital> hospitales){
        nombresHospitales = new ArrayList<>();
        idHospitales = new ArrayList<>();
        for(Hospital hospital: hospitales){
            nombresHospitales.add(hospital.getNombre());
            idHospitales.add(hospital.getIdHospital());
        }
        nombresHospitales.add(0, "Otro");
        idHospitales.add(0, Long.parseLong("1"));
        ArrayAdapter<String> arrayHospitales = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresHospitales);
        dropdownHospital.setAdapter(arrayHospitales);
    }

    private void obtenerMedicosPorHospital(String hospitalNombre){
        int index = nombresHospitales.indexOf(hospitalNombre);
        if(index != -1){
            Long idHospital = idHospitales.get(index);
            listaMedicosHospital(idHospital);
        }
    }


    private void listaMedicosHospital(Long idHospital){
        nombreMedicos = new ArrayList<>();
        dniMedicos = new ArrayList<>();
        uViewModel.getMedicosByHospital(idHospital).observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                if(usuarios != null){
                    for(Usuario medico : usuarios){
                        String nombreCompleto = medico.getNombreCompleto();
                        nombreMedicos.add(nombreCompleto);
                        dniMedicos.add(medico.getDni());
                    }
                }
            }
        });
        ArrayAdapter<String> arrayMedicos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombreMedicos);
        dropdownMedico.setAdapter(arrayMedicos);
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

    private void agregarOperacion() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View itemOperacion = inflater.inflate(R.layout.item_add_operacion, layoutOperaciones, false);
        layoutOperaciones.addView(itemOperacion);
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
                dropdownHospital.setEnabled(true);
            }
        });
        dropdownHospital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hospital = s.toString();
                obtenerMedicosPorHospital(hospital);
                txtInputHospital.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                dropdownMedico.setEnabled(true);

            }
        });
        dropdownMedico.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                medico = s.toString();
                txtInputMedico.setErrorEnabled(false);
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