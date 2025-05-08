package com.tfg.vitalfit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Observaciones;
import com.tfg.vitalfit.repository.ObservacionesRepository;

public class ObservacionesViewModel extends AndroidViewModel {
    private final ObservacionesRepository repository;

    public ObservacionesViewModel(@NonNull Application application) {
        super(application);
        this.repository = ObservacionesRepository.getInstance();
    }

    public LiveData<GenericResponse<Observaciones>> save(Observaciones o){ return this.repository.save(o); }

    public LiveData<GenericResponse<Void>> delete(Long idObservacion){
        return this.repository.eliminarOperacion(idObservacion);
    }
}
