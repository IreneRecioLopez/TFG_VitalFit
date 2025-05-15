package com.tfg.vitalfit.activity.uiMedicoNutricionista.configuracion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.tfg.vitalfit.databinding.FragmentConfiguracionMedicoNutricionistaBinding;

public class ConfiguracionMedicoNutricionistaFragment extends Fragment {

    private FragmentConfiguracionMedicoNutricionistaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentConfiguracionMedicoNutricionistaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}