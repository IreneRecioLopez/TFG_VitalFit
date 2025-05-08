package com.tfg.vitalfit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.Fecha;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

public class MedicoDatosPacienteActivity extends AppCompatActivity {

    private TextView txtNombrePaciente, txtFechaNacimientoPaciente, txtTarjetaSanitariaPaciente;
    private Toolbar toolbar;
    private Button btnAsignarNutricionista, btnOtrosDatos;
    private UsuarioViewModel usuarioViewModel;
    private Usuario paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_datos_paciente);
        this.initViewModel();
        this.init();
    }

    private void initViewModel(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        usuarioViewModel = vmp.get(UsuarioViewModel.class);
    }

    private void init(){
        txtNombrePaciente = findViewById(R.id.txtNombreCompletoPaciente);
        txtFechaNacimientoPaciente = findViewById(R.id.txtFechaNacimientoPaciente);
        txtTarjetaSanitariaPaciente = findViewById(R.id.txtNumeroTarjetaSanitaria);
        toolbar = findViewById(R.id.toolbarDatosPacienteMedico);
        btnAsignarNutricionista = findViewById(R.id.btnAsignarNutricionista);
        btnOtrosDatos = findViewById(R.id.btnOtrosDatos);

        setSupportActionBar(toolbar);

        // Habilitar el botón de regreso en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        paciente = (Usuario) getIntent().getSerializableExtra("paciente");
        Log.d("Paciente recibido", paciente.toString());

        if (paciente != null) {
            txtNombrePaciente.setText(paciente.getNombreCompleto());
            txtFechaNacimientoPaciente.setText(Fecha.obtenerFecha(paciente.getPaciente().getFechaNacimiento()));
            txtTarjetaSanitariaPaciente.setText(paciente.getPaciente().getNumSeguridadSocial());
        } else {
            ToastMessage.Invalido(this, "No se recibió el paciente");
        }

        btnAsignarNutricionista.setOnClickListener(v -> {

        });

        btnOtrosDatos.setOnClickListener(v -> {
            Intent intent = new Intent(this, OtrosDatosPacienteActivity.class);
            intent.putExtra("paciente", paciente);
            startActivity(intent);
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