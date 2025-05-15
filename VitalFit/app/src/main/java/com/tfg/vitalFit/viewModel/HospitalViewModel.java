package com.tfg.vitalfit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.repository.HospitalRepository;

import java.util.List;

public class HospitalViewModel extends AndroidViewModel {
    private final HospitalRepository repository;

    public HospitalViewModel(@NonNull Application application){
        super(application);
        this.repository = HospitalRepository.getInstance();
    }

    public LiveData<List<Hospital>> hospitalPorProvincia(String provincia){
        return this.repository.hospitalsByProvince(provincia);
    }

    public LiveData<Hospital> hospitalPorId(Long idHospital){
        return this.repository.hospitalById(idHospital);
    }

}
