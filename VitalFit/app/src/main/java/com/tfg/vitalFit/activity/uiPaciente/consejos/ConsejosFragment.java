package com.tfg.vitalfit.activity.uiPaciente.consejos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tfg.vitalfit.databinding.FragmentConsejosBinding;

public class ConsejosFragment extends Fragment {

    private FragmentConsejosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConsejosViewModel slideshowViewModel =
                new ViewModelProvider(this).get(ConsejosViewModel.class);

        binding = FragmentConsejosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}