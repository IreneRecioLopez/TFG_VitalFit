package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.AlergiasApi;
import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.OperacionesApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Alergias;
import com.tfg.vitalfit.entity.service.Operaciones;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperacionesRepository {
    private static OperacionesRepository repository;
    private final OperacionesApi api;

    public OperacionesRepository() {
        this.api = ConfigApi.getOperacionesApi();
    }

    public static OperacionesRepository getInstance(){
        if(repository == null){
            repository = new OperacionesRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Operaciones>> save(Operaciones op){
        final MutableLiveData<GenericResponse<Operaciones>> mld = new MutableLiveData<>();
        this.api.guardarOperacion(op).enqueue(new Callback<GenericResponse<Operaciones>>() {
            @Override
            public void onResponse(Call<GenericResponse<Operaciones>> call, Response<GenericResponse<Operaciones>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Operaciones>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al guardar la operación" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
