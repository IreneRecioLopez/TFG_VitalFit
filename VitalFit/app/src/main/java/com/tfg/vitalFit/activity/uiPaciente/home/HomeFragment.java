package com.tfg.vitalfit.activity.uiPaciente.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.tfg.vitalfit.activity.DietaActivity;
import com.tfg.vitalfit.activity.LeerConsejosActivity;
import com.tfg.vitalfit.activity.OtrosDatosPacienteActivity;
import com.tfg.vitalfit.databinding.FragmentHomeBinding;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.entity.service.Peso;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.Fecha;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.PacienteViewModel;
import com.tfg.vitalfit.viewModel.PesosViewModel;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SharedPreferences prefs;
    private static final String PREF_NAME = "MisPreferencias";
    private static final String KEY_LAST_USED_DATE = "ultima_fecha_uso";
    private UsuarioViewModel usuarioViewModel;
    private PacienteViewModel pacienteViewModel;
    private PesosViewModel pesosViewModel;
    private Usuario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        initViewModel();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prefs = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        obtenerDatosUsuario(root);

        if(usuario != null){
            binding.txtNombreCompletoPaciente.setText(usuario.getNombreCompleto());
            binding.txtFechaNacimientoPaciente.setText(Fecha.obtenerFecha(usuario.getPaciente().getFechaNacimiento()));
            binding.txtNumeroSeguridadSocial.setText(usuario.getPaciente().getNumSeguridadSocial());
        }

        String ultimaFechaUso = prefs.getString(KEY_LAST_USED_DATE, "");
        String fechaActual = Fecha.obtenerFechaActual();

        if (!fechaActual.equals(ultimaFechaUso)) {
            binding.edtPesoP.setEnabled(true);  // habilita si es un nuevo día
        } else {
            binding.edtPesoP.setText(usuario.getPaciente().getPesoActual().toString());
            binding.edtPesoP.setEnabled(false); // deshabilita si ya fue usado hoy
        }

        binding.btnLeerConsejos.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LeerConsejosActivity.class));
        });

        binding.btnOtrosDatos.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), OtrosDatosPacienteActivity.class);
            intent.putExtra("paciente", usuario);
            startActivity(intent);
        });

        binding.btnDieta.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DietaActivity.class);
            intent.putExtra("paciente", usuario);
            startActivity(intent);
        });

        binding.btnGuardarPeso.setOnClickListener(v -> {
            registrarPeso();
        });

        return root;
    }

    private void initViewModel(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        usuarioViewModel = vmp.get(UsuarioViewModel.class);
        pacienteViewModel = vmp.get(PacienteViewModel.class);
        pesosViewModel = vmp.get(PesosViewModel.class);
    }

    public void registrarPeso(){
        if(validar()){
            Peso peso = new Peso();
            Double p = Double.parseDouble(binding.edtPesoP.getText().toString());
            peso.setPeso(p);
            peso.setFecha(Fecha.obtenerFechaActual());
            peso.setPaciente(new Paciente(usuario.getPaciente().getDni()));
            pesosViewModel.save(peso).observe(getViewLifecycleOwner(), response ->{
                if(response.getRpta() == 1){
                    Paciente updatePaciente = usuario.getPaciente();
                    updatePaciente.setPesoActual(p);
                    Double altura = updatePaciente.getAltura();
                    Double imc = p / (altura * altura);
                    imc = Math.round(imc * 1000.0) / 1000.0;
                    updatePaciente.setImc(imc);
                    pacienteViewModel.actualizarPaciente(updatePaciente).observe(getViewLifecycleOwner(), updateResponse ->{
                        if(updateResponse.getRpta() == 1){
                            ToastMessage.Correcto(getContext(), "Paciente actualizado correctamente");
                            binding.edtPesoP.setEnabled(false);
                            prefs.edit().putString(KEY_LAST_USED_DATE, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())).apply();
                        }
                    });
                    ToastMessage.Correcto(getContext(), "Peso registrado correctamente");
                }else{
                    ToastMessage.Invalido(getContext(), "Error al registrar el peso");
                }
            });
        }
    }

    private boolean validar(){
        if(binding.edtPesoP.getText().toString().isEmpty()){
            binding.txtInputPesoP.setError("Insertar peso");
            return false;
        }else{
            binding.txtInputPesoP.setErrorEnabled(false);
            return true;
        }
    }

    private void obtenerDatosUsuario(View view){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        Log.d("UsuarioRecibidoHomeFragment", new Gson().toJson(usuario));

        if(jsonUsuario != null) {
            usuario = new Gson().fromJson(jsonUsuario, Usuario.class);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}