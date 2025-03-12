package com.tfg.vitalfit.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.viewModel.MedicoViewModel;
import com.tfg.vitalfit.viewModel.NutricionistaViewModel;
import com.tfg.vitalfit.viewModel.PacienteViewModel;

public class RegistroPacienteActivity extends AppCompatActivity {

    private PacienteViewModel pViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);
        initViewModel();
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        pViewModel = vmp.get(PacienteViewModel.class);
    }
}