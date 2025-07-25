package com.tfg.vitalfit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Operacion;
import com.tfg.vitalfit.repository.OperacionesRepository;

public class OperacionesViewModel extends AndroidViewModel {
    private final OperacionesRepository repository;

    public OperacionesViewModel(@NonNull Application application) {
        super(application);
        this.repository = OperacionesRepository.getInstance();
    }

    public LiveData<GenericResponse<Operacion>> save(Operacion op){
        return this.repository.save(op);
    }

    public LiveData<GenericResponse<Void>> delete(Long idOperacion){ return this.repository.eliminarOperacion(idOperacion);}
}
