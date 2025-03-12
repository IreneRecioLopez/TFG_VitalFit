package com.tfg.vitalfit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.viewModel.MedicoViewModel;
import com.tfg.vitalfit.viewModel.NutricionistaViewModel;
import com.tfg.vitalfit.viewModel.PacienteViewModel;

public class SeleccionTipoUsuarioActivity extends AppCompatActivity {

    private CheckBox chkPaciente, chkMedico, chkNutricionista;
    private Button btnContinuar;
    private PacienteViewModel pViewModel;
    private NutricionistaViewModel nViewModel;
    private MedicoViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_tipo_usuario);
        this.initViewModel();
        this.init();
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        pViewModel = vmp.get(PacienteViewModel.class);
        mViewModel = vmp.get(MedicoViewModel.class);
        nViewModel = vmp.get(NutricionistaViewModel.class);
    }

    private void init(){
        btnContinuar = findViewById(R.id.btnContinuarRegistro);
        chkPaciente = findViewById(R.id.chkPacienteUsuario);
        chkMedico = findViewById(R.id.chkMedicoUsuario);
        chkNutricionista = findViewById(R.id.chkNutricionistaUsuario);

        btnContinuar.setOnClickListener(v -> {
            if(validarSeleccionUsuario()){
                if(chkPaciente.isChecked()){
                    startActivity(new Intent(this, RegistroPacienteActivity.class));
                }else if(chkMedico.isChecked()) {
                    startActivity(new Intent(this, RegistroMedicoActivity.class));
                }else if(chkNutricionista.isChecked()){
                    startActivity(new Intent(this, InicioActivity.class));
                }
            }else{
                toastInvalido("Por favor, complete todos los campos.");
            }
        });
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