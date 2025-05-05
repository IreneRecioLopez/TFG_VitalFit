package com.tfg.vitalfit.activity.uiPaciente.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tfg.vitalfit.activity.LeerConsejosActivity;
import com.tfg.vitalfit.databinding.FragmentHomeBinding;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button leerMensajes;
    private UsuarioViewModel usuarioViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        initViewModel();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.leerConsejos.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), LeerConsejosActivity.class));
        });

        return root;
    }

    private void initViewModel(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        usuarioViewModel = vmp.get(UsuarioViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}