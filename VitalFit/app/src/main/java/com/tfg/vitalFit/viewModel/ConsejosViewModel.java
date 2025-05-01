package com.tfg.vitalfit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Consejo;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.repository.ConsejosRepository;

import java.util.List;

public class ConsejosViewModel extends AndroidViewModel {
    private final ConsejosRepository repository;

    public ConsejosViewModel(@NonNull Application application){
        super(application);
        this.repository = ConsejosRepository.getInstance();
    }

    public LiveData<List<Consejo>> consejosPorPaciente(String dni){
        return this.repository.consejosPorPaciente(dni);
    }

    public LiveData<GenericResponse<Void>> marcarComoLeido(Consejo c){
        return this.repository.marcarComoLeido(c);
    }
}
