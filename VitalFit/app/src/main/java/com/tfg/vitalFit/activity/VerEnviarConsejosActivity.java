package com.tfg.vitalfit.activity;

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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.adapter.ConsejoAdapter;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.viewModel.ConsejosViewModel;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class VerEnviarConsejosActivity extends AppCompatActivity {

    private Button btnEnviarConsejo, btnVerConsejos, btnEnviar;
    private LinearLayout verConsejosLayout, enviarConsejosLayout;
    private TextView noHayConsejos;
    private AutoCompleteTextView dropdownPaciente;
    private EditText edtTitulo, edtDescripcion;
    private TextInputLayout txtInputPaciente, txtInputTitulo, txtInputDescripcion;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ConsejosViewModel consejosViewModel;
    private UsuarioViewModel usuarioViewModel;
    private Usuario nutricionista;
    private String paciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_enviar_consejos);
        this.initViewModel();
        this.init();
    }

    private void initViewModel(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        consejosViewModel = vmp.get(ConsejosViewModel.class);
        usuarioViewModel = vmp.get(UsuarioViewModel.class);
    }

    private void init(){
        btnEnviarConsejo = findViewById(R.id.btnEnviarConsejos);
        btnVerConsejos = findViewById(R.id.btnVerConsejos);
        enviarConsejosLayout = findViewById(R.id.enviarConsejosLayout);
        verConsejosLayout = findViewById(R.id.verConsejosLayout);

        noHayConsejos = findViewById(R.id.noHayConsejos);
        dropdownPaciente = findViewById(R.id.dropdownPaciente);
        edtTitulo = findViewById(R.id.edtTitulo);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        txtInputPaciente = findViewById(R.id.txtInputPaciente);
        txtInputTitulo = findViewById(R.id.txtInputTitulo);
        txtInputDescripcion = findViewById(R.id.txtInputDescripcion);

        toolbar = findViewById(R.id.toolbarVerEnviarConsejos);

        // Habilitar el botón de regreso en el Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        obtenerDatosNutricionista();
        listeners();

        btnEnviarConsejo.setOnClickListener(v -> {
            enviarConsejosLayout.setVisibility(View.VISIBLE);
            verConsejosLayout.setVisibility(View.GONE);
            rellenarConsejo();

        });

        btnVerConsejos.setOnClickListener(v -> {
            enviarConsejosLayout.setVisibility(View.GONE);
            verConsejosLayout.setVisibility(View.VISIBLE);
            mostrarConsejos();
        });
    }

    private void mostrarConsejos(){
        recyclerView = findViewById(R.id.recyclerConsejos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        if (nutricionista != null) {
            consejosViewModel.consejosPorNutricionista(nutricionista.getDni()).observe(this, consejos -> {
                if(consejos.isEmpty()){
                    noHayConsejos.setVisibility(View.VISIBLE);
                }
                ConsejoAdapter adapter = new ConsejoAdapter(this, consejos);
                recyclerView.setAdapter(adapter);
            });
        }

    }

    private void rellenarConsejo(){
        listaPacientesNutriocionista(nutricionista.getDni());


    }

    private void listeners(){
        edtTitulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputTitulo.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtDescripcion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputDescripcion.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        dropdownPaciente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                paciente = s.toString();
                txtInputPaciente.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void obtenerDatosNutricionista() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        if (jsonUsuario != null) {
            nutricionista = new Gson().fromJson(jsonUsuario, Usuario.class);
            Log.d("UsuarioRecibido", new Gson().toJson(nutricionista));
        }
    }

    private void listaPacientesNutriocionista(String dniNutricionista){
        List<String> nombresCompletosPacientes = new ArrayList<>();
        usuarioViewModel.getPacientesByNutricionista(dniNutricionista).observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                if(usuarios != null){
                    for(Usuario medico : usuarios){
                        String nombreCompleto = medico.getNombre() + " " + medico.getApellido1() + " " + medico.getApellido2();
                        nombresCompletosPacientes.add(nombreCompleto);
                    }
                }
            }
        });
        ArrayAdapter<String> arrayMedicos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresCompletosPacientes);
        dropdownPaciente.setAdapter(arrayMedicos);
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