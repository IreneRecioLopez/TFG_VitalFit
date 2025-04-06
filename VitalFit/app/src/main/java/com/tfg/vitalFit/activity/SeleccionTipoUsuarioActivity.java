package com.tfg.vitalfit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.viewModel.MedicoViewModel;
import com.tfg.vitalfit.viewModel.NutricionistaViewModel;
import com.tfg.vitalfit.viewModel.PacienteViewModel;

public class SeleccionTipoUsuarioActivity extends AppCompatActivity {

    private CheckBox chkPaciente, chkMedico, chkNutricionista;
    private Button btnContinuar;
    private Toolbar toolbar;
    private Boolean isPaciente, isMedico, isNutricionista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_tipo_usuario);
        this.init();
    }

    private void init(){
        btnContinuar = findViewById(R.id.btnContinuarRegistro);
        chkPaciente = findViewById(R.id.chkPacienteUsuario);
        chkMedico = findViewById(R.id.chkMedicoUsuario);
        chkNutricionista = findViewById(R.id.chkNutricionistaUsuario);

        toolbar = findViewById(R.id.toolbarEleccionUsuario);
        setSupportActionBar(toolbar);

        // Habilitar el botón de regreso en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listeners();

        btnContinuar.setOnClickListener(v -> {
            if(isPaciente){
                chkPaciente.setChecked(false);
                startActivity(new Intent(this, RegistroPacienteActivity.class));
            }else if(isMedico) {
                chkMedico.setChecked(false);
                startActivity(new Intent(this, RegistroMedicoActivity.class));
            }else if(isNutricionista){
                chkNutricionista.setChecked(false);
                startActivity(new Intent(this, RegistroNutricionistaActivity.class));
            }else{
                toastInvalido("Por favor, seleccione un tipo de usuario.");
            }
        });
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

    private void listeners(){
        chkPaciente.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isPaciente = true;
                isNutricionista = true;
                isMedico = false;
                chkNutricionista.setChecked(false);
                chkMedico.setChecked(false);
            }
        });
        chkMedico.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isMedico = true;
                isNutricionista = false;
                isPaciente = false;
                chkNutricionista.setChecked(false);
                chkPaciente.setChecked(false);
            }
        });
        chkNutricionista.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                isNutricionista = true;
                isPaciente = false;
                isMedico = false;
                chkPaciente.setChecked(false);
                chkMedico.setChecked(false);
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