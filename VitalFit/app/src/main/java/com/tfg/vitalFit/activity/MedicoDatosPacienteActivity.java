package com.tfg.vitalfit.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.ToastMessage;

public class MedicoDatosPacienteActivity extends AppCompatActivity {

    private TextView txtNombrePaciente, txtFechaNacimientoPaciente;
    private Toolbar toolbar;
    private Usuario paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_datos_paciente);
        this.initViewModel();
        this.init();
    }

    private void initViewModel(){

    }

    private void init(){
        txtNombrePaciente = findViewById(R.id.txtNombreCompletoPaciente);
        txtFechaNacimientoPaciente = findViewById(R.id.txtFechaNacimientoPaciente);
        toolbar = findViewById(R.id.toolbarDatosPacienteMedico);

        setSupportActionBar(toolbar);

        // Habilitar el botón de regreso en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        paciente = (Usuario) getIntent().getSerializableExtra("paciente");
        Log.d("Paciente recibido", paciente.toString());

        if (paciente != null) {
            txtNombrePaciente.setText(paciente.getNombreCompleto());
            txtFechaNacimientoPaciente.setText(paciente.getPaciente().getFechaNacimiento());
        } else {
            ToastMessage.Invalido(this, "No se recibió el paciente");
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