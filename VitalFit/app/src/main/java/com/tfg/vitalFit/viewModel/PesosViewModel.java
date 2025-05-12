package com.tfg.vitalfit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Peso;
import com.tfg.vitalfit.repository.PesosRepository;

public class PesosViewModel extends AndroidViewModel {
    private final PesosRepository repository;

    public PesosViewModel(@NonNull Application application) {
        super(application);
        this.repository = PesosRepository.getInstance();
    }

    public LiveData<GenericResponse<Peso>> save(Peso p){
        return this.repository.save(p);
    }
}
