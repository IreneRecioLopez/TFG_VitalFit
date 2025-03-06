package com.tfg.vitalfit.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Medico;
import com.tfg.vitalfit.entity.service.Nutricionista;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.utils.DateSerializer;
import com.tfg.vitalfit.utils.TimeSerializer;
import com.tfg.vitalfit.viewModel.MedicoViewModel;
import com.tfg.vitalfit.viewModel.NutricionistaViewModel;
import com.tfg.vitalfit.viewModel.PacienteViewModel;

import java.sql.Date;
import java.sql.Time;



public class MainActivity extends AppCompatActivity {

    private EditText edtDNI, edtPassword;
    private Button btnIniciarSesion;
    private PacienteViewModel pViewModel;
    private NutricionistaViewModel nViewModel;
    private MedicoViewModel mViewModel;
    private TextView txtInputUsuario, txtInputPassword;
    private CheckBox chkPaciente, chkMedico, chkNutricionista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initViewModel();
        this.init();

    }

    private void initViewModel() {
        pViewModel = new ViewModelProvider(this).get(PacienteViewModel.class);
        mViewModel = new ViewModelProvider(this).get(MedicoViewModel.class);
        nViewModel = new ViewModelProvider(this).get(NutricionistaViewModel.class);
    }

    private void init(){
        edtDNI = findViewById(R.id.edtDNI);
        edtPassword = findViewById(R.id.edtPassword);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        chkPaciente = findViewById(R.id.chkPaciente);
        chkMedico = findViewById(R.id.chkMedico);
        chkNutricionista = findViewById(R.id.chkNutricionista);

        btnIniciarSesion.setOnClickListener(v -> {
            String email = edtDNI.getText().toString();
            String password = edtPassword.getText().toString();

            if (chkPaciente.isChecked()) {
                pViewModel.login(email, password).observe(this, response -> manejarRespuestaPaciente(response));
            } else if (chkMedico.isChecked()) {
                mViewModel.login(email, password).observe(this, response -> manejarRespuestaMedico(response));
            } else if (chkNutricionista.isChecked()) {
                nViewModel.login(email, password).observe(this, response -> manejarRespuestaNutricionista(response));
            }
        });
    }

    private String obtenerTipoUsuario(){
        if(chkPaciente.isChecked()){
            return "PACIENTE";
        }else if(chkMedico.isChecked()){
            return "MEDICO";
        }else if(chkNutricionista.isChecked()){
            return "NUTRICIONISTA";
        }else{
            return "";
        }
    }

    private boolean validarSeleccionUsuario() {
        int cnt = 0;

        if (chkPaciente.isChecked()) cnt++;
        if (chkMedico.isChecked()) cnt++;
        if (chkNutricionista.isChecked()) cnt++;

        if (cnt == 0) {
            Toast.makeText(this, "Debes seleccionar un tipo de usuario.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (cnt > 1) {
            Toast.makeText(this, "Solo puedes seleccionar un tipo de usuario.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void manejarRespuestaPaciente(GenericResponse<Paciente> response){
        if(response.getRpta() == 1){
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            Paciente p = response.getBody();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            final Gson g = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateSerializer())
                    .registerTypeAdapter(Time.class, new TimeSerializer())
                    .create();
            editor.putString("PacienteJson", g.toJson(p, new TypeToken<Paciente>(){
            }.getType()));
            edtDNI.setText("");
            edtPassword.setText("");
            startActivity(new Intent(this, InicioActivity.class));
        } else{
            Toast.makeText(this, "Ocurrio un error " + response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void manejarRespuestaMedico(GenericResponse<Medico> response){
        if(response.getRpta() == 1){
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            Medico m = response.getBody();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            final Gson g = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateSerializer())
                    .registerTypeAdapter(Time.class, new TimeSerializer())
                    .create();
            editor.putString("MedicoJson", g.toJson(m, new TypeToken<Medico>(){
            }.getType()));
            edtDNI.setText("");
            edtPassword.setText("");
            startActivity(new Intent(this, InicioActivity.class));
        } else{
            Toast.makeText(this, "Ocurrio un error " + response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void manejarRespuestaNutricionista(GenericResponse<Nutricionista> response){
        if(response.getRpta() == 1){
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            Nutricionista n = response.getBody();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            final Gson g = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateSerializer())
                    .registerTypeAdapter(Time.class, new TimeSerializer())
                    .create();
            editor.putString("NutricionistaJson", g.toJson(n, new TypeToken<Paciente>(){
            }.getType()));
            edtDNI.setText("");
            edtPassword.setText("");
            startActivity(new Intent(this, InicioActivity.class));
        } else{
            Toast.makeText(this, "Ocurrio un error " + response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}