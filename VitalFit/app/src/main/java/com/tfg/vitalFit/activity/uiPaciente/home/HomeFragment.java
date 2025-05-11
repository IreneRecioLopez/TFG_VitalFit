package com.tfg.vitalfit.activity.uiPaciente.home;

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
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.tfg.vitalfit.activity.DietaActivity;
import com.tfg.vitalfit.activity.LeerConsejosActivity;
import com.tfg.vitalfit.activity.OtrosDatosPacienteActivity;
import com.tfg.vitalfit.databinding.FragmentHomeBinding;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private UsuarioViewModel usuarioViewModel;
    private Usuario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        initViewModel();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        obtenerDatosUsuario(root);

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

        return root;
    }

    private void initViewModel(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        usuarioViewModel = vmp.get(UsuarioViewModel.class);
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