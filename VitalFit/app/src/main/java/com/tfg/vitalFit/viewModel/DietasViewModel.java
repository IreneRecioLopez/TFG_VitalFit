package com.tfg.vitalfit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Dieta;
import com.tfg.vitalfit.entity.service.dto.DietaConPlatosDTO;
import com.tfg.vitalfit.entity.service.dto.GenerarDietaDTO;
import com.tfg.vitalfit.repository.DietasRepository;

import java.util.List;

public class DietasViewModel extends AndroidViewModel {
    private final DietasRepository repository;

    public DietasViewModel(@NonNull Application application){
        super(application);
        this.repository = DietasRepository.getInstance();
    }

    public LiveData<GenericResponse<GenerarDietaDTO>> save(GenerarDietaDTO d) { return this.repository.save(d); }

    /*public LiveData<List<DietaConPlatosDTO>> obtenerDietasPorPaciente(String dniPaciente) {
        return this.repository.obtenerDietaPorPaciente(dniPaciente);
    }*/

    public LiveData<DietaConPlatosDTO> obtenerDietaPorPacienteYDia(String dniPaciente, String diaSemana){
        return repository.getDietaPorPacienteYDia(dniPaciente, diaSemana);
    }
}
