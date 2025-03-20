package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.HospitalApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalRepository {
    private static HospitalRepository repository;
    private final HospitalApi api;

    public HospitalRepository(){
        this.api = ConfigApi.getHospitalApi();
    }

    public static HospitalRepository getInstance(){
        if(repository == null){
            repository = new HospitalRepository();
        }
        return repository;
    }

    public LiveData<List<Hospital>> hospitalsByProvince(String provincia){
        final MutableLiveData<List<Hospital>> mld = new MutableLiveData<>();
        this.api.obtenerHospitalesPorProvincia(provincia).enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(Call<List<Hospital>> call, Response<List<Hospital>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Hospital>> call, Throwable t) {
                mld.setValue(new ArrayList<>());
                System.out.println("Se ha producido un error al obtener los hospital de una provincia: " + t.getMessage());
                t.printStackTrace();
            }

        });
        return mld;
    }

    public LiveData<Hospital> hospitalByNameAndProvince(String nombre, String provincia) {
        final MutableLiveData<Hospital> mld = new MutableLiveData<>();
        this.api.obtenerHospitalPorNombreYProvincia(nombre, provincia).enqueue(new Callback<Hospital>() {
            @Override
            public void onResponse(Call<Hospital> call, Response<Hospital> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Hospital> call, Throwable t) {
                mld.setValue(new Hospital());
                System.out.println("Se ha producido un error al obtener los hospital de una provincia y nombre dado: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
