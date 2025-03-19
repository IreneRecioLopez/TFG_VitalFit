package com.tfg.vitalfit.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.viewModel.PacienteViewModel;

import java.util.Calendar;
import java.util.Locale;

public class RegistroPacienteActivity extends AppCompatActivity {

    private PacienteViewModel pViewModel;
    private EditText edtFechaNacimiento;
    private Toolbar toolbar;


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
    }

    private void init(){
        edtFechaNacimiento = findViewById(R.id.edtFechaNacimientoP);
        edtFechaNacimiento.setOnClickListener(v -> mostrarCalendario());
        toolbar = findViewById(R.id.toolbarRegPaciente);
        setSupportActionBar(toolbar);

        // Habilitar el botón de regreso en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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