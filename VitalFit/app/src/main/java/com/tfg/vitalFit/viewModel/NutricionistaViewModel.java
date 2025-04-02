package com.tfg.vitalfit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Nutricionista;
import com.tfg.vitalfit.repository.NutricionistaRepository;

public class NutricionistaViewModel extends AndroidViewModel {
    private final NutricionistaRepository repository;

    public NutricionistaViewModel(@NonNull Application application) {
        super(application);
        this.repository = NutricionistaRepository.getInstance();
    }

    public LiveData<GenericResponse<Nutricionista>> login(String dni, String password){
        return this.repository.login(dni, password);
    }

    public LiveData<GenericResponse<Nutricionista>> save(Nutricionista n){
        return this.repository.save(n);
    }

    public LiveData<GenericResponse<Void>> asociarNutricionistaHospital(String dniNutricionista, Hospital hospital){
        return this.repository.asociarNutricionistaHospital(dniNutricionista, hospital);
    }
}
