package com.tfg.vitalfit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Alergia;
import com.tfg.vitalfit.repository.AlergiasRepository;

public class AlergiasViewModel extends AndroidViewModel {
    private final AlergiasRepository repository;

    public AlergiasViewModel(@NonNull Application application) {
        super(application);
        this.repository = AlergiasRepository.getInstance();
    }

    public LiveData<GenericResponse<Alergia>> save(Alergia a){
        return this.repository.save(a);
    }

    public LiveData<GenericResponse<Void>> delete(Long idAlergia){ return this.repository.eliminarAlergia(idAlergia); }
}
