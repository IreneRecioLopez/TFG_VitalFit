package com.tfg.vitalfit.activity.uiNutricionista.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.tfg.vitalfit.activity.LeerConsejosActivity;
import com.tfg.vitalfit.activity.VerEnviarConsejosActivity;
import com.tfg.vitalfit.databinding.FragmentHomeMedicoBinding;
import com.tfg.vitalfit.databinding.FragmentHomeNutricionistaBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeMedicoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initViewModel();

        binding = FragmentHomeMedicoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnContinuarMedico.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), VerEnviarConsejosActivity.class));
        });

        return root;
    }

    private void initViewModel(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}