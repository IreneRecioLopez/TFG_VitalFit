package com.tfg.vitalfit.activity.uiPaciente.consejos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.adapter.ConsejoAdapter;
import com.tfg.vitalfit.databinding.FragmentConsejosBinding;
import com.tfg.vitalfit.entity.service.Consejo;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.viewModel.ConsejosViewModel;

import java.util.List;

public class ConsejosFragment extends Fragment {

    private FragmentConsejosBinding binding;

    private RecyclerView recyclerView;
    private Usuario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConsejosViewModel consejosViewModel =
                new ViewModelProvider(this).get(ConsejosViewModel.class);

        binding = FragmentConsejosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.recyclerConsejos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        obtenerDatosUsuario(root);

        consejosViewModel.consejosPorPaciente(usuario.getDni()).observe(getViewLifecycleOwner(), consejos ->{
            ConsejoAdapter adapter = new ConsejoAdapter(getContext(), consejos);
            recyclerView.setAdapter(adapter);
        });

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