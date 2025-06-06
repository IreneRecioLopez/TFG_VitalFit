package com.tfg.vitalfit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.Fecha;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class MedicoDatosPacienteActivity extends AppCompatActivity {

    private LinearLayout layoutAsignarNutricionista;
    private TextView txtNombrePaciente, txtFechaNacimientoPaciente, txtTarjetaSanitariaPaciente;
    private AutoCompleteTextView dropdownNutricionista;
    private TextInputLayout txtInputNutricionista;
    private Toolbar toolbar;
    private Button btnAsignarNutricionista, btnOtrosDatos, btnDieta, btnEstadisticas, btnAsignar;
    private UsuarioViewModel usuarioViewModel;
    private Usuario paciente;
    private List<String> nombreCompletosNutricionistas, dniNutricionistas;
    private String nutricionista;

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
        layoutAsignarNutricionista = findViewById(R.id.layoutAsignarNutricionista);
        txtNombrePaciente = findViewById(R.id.txtNombreCompletoPaciente);
        txtFechaNacimientoPaciente = findViewById(R.id.txtFechaNacimientoPaciente);
        txtTarjetaSanitariaPaciente = findViewById(R.id.txtNumeroTarjetaSanitaria);
        txtInputNutricionista = findViewById(R.id.txtInputNutricionista);
        dropdownNutricionista = findViewById(R.id.dropdownNutricionista);
        toolbar = findViewById(R.id.toolbarDatosPacienteMedico);
        btnAsignarNutricionista = findViewById(R.id.btnAsignarNutricionista);
        btnAsignar = findViewById(R.id.btnAsignar);
        btnOtrosDatos = findViewById(R.id.btnOtrosDatos);
        btnDieta = findViewById(R.id.btnDieta);
        btnEstadisticas = findViewById(R.id.btnEstadisticas);

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
            layoutAsignarNutricionista.setVisibility(View.VISIBLE);
            listaNutricionistasHospital(paciente.getHospital().getIdHospital());
        });

        dropdownNutricionista.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nutricionista = s.toString();
                txtInputNutricionista.setErrorEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnOtrosDatos.setOnClickListener(v -> {
            Intent intent = new Intent(this, OtrosDatosPacienteActivity.class);
            intent.putExtra("paciente", paciente);
            startActivity(intent);
        });

        btnDieta.setOnClickListener(v -> {
            Intent intent = new Intent(this, DietaActivity.class);
            intent.putExtra("paciente", paciente);
            startActivity(intent);
        });

        btnEstadisticas.setOnClickListener(v -> {
            Intent intent = new Intent(this, EstadisticasPacienteActivity.class);
            intent.putExtra("paciente", paciente);
            startActivity(intent);
        });

        btnAsignar.setOnClickListener(v -> {
            asignarNutricionista();
        });

    }

    private void asignarNutricionista(){
        int index = nombreCompletosNutricionistas.indexOf(nutricionista);
        if(index != -1){
            String dniSeleccionado = dniNutricionistas.get(index);

            usuarioViewModel.getUsuarioByDni(dniSeleccionado).observe(this, nutricionistaSeleccionado -> {
                if(nutricionistaSeleccionado != null){
                    usuarioViewModel.asociarPacienteNutricionista(paciente.getDni(), nutricionistaSeleccionado).observe(this, response -> {
                        if(response.getRpta() == 1){
                            ToastMessage.Correcto(this, "Nutricionista asociado correctamente al paciente");
                            dropdownNutricionista.setText("");
                            dropdownNutricionista.clearFocus();
                            layoutAsignarNutricionista.setVisibility(View.GONE);
                        }else{
                            ToastMessage.Invalido(this, "No se pudo asociar correctamente al paciente");
                        }
                    });
                }
            });

        }else{
            txtInputNutricionista.setError("Seleccione un nutricionista");
            ToastMessage.Invalido(this, "Selecciona un nutricionista");
        }
    }

    private void listaNutricionistasHospital(Long idHospital){
        nombreCompletosNutricionistas = new ArrayList<>();
        dniNutricionistas = new ArrayList<>();
        usuarioViewModel.getNutricionistasByHospital(idHospital).observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                if(usuarios != null){
                    for(Usuario nutricionista : usuarios){
                        String nombreCompleto = nutricionista.getNombreCompleto();
                        dniNutricionistas.add(nutricionista.getDni());
                        nombreCompletosNutricionistas.add(nombreCompleto);
                    }
                }
            }
        });
        ArrayAdapter<String> arrayNutricionistas = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombreCompletosNutricionistas);
        dropdownNutricionista.setAdapter(arrayNutricionistas);
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