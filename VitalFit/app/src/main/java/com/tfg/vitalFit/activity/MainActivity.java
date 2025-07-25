package com.tfg.vitalfit.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.DateSerializer;
import com.tfg.vitalfit.utils.Security;
import com.tfg.vitalfit.utils.TimeSerializer;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

import java.sql.Date;
import java.sql.Time;


public class MainActivity extends AppCompatActivity {

    private EditText edtDNI, edtPassword;
    private Button btnIniciarSesion;
    private UsuarioViewModel uViewModel;
    private TextInputLayout txtInputUsuario, txtInputPassword;
    private CheckBox chkPaciente, chkMedico, chkNutricionista;
    private TextView txtNuevoUsuario, txtForgetPassword;

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
        String pref = preference.getString("UsuarioJson", "");
    }

    private void initViewModel() {
        uViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }

    private void init(){
        edtDNI = findViewById(R.id.edtDNI);
        edtPassword = findViewById(R.id.edtPassword);
        txtInputUsuario = findViewById(R.id.txtInputUsuario);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        txtNuevoUsuario = findViewById(R.id.txtNuevoUsuario);
        txtForgetPassword = findViewById(R.id.txtForgetPassword);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        chkPaciente = findViewById(R.id.chkPaciente);
        chkMedico = findViewById(R.id.chkMedico);
        chkNutricionista = findViewById(R.id.chkNutricionista);

        listeners();

        btnIniciarSesion.setOnClickListener(v -> {
            try{
                if(validar()){
                    String dni = edtDNI.getText().toString();
                    String password = Security.encriptar(edtPassword.getText().toString());
                    //método para obtener usuario y despues comprobar el tipo de rol
                    uViewModel.login(dni, password).observe(this, response -> manejarRespuestaUsuario(response));
                }else{
                    ToastMessage.Invalido(this, "Por favor, complete todos los campos.");

                }
            }catch(Exception e){
                ToastMessage.Invalido(this, "Se ha producido un error al intentar iniciar sesión: " + e.getMessage());
            }
        });
        txtNuevoUsuario.setOnClickListener(v -> {
            startActivity(new Intent(this, SeleccionTipoUsuarioActivity.class));
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        });
        txtForgetPassword.setOnClickListener(v -> {
            startActivity(new Intent(this, OlvidarPasswordActivity.class));
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        });

    }

    private void manejarRespuestaUsuario(GenericResponse<Usuario> response) {
        if (response.getRpta() == 1) {
            ToastMessage.Correcto(this, response.getMessage());
            Usuario u = response.getBody();

            SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            prefs.edit().putString("dni", u.getDni()).apply();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            final Gson g = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateSerializer())
                    .registerTypeAdapter(Time.class, new TimeSerializer())
                    .create();
            editor.putString("UsuarioJson", g.toJson(u, new TypeToken<Usuario>() {}.getType()));
            editor.apply();
            edtDNI.setText("");
            edtPassword.setText("");
            edtDNI.clearFocus();
            edtPassword.clearFocus();
            String rol = u.getRol();
            if (rol.equals("Paciente")) {
                chkPaciente.setChecked(true);
                startActivity(new Intent(this, InicioActivity.class));
            } else if (rol.equals("Médico")) {
                chkMedico.setChecked(true);
                startActivity(new Intent(this, InicioMedicoNutricionistaActivity.class));
            } else if (rol.equals("Nutricionista")) {
                chkNutricionista.setChecked(true);
                startActivity(new Intent(this, InicioMedicoNutricionistaActivity.class));
            }
        } else {
            ToastMessage.Invalido(this, response.getMessage());
        }
        chkPaciente.setChecked(false);
        chkMedico.setChecked(false);
        chkNutricionista.setChecked(false);
    }

    private void listeners(){
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
        chkPaciente.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                chkNutricionista.setChecked(false);
                chkMedico.setChecked(false);
            }
        });
        chkMedico.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                chkNutricionista.setChecked(false);
                chkPaciente.setChecked(false);
            }
        });
        chkNutricionista.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                chkPaciente.setChecked(false);
                chkMedico.setChecked(false);
            }
        });
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

}