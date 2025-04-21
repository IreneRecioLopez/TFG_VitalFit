package com.tfg.vitalfit.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.entity.service.Nutricionista;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.utils.DateSerializer;
import com.tfg.vitalfit.utils.Security;
import com.tfg.vitalfit.utils.TimeSerializer;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;
import com.tfg.vitalfit.viewModel.NutricionistaViewModel;
import com.tfg.vitalfit.viewModel.PacienteViewModel;

import java.sql.Date;
import java.sql.Time;


public class MainActivity extends AppCompatActivity {

    private EditText edtDNI, edtPassword;
    private Button btnIniciarSesion;
    private PacienteViewModel pViewModel;
    private NutricionistaViewModel nViewModel;
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
        if(!pref.equals("")){
            ToastMessage.Correcto(this, "Se detectó una sesión activa, el login sera omitido");
            this.startActivity(new Intent(this, InicioActivity.class));
            this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }
    }

    private void initViewModel() {
        pViewModel = new ViewModelProvider(this).get(PacienteViewModel.class);
        uViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        nViewModel = new ViewModelProvider(this).get(NutricionistaViewModel.class);
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

    private void manejarRespuestaUsuario(GenericResponse<Usuario> response){
        if(response.getRpta() == 1){
            //ToastMessage.makeText(this, response.getMessage(), ToastMessage.LENGTH_SHORT).show();
            ToastMessage.Correcto(this, response.getMessage());
            Usuario u = response.getBody();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            final Gson g = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateSerializer())
                    .registerTypeAdapter(Time.class, new TimeSerializer())
                    .create();
            editor.putString("UsuarioJson", g.toJson(u, new TypeToken<Usuario>(){
            }.getType()));
            edtDNI.setText("");
            edtPassword.setText("");
            String rol = u.getRol();
            if(rol.equals("Paciente")){
                Log.e("Inicio Sesion", "paciente");
                startActivity(new Intent(this, InicioActivity.class));
            }else if(rol.equals("Médico")){
                Log.e("Inicio Sesion", "medico");
                startActivity(new Intent(this, InicioMedicoActivity.class));
            }else if(rol.equals("Nutricionista")){
                Log.e("Inicio Sesion", "nutricionista");
                startActivity(new Intent(this, InicioNutricionistaActivity.class));
            }
        } else{
            //ToastMessage.makeText(this, "Ocurrio un error " + response.getMessage(), ToastMessage.LENGTH_SHORT).show();
            ToastMessage.Invalido(this, response.getMessage());
        }
    }


}