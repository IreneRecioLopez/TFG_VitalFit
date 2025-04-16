package com.tfg.vitalfit.activity.uiPaciente.datosPersonales;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tfg.vitalfit.databinding.FragmentDatospersonalesBinding;

public class DatosPersonalesFragment extends Fragment {

    private FragmentDatospersonalesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DatosPersonalesViewModel datosPersonalesViewModel =
                new ViewModelProvider(this).get(DatosPersonalesViewModel.class);

        binding = FragmentDatospersonalesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}