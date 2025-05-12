package com.tfg.vitalfit.activity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.adapter.AlergiasAdapter;
import com.tfg.vitalfit.adapter.ObservacionesAdapter;
import com.tfg.vitalfit.adapter.OperacionesAdapter;
import com.tfg.vitalfit.entity.service.Alergia;
import com.tfg.vitalfit.entity.service.Observacion;
import com.tfg.vitalfit.entity.service.Operacion;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.Fecha;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.AlergiasViewModel;
import com.tfg.vitalfit.viewModel.ObservacionesViewModel;
import com.tfg.vitalfit.viewModel.OperacionesViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OtrosDatosPacienteActivity extends AppCompatActivity {

    private RecyclerView recyclerAlergias, recyclerOperaciones, recyclerOtrasObservaciones;
    private AlergiasAdapter alergiasAdapter;
    private OperacionesAdapter operacionesAdapter;
    private ObservacionesAdapter observacionesAdapter;
    private AlergiasViewModel alergiasViewModel;
    private OperacionesViewModel operacionesViewModel;
    private ObservacionesViewModel observacionesViewModel;
    private AutoCompleteTextView dropdownTipoDatos, dropdownTipoAlergia;
    private EditText edtNombreAlergia, edtNombreOperacion, edtFechaOperacion, edtObservacion;
    private TextInputLayout txtInputNombreAlergia, txtInputTipoAlergia, txtInputNombreOperacion, txtInputFechaOperacion, txtInputObservacion;
    private TextView noHayAlergias, noHayOperaciones, noHayOtrasObservaciones;
    private Toolbar toolbar;
    private LinearLayout alergiasLayout, operacionesLayout, otrasObservacionesLayout, addAlergia, addOperacion, addObservacion;
    private Button btnAddAlergia, btnGuardarAlergia, btnAddOperacion, btnGuardarOperacion, btnAddObservacion, btnGuardarObservacion;
    private String tipoDato, tipoAlergia;
    private Boolean anadirAlergia, anadirOperacion, anadirObservacion, alergia, operacion, observaciones;
    private Usuario paciente, usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otros_datos_paciente);
        this.initRecyclers();
        this.initViewModels();
        this.obtenerDatosUsuario();
        this.obtenerDatosPaciente();
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

    private void initViewModels(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        alergiasViewModel = vmp.get(AlergiasViewModel.class);
        operacionesViewModel = vmp.get(OperacionesViewModel.class);
        observacionesViewModel = vmp.get(ObservacionesViewModel.class);
    }

    private void init(){
        initVariables();

        setSupportActionBar(toolbar);

        // Habilitar el botón de regreso en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
                if(tipoDato.equals("Alergia")){
                    alergiasLayout.setVisibility(View.VISIBLE);
                    operacionesLayout.setVisibility(View.GONE);
                    otrasObservacionesLayout.setVisibility(View.GONE);
                    alergia = true;
                    observaciones = false;
                    operacion = false;
                    initAlergias();
                }else if(tipoDato.equals("Operacion")){
                    alergiasLayout.setVisibility(View.GONE);
                    operacionesLayout.setVisibility(View.VISIBLE);
                    otrasObservacionesLayout.setVisibility(View.GONE);
                    operacion = true;
                    alergia = false;
                    observaciones = false;
                    initOperaciones();
                }else if(tipoDato.equals("Otras Observacion")){
                    alergiasLayout.setVisibility(View.GONE);
                    operacionesLayout.setVisibility(View.GONE);
                    otrasObservacionesLayout.setVisibility(View.VISIBLE);
                    observaciones = true;
                    operacion = false;
                    alergia = false;
                    initOtrasObservaciones();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initVariables(){
        toolbar = findViewById(R.id.toolbarOtrosDatosPaciente);
        alergiasLayout = findViewById(R.id.layoutAlergias);
        operacionesLayout = findViewById(R.id.layoutOperaciones);
        otrasObservacionesLayout = findViewById(R.id.layoutOtrasObservaciones);
        noHayAlergias = findViewById(R.id.noHayAlergias);
        noHayOperaciones = findViewById(R.id.noHayOperaciones);
        noHayOtrasObservaciones = findViewById(R.id.noHayObservaciones);
        addAlergia = findViewById(R.id.addAlergia);
        addOperacion = findViewById(R.id.addOperacion);
        addObservacion = findViewById(R.id.addObservacion);
        dropdownTipoDatos = findViewById(R.id.dropdownTiposDato);
        dropdownTipoAlergia = findViewById(R.id.dropdownTipoAlergia);
        edtNombreAlergia = findViewById(R.id.edtNombreAlergia);
        edtNombreOperacion = findViewById(R.id.edtNombreOperacion);
        edtFechaOperacion = findViewById(R.id.edtFechaOperacion);
        edtObservacion = findViewById(R.id.edtObservacion);
        txtInputNombreAlergia = findViewById(R.id.txtInputNombreAlergia);
        txtInputTipoAlergia = findViewById(R.id.txtInputTipoAlergia);
        txtInputNombreOperacion = findViewById(R.id.txtInputNombreOperacion);
        txtInputFechaOperacion = findViewById(R.id.txtInputFechaOperacion);
        txtInputObservacion = findViewById(R.id.txtInputObservacion);
        btnAddAlergia = findViewById(R.id.btnAddAlergia);
        btnGuardarAlergia = findViewById(R.id.btnGuardarAlergia);
        btnAddOperacion = findViewById(R.id.btnAddOperacion);
        btnGuardarOperacion = findViewById(R.id.btnGuardarOperacion);
        btnAddObservacion = findViewById(R.id.btnAddObservacion);
        btnGuardarObservacion = findViewById(R.id.btnGuardarObservacion);

        alergia = false;
        operacion = false;
        observaciones = false;
    }

    private void initAlergias(){
        anadirAlergia = false;
        if(paciente != null){
            List<Alergia> alergias = paciente.getPaciente().getAlergias();
            if(alergias.isEmpty()){
                noHayAlergias.setVisibility(View.VISIBLE);
            }
            if(usuario.getRol().equals("Paciente")){
                alergiasAdapter = new AlergiasAdapter(this, alergias, true, alergiasViewModel);
                btnAddAlergia.setVisibility(View.VISIBLE);
            }else{
                alergiasAdapter = new AlergiasAdapter(this, alergias, false, alergiasViewModel);
            }
            recyclerAlergias.setAdapter(alergiasAdapter);

            btnAddAlergia.setOnClickListener(v -> {
                addAlergia.setVisibility(View.VISIBLE);
                anadirAlergia = true;
                addAlergia();
            });
        }
    }

    private void addAlergia(){
        // Adapter para otros tipoos de datos
        String[] alergiaTipo = getResources().getStringArray(R.array.tipoAlergia);
        ArrayAdapter<String> adapterAlergiaTipos = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, alergiaTipo
        );
        dropdownTipoAlergia.setAdapter(adapterAlergiaTipos);
        dropdownTipoAlergia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tipoAlergia = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnGuardarAlergia.setOnClickListener(v -> {
            if(validAlergia()){
                Alergia a = new Alergia();
                a.setAlergia(edtNombreAlergia.getText().toString());
                a.setTipo(tipoAlergia);
                a.setPaciente(paciente.getPaciente());
                alergiasViewModel.save(a).observe(this, response -> {
                    if(response.getRpta() == 1){
                        ToastMessage.Correcto(this, "Alergia guardada");
                        paciente.getPaciente().getAlergias().add(a);
                        alergiasAdapter.notifyItemInserted(paciente.getPaciente().getAlergias().size() - 1);
                        edtNombreAlergia.setText("");
                        dropdownTipoAlergia.setText("");
                        edtNombreAlergia.clearFocus();
                        dropdownTipoAlergia.clearFocus();
                        addAlergia.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    private boolean validAlergia(){
        boolean val = true;
        String dropTipo, alergia;
        alergia = edtNombreAlergia.getText().toString();
        dropTipo = dropdownTipoAlergia.getText().toString();

        if(dropTipo.isEmpty()){
            txtInputTipoAlergia.setError("Seleccione un tipo");
            return false;
        }else{
            txtInputTipoAlergia.setErrorEnabled(false);
        }
        if(alergia.isEmpty()){
            txtInputNombreAlergia.setError("Escriba la alergia");
            return false;
        }else{
            txtInputNombreAlergia.setErrorEnabled(false);
        }

        return val;
    }

    private boolean vaciaAlergia(){
        String dropTipo, alergia;
        alergia = edtNombreAlergia.getText().toString();
        dropTipo = dropdownTipoAlergia.getText().toString();
        if(dropTipo.isEmpty() && alergia.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    private void initOperaciones(){
        anadirOperacion = false;
        if(paciente != null){
            List<Operacion> operaciones = paciente.getPaciente().getOperaciones();
            if(operaciones.isEmpty()){
                noHayOperaciones.setVisibility(View.VISIBLE);
            }
            if(usuario.getRol().equals("Paciente")){
                operacionesAdapter = new OperacionesAdapter(this, operaciones, true, operacionesViewModel);
                btnAddOperacion.setVisibility(View.VISIBLE);
            }else{
                operacionesAdapter = new OperacionesAdapter(this, operaciones, false, operacionesViewModel);
            }
            recyclerOperaciones.setAdapter(operacionesAdapter);

            btnAddOperacion.setOnClickListener(v -> {
                addOperacion.setVisibility(View.VISIBLE);
                anadirOperacion = true;
                edtFechaOperacion.setOnClickListener(x -> mostrarCalendario());
                addOperacion();
            });
        }
    }

    private void addOperacion(){
        btnGuardarOperacion.setOnClickListener(v -> {
            if(validOperacion()){
                Operacion o = new Operacion();
                o.setNombre(edtNombreOperacion.getText().toString());
                o.setFecha(Fecha.registrarFecha(edtFechaOperacion.getText().toString()));
                o.setPaciente(paciente.getPaciente());
                operacionesViewModel.save(o).observe(this, response -> {
                    if(response.getRpta() == 1){
                        ToastMessage.Correcto(this, "Operación guardada");
                        paciente.getPaciente().getOperaciones().add(o);
                        operacionesAdapter.notifyItemInserted(paciente.getPaciente().getOperaciones().size() - 1);
                        edtNombreOperacion.setText("");
                        edtFechaOperacion.setText("");
                        edtNombreOperacion.clearFocus();
                        edtFechaOperacion.clearFocus();
                        addOperacion.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private boolean validOperacion(){
        boolean val = true;
        String operacion, fecha;
        operacion = edtNombreOperacion.getText().toString();
        fecha = edtFechaOperacion.getText().toString();

        if(operacion.isEmpty()){
            txtInputNombreOperacion.setError("Escriba el nombre de la operación");
            return false;
        }else{
            txtInputNombreOperacion.setErrorEnabled(false);
        }
        if(fecha.isEmpty()){
            txtInputFechaOperacion.setError("Escriba la fecha de la operación");
            return false;
        }else{
            txtInputFechaOperacion.setErrorEnabled(false);
        }

        return val;
    }

    private boolean vaciaOperacion(){
        String operacion, fecha;
        operacion = edtNombreOperacion.getText().toString();
        fecha = edtFechaOperacion.getText().toString();
        if(operacion.isEmpty() && fecha.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    private void initOtrasObservaciones(){
        anadirObservacion = false;
        if(paciente != null){
            List<Observacion> observaciones = paciente.getPaciente().getObservaciones();
            if(observaciones.isEmpty()){
                noHayOtrasObservaciones.setVisibility(View.VISIBLE);
            }
            if(usuario.getRol().equals("Paciente")){
                observacionesAdapter = new ObservacionesAdapter(this, observaciones, true, observacionesViewModel);
                btnAddObservacion.setVisibility(View.VISIBLE);
            }else{
                observacionesAdapter = new ObservacionesAdapter(this, observaciones, false, observacionesViewModel);
            }
            recyclerOtrasObservaciones.setAdapter(observacionesAdapter);

            btnAddObservacion.setOnClickListener(v -> {
                addObservacion.setVisibility(View.VISIBLE);
                anadirObservacion = true;
                addObservacion();
            });
        }
    }

    private void addObservacion(){
        btnGuardarObservacion.setOnClickListener(v -> {
            if(validObservacion()){
                Observacion o = new Observacion();
                o.setObservacion(edtObservacion.getText().toString());
                o.setPaciente(paciente.getPaciente());
                observacionesViewModel.save(o).observe(this, response -> {
                    if(response.getRpta() == 1){
                        ToastMessage.Correcto(this, "Operación guardada");
                        paciente.getPaciente().getObservaciones().add(o);
                        observacionesAdapter.notifyItemInserted(paciente.getPaciente().getObservaciones().size() - 1);
                        edtObservacion.setText("");
                        edtObservacion.clearFocus();
                        addObservacion.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private boolean validObservacion(){
        String observacion;
        observacion = edtObservacion.getText().toString();

        if(observacion.isEmpty()){
            txtInputObservacion.setError("Escriba el nombre de la operación");
            return false;
        }else{
            txtInputObservacion.setErrorEnabled(false);
            return true;
        }
    }

    private boolean vaciaObservacion(){
        String observacion;
        observacion = edtObservacion.getText().toString();
        if(observacion.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    private void obtenerDatosUsuario(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        Log.d("UsuarioRecibidoHomeFragment", new Gson().toJson(usuario));

        if(jsonUsuario != null) {
            usuario = new Gson().fromJson(jsonUsuario, Usuario.class);
        }
    }

    private void obtenerDatosPaciente(){
        paciente = (Usuario) getIntent().getSerializableExtra("paciente");
        //Log.d("Paciente recibido otros datos", paciente.toString());
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
                    edtFechaOperacion.setText(fechaSeleccionada);
                },
                anio, mes, dia
        );

        datePickerDialog.show();
    }

    // Capturar el clic en el botón de regreso
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// Este es el ID del botón de navegación
            if(usuario.getRol().equals("Paciente")){
                if(alergia){
                    if (anadirAlergia && !vaciaAlergia()) {
                        new AlertDialog.Builder(this)
                                .setTitle("¿Descartar cambios?")
                                .setMessage("Tienes cambios sin guardar. ¿Deseas descartarlos?")
                                .setPositiveButton("Sí", (dialog, which) -> {
                                    onBackPressed(); // Regresa a la pantalla anterior
                                })
                                .setNegativeButton("Cancelar", null)
                                .show();
                        return false;
                    }else{
                        onBackPressed(); // Regresa a la pantalla anterior
                    }
                }else if (operacion){
                    if (anadirOperacion && !vaciaOperacion()) {
                        new AlertDialog.Builder(this)
                                .setTitle("¿Descartar cambios?")
                                .setMessage("Tienes cambios sin guardar. ¿Deseas descartarlos?")
                                .setPositiveButton("Sí", (dialog, which) -> {
                                    onBackPressed(); // Regresa a la pantalla anterior
                                })
                                .setNegativeButton("Cancelar", null)
                                .show();
                        return false;
                    }else{
                        onBackPressed(); // Regresa a la pantalla anterior
                    }
                }else if(observaciones){
                    if (anadirObservacion && !vaciaObservacion()) {
                        new AlertDialog.Builder(this)
                                .setTitle("¿Descartar cambios?")
                                .setMessage("Tienes cambios sin guardar. ¿Deseas descartarlos?")
                                .setPositiveButton("Sí", (dialog, which) -> {
                                    onBackPressed(); // Regresa a la pantalla anterior
                                })
                                .setNegativeButton("Cancelar", null)
                                .show();
                        return false;
                    }else{
                        onBackPressed(); // Regresa a la pantalla anterior
                    }
                }else{
                    onBackPressed(); // Regresa a la pantalla anterior
                }
            }else{
                onBackPressed(); // Regresa a la pantalla anterior
            }
        }
        return super.onOptionsItemSelected(item);
    }

}