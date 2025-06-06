package com.tfg.vitalfit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.repository.PacienteRepository;

public class PacienteViewModel extends AndroidViewModel {

    private final PacienteRepository repository;

    public PacienteViewModel(@NonNull Application application) {
        super(application);
        this.repository = PacienteRepository.getInstance();
    }

    public LiveData<GenericResponse<Paciente>> save(Paciente p){
        return this.repository.save(p);
    }

    public LiveData<GenericResponse<Void>> actualizarPaciente(Paciente p){
        return this.repository.actualizarPaciente(p);
    }

    public LiveData<Paciente> pacienteByDNI(String dni){ return this.repository.pacienteByDNI(dni); }

}
