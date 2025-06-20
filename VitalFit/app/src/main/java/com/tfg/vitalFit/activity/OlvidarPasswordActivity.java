package com.tfg.vitalfit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.utils.Security;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;


public class OlvidarPasswordActivity extends AppCompatActivity {
    private Button btnOlvidarPassword;
    private UsuarioViewModel uViewModel;
    private Toolbar toolbar;
    private EditText edtDNI, edtPassword, edtPasswordVal;
    private TextInputLayout txtInputDNI, txtInputPassword, txtInputPasswordVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_password);
        this.initViewModel();
        this.init();
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        uViewModel = vmp.get(UsuarioViewModel.class);

    }

    private void init(){
        toolbar = findViewById(R.id.toolbarOlvidoPassword);
        btnOlvidarPassword = findViewById(R.id.btnOlvidoPassword);
        edtDNI = findViewById(R.id.edtDNI);
        edtPassword = findViewById(R.id.edtPassword);
        edtPasswordVal = findViewById(R.id.edtPasswordValidacion);
        txtInputDNI = findViewById(R.id.txtInputUsuarioDNI);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        txtInputPasswordVal = findViewById(R.id.txtInputPasswordValidacion);

        // Habilitar el botón de regreso en el Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listeners();

        btnOlvidarPassword.setOnClickListener(v -> {
            try{
                if(validar()){
                   String dni = edtDNI.getText().toString();
                   String password = Security.encriptar(edtPassword.getText().toString());
                    uViewModel.actualizarPassword(dni, password).observe(this, response -> {
                        if(response.getRpta() == 1){
                            ToastMessage.Correcto(this, response.getMessage());
                            startActivity(new Intent(this, MainActivity.class));
                        }else{
                            ToastMessage.Invalido(this, "No se ha podido actualizar la contraseña");
                        }
                    });
                }else{
                    ToastMessage.Invalido(this, "Por favor, complete todos los campos");
                }

            }catch (Exception e){
                ToastMessage.Invalido(this, "Se ha producido un error al intentar cambiar la contraseña " + e.getMessage());
            }
        });
    }

    private boolean validar(){
        boolean val = true;
        String usuario, password, passwordVal;
        usuario = edtDNI.getText().toString();
        password = edtPassword.getText().toString();
        passwordVal = edtPasswordVal.getText().toString();
        if(usuario.isEmpty()){
            txtInputDNI.setError("Ingrese su dni");
            val = false;
        }else{
            txtInputDNI.setErrorEnabled(false);
        }
        if(password.isEmpty()){
            txtInputPassword.setError("Introduzca su contraseña");
            val = false;
        }else{
            txtInputPassword.setErrorEnabled(false);
        }
        if(passwordVal.isEmpty()){
            txtInputPasswordVal.setError("Introduzca su contraseña de validación");
            val = false;
        }else{
            txtInputPasswordVal.setErrorEnabled(false);
        }

        if(!password.equals(passwordVal)){
            ToastMessage.Invalido( this, "La contraseña y la contraseña de validación no son iguales");
            val = false;
        }
        return val;
    }

    public void listeners(){
        edtDNI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputDNI.setErrorEnabled(false);
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
        edtPasswordVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputPasswordVal.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
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
