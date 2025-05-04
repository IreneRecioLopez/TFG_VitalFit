package com.tfg.vitalfit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NutricionistaDatosPacienteActivity extends AppCompatActivity {

    private TextView txtNombrePaciente, txtFechaNacimientoPaciente, txtTarjetaSanitariaPaciente;
    private Toolbar toolbar;
    private Button btnConsejos;
    private Usuario paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutricionista_datos_paciente);
        this.initViewModel();
        this.init();
    }

    private void initViewModel(){

    }

    private void init(){
        txtNombrePaciente = findViewById(R.id.txtNombreCompletoPaciente);
        txtFechaNacimientoPaciente = findViewById(R.id.txtFechaNacimientoPaciente);
        txtTarjetaSanitariaPaciente = findViewById(R.id.txtNumeroTarjetaSanitaria);
        toolbar = findViewById(R.id.toolbarDatosPacienteNutricionista);
        btnConsejos = findViewById(R.id.btnConsejos);

        setSupportActionBar(toolbar);

        // Habilitar el botón de regreso en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        paciente = (Usuario) getIntent().getSerializableExtra("paciente");
        Log.d("Paciente recibido", paciente.toString());

        if (paciente != null) {
            txtNombrePaciente.setText(paciente.getNombreCompleto());
            txtFechaNacimientoPaciente.setText(convertirFecha(paciente.getPaciente().getFechaNacimiento()));
            txtTarjetaSanitariaPaciente.setText(paciente.getPaciente().getNumSeguridadSocial());
        } else {
            ToastMessage.Invalido(this, "No se recibió el paciente");
        }

        btnConsejos.setOnClickListener(v -> {
            startActivity(new Intent(this, VerEnviarConsejosActivity.class));
        });
    }

    private String convertirFecha(String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Log.e("FormatoFecha", fecha);
            Date date = formatoEntrada.parse(fecha);
            return formatoSalida.format(date);
        } catch (ParseException e) {
            Log.e("ErrorFecha", "Formato incorrecto: " + fecha);
            return null;
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