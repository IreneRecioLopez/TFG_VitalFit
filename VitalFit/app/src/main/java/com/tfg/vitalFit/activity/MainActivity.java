package com.tfg.vitalfit.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
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
    private TextInputLayout txtInputUsuario, txtInputPassword;
    private CheckBox chkPaciente, chkMedico, chkNutricionista;
    private TextView txtNuevoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initViewModel();
        this.init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        String pref = preference.getString("PacienteJson", "");
        if(!pref.equals("")){
            toastCorrecto("Se detectó una sesión activa, el login sera omitido");
            this.startActivity(new Intent(this, InicioActivity.class));
            this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }else{
            pref = preference.getString("MedicoJson", "");
            if(!pref.equals("")){
                toastCorrecto("Se detectó una sesión activa, el login sera omitido");
                this.startActivity(new Intent(this, InicioActivity.class));
                this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }else {
                pref = preference.getString("NutricionistaJson", "");
                if (!pref.equals("")) {
                    toastCorrecto("Se detectó una sesión activa, el login sera omitido");
                    this.startActivity(new Intent(this, InicioActivity.class));
                    this.overridePendingTransition(R.anim.left_in, R.anim.left_out);

                }
            }
        }
    }

    private void initViewModel() {
        pViewModel = new ViewModelProvider(this).get(PacienteViewModel.class);
        mViewModel = new ViewModelProvider(this).get(MedicoViewModel.class);
        nViewModel = new ViewModelProvider(this).get(NutricionistaViewModel.class);
    }

    private void init(){
        edtDNI = findViewById(R.id.edtDNI);
        edtPassword = findViewById(R.id.edtPassword);
        txtInputUsuario = findViewById(R.id.txtInputUsuario);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        txtNuevoUsuario = findViewById(R.id.txtNuevoUsuario);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        chkPaciente = findViewById(R.id.chkPaciente);
        chkMedico = findViewById(R.id.chkMedico);
        chkNutricionista = findViewById(R.id.chkNutricionista);

        btnIniciarSesion.setOnClickListener(v -> {
            try{
                if(validar() && validarSeleccionUsuario()){
                    String dni = edtDNI.getText().toString();
                    String password = edtPassword.getText().toString();

                    if (chkPaciente.isChecked()) {
                        pViewModel.login(dni, password).observe(this, response -> manejarRespuestaPaciente(response));
                    } else if (chkMedico.isChecked()) {
                        mViewModel.login(dni, password).observe(this, response -> manejarRespuestaMedico(response));
                    } else if (chkNutricionista.isChecked()) {
                        nViewModel.login(dni, password).observe(this, response -> manejarRespuestaNutricionista(response));
                    }
                }else{
                    toastInvalido("Por favor, complete todos los campos.");
                }
            }catch(Exception e){
                toastInvalido("Se ha producido un error al intentar iniciar sesión: " + e.getMessage());
            }
        });
        txtNuevoUsuario.setOnClickListener(v -> {
            startActivity(new Intent(this, SeleccionTipoUsuarioActivity.class));
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        });
        edtDNI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputUsuario.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void toastCorrecto(String msg){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastOk);
        txtMensaje.setText(msg);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void toastInvalido(String msg){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_bad, (ViewGroup) findViewById(R.id.ll_custom_toast_bad));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastBad);
        txtMensaje.setText(msg);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
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

    private boolean validar(){
        boolean val = true;
        String usuario, password;
        usuario = edtDNI.getText().toString();
        password = edtPassword.getText().toString();
        if(usuario.isEmpty()){
            txtInputUsuario.setError("Ingrese su dni");
            val = false;
        }else{
            txtInputUsuario.setErrorEnabled(false);
        }
        if(password.isEmpty()){
            txtInputPassword.setError("Introduzca su contraseña");
            val = false;
        }else{
            txtInputPassword.setErrorEnabled(false);
        }
        return val;
    }

    private boolean validarSeleccionUsuario() {
        int cnt = 0;

        if (chkPaciente.isChecked()) cnt++;
        if (chkMedico.isChecked()) cnt++;
        if (chkNutricionista.isChecked()) cnt++;

        if (cnt == 0) {
            toastInvalido("Debes seleccionar un tipo de usuario.");
            return false;
        } else if (cnt > 1) {
            toastInvalido( "Solo puedes seleccionar un tipo de usuario.");
            return false;
        }

        return true;
    }

    private void manejarRespuestaPaciente(GenericResponse<Paciente> response){
        if(response.getRpta() == 1){
            //Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            toastCorrecto(response.getMessage());
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
            //Toast.makeText(this, "Ocurrio un error " + response.getMessage(), Toast.LENGTH_SHORT).show();
            toastInvalido(response.getMessage());
        }
    }

    private void manejarRespuestaMedico(GenericResponse<Medico> response){
        if(response.getRpta() == 1){
            //Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            toastCorrecto(response.getMessage());
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
            //Toast.makeText(this, "Ocurrio un error " + response.getMessage(), Toast.LENGTH_SHORT).show();
            toastInvalido(response.getMessage());
        }
    }

    private void manejarRespuestaNutricionista(GenericResponse<Nutricionista> response){
        if(response.getRpta() == 1){
            //Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            toastCorrecto(response.getMessage());
            Nutricionista n = response.getBody();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            final Gson g = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateSerializer())
                    .registerTypeAdapter(Time.class, new TimeSerializer())
                    .create();
            editor.putString("NutricionistaJson", g.toJson(n, new TypeToken<Nutricionista>(){
            }.getType()));
            edtDNI.setText("");
            edtPassword.setText("");
            startActivity(new Intent(this, InicioActivity.class));
        } else{
            //Toast.makeText(this, "Ocurrio un error " + response.getMessage(), Toast.LENGTH_SHORT).show();
            toastInvalido(response.getMessage());
        }
    }

}