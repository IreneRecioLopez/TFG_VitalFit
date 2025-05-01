package com.tfg.vitalfit.activity.uiNutricionista.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.tfg.vitalfit.databinding.FragmentHomeNutricionistaBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeNutricionistaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initViewModel();

        binding = FragmentHomeNutricionistaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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