package com.tfg.vitalfit.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.adapter.ConsejoAdapter;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.viewModel.ConsejosViewModel;

public class LeerConsejosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ConsejoAdapter adapter;
    private ConsejosViewModel consejosViewModel;
    private Usuario paciente;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_consejos);

        recyclerView = findViewById(R.id.recyclerConsejos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.initViewModel();
        obtenerDatosUsuario();
        initToolbar();
        this.init();

    }

    private void initViewModel(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        consejosViewModel = vmp.get(ConsejosViewModel.class);
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.toolbarLeerConsejos);

        // Habilitar el botón de regreso en el Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void init(){

        if (paciente != null) {
            consejosViewModel.consejosPorPaciente(paciente.getDni()).observe(this, consejos -> {
                ConsejoAdapter adapter = new ConsejoAdapter(this, consejos, paciente,  consejo -> {
                    consejo.setLeido(1);
                    consejosViewModel.marcarComoLeido(consejo).observe(this, response -> {
                    });
                });
                recyclerView.setAdapter(adapter);
            });
        }

    }

    private void obtenerDatosUsuario() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        if (jsonUsuario != null) {
            paciente = new Gson().fromJson(jsonUsuario, Usuario.class);
            Log.d("UsuarioRecibido", new Gson().toJson(paciente));
        }
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