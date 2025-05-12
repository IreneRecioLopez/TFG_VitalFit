package com.tfg.vitalfit.activity.uiPaciente.configuracion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.databinding.FragmentConfiguracionBinding;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.viewModel.ConsejosViewModel;

public class ConfiguracionFragment extends Fragment {

    private FragmentConfiguracionBinding binding;

    private Usuario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConsejosViewModel consejosViewModel =
                new ViewModelProvider(this).get(ConsejosViewModel.class);

        binding = FragmentConfiguracionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        obtenerDatosUsuario(root);


        return root;
    }

    private void obtenerDatosUsuario(View view){
        //Obtener los datos del usuario
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        Log.d("UsuarioRecibido", new Gson().toJson(usuario));

        if(jsonUsuario != null){
            usuario = new Gson().fromJson(jsonUsuario, Usuario.class);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}