package com.tfg.vitalfit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Medico;
import com.tfg.vitalfit.repository.MedicoRepository;

public class MedicoViewModel extends AndroidViewModel {
    private final MedicoRepository repository;

    public MedicoViewModel(@NonNull Application application) {
        super(application);
        this.repository = MedicoRepository.getInstance();
    }

    public LiveData<GenericResponse<Medico>> login(String dni, String password){
        return this.repository.login(dni, password);
    }

    public LiveData<GenericResponse<Medico>> save(Medico m){
        return this.repository.save(m);
    }

    public LiveData<GenericResponse<Void>> asociarMedicoHospital(String dniMedico, Hospital hospital){
        return this.repository.asociarMedicoHospital(dniMedico, hospital);
    }
}
