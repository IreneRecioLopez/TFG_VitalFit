package com.tfg.vitalfit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.tfg.vitalfit.R;
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
        btnOlvidarPassword = findViewById(R.id.btnOlvidoPassword);
        edtDNI = findViewById(R.id.edtDNI);
        edtPassword = findViewById(R.id.edtPassword);
        edtPasswordVal = findViewById(R.id.edtPasswordValidacion);
        txtInputDNI = findViewById(R.id.txtInputUsuarioDNI);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        txtInputPasswordVal = findViewById(R.id.txtInputPasswordValidacion);

        listeners();

        btnOlvidarPassword.setOnClickListener(v -> {
            try{
                if(validar()){
                   String dni = edtDNI.getText().toString();
                   String password = edtPassword.getText().toString();
                    uViewModel.actualizarPassword(dni, password).observe(this, response -> {
                        if(response.getRpta() == 1){
                            toastCorrecto(response.getMessage());
                            startActivity(new Intent(this, MainActivity.class));
                        }else{
                            toastInvalido("No se ha podido actualizar la contraseña");
                        }
                    });
                }else{
                    toastInvalido("Por favor, complete todos los campos");
                }

            }catch (Exception e){
                toastInvalido("Se ha producido un error al intentar cambiar la contraseña " + e.getMessage());
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
            toastInvalido("La contraseña y la contraseña de validación no son iguales");
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
}
