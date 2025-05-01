package com.tfg.vitalfit.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.adapter.ConsejoAdapter;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.viewModel.ConsejosViewModel;

public class VerEnviarConsejosActivity extends AppCompatActivity {

    private Button btnEnviarConsejo, btnVerConsejos;
    private LinearLayout verConsejosLayout, enviarConsejosLayout;
    private TextView verConsejos;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ConsejosViewModel consejosViewModel;
    private Usuario nutricionista;


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
    }

    private void init(){
        btnEnviarConsejo = findViewById(R.id.btnEnviarConsejos);
        btnVerConsejos = findViewById(R.id.btnVerConsejos);
        enviarConsejosLayout = findViewById(R.id.enviarConsejosLayout);
        verConsejosLayout = findViewById(R.id.verConsejosLayout);
        verConsejos = findViewById(R.id.verConsejos);

        toolbar = findViewById(R.id.toolbarVerEnviarConsejos);

        // Habilitar el botón de regreso en el Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnEnviarConsejo.setOnClickListener(v -> {
            enviarConsejosLayout.setVisibility(View.VISIBLE);
            verConsejosLayout.setVisibility(View.GONE);
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

        obtenerDatosNutricionista();

        if (nutricionista != null) {
            consejosViewModel.consejosPorNutricionista(nutricionista.getDni()).observe(this, consejos -> {
                if(consejos.isEmpty()){
                    verConsejos.setVisibility(View.VISIBLE);
                }
                ConsejoAdapter adapter = new ConsejoAdapter(this, consejos);
                recyclerView.setAdapter(adapter);
            });
        }

    }

    private void obtenerDatosNutricionista() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        if (jsonUsuario != null) {
            nutricionista = new Gson().fromJson(jsonUsuario, Usuario.class);
            Log.d("UsuarioRecibido", new Gson().toJson(nutricionista));
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