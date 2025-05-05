package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.ObservacionesApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Observaciones;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObservacionesRepository {
    private static ObservacionesRepository repository;
    private final ObservacionesApi api;

    public ObservacionesRepository() {
        this.api = ConfigApi.getObservacionesApi();
    }

    public static ObservacionesRepository getInstance(){
        if(repository == null){
            repository = new ObservacionesRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Observaciones>> save(Observaciones o){
        final MutableLiveData<GenericResponse<Observaciones>> mld = new MutableLiveData<>();
        this.api.guardarObservacion(o).enqueue(new Callback<GenericResponse<Observaciones>>() {
            @Override
            public void onResponse(Call<GenericResponse<Observaciones>> call, Response<GenericResponse<Observaciones>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Observaciones>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al guardar la observación" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
