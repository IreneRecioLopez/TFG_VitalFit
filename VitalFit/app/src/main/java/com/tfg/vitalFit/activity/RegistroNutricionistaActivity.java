package com.tfg.vitalfit.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.viewModel.NutricionistaViewModel;

public class RegistroNutricionistaActivity extends AppCompatActivity {

    private NutricionistaViewModel nViewModel;
    private Toolbar toolbar;

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
    }

    private void init(){
        toolbar = findViewById(R.id.toolbarRegNutricionista);
        setSupportActionBar(toolbar);
        // Habilitar el botón de regreso en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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