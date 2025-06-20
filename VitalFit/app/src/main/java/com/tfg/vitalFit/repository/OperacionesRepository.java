package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.OperacionesApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Operacion;

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

    public LiveData<GenericResponse<Operacion>> save(Operacion op){
        final MutableLiveData<GenericResponse<Operacion>> mld = new MutableLiveData<>();
        this.api.guardarOperacion(op).enqueue(new Callback<GenericResponse<Operacion>>() {
            @Override
            public void onResponse(Call<GenericResponse<Operacion>> call, Response<GenericResponse<Operacion>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Operacion>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al guardar la operación" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Void>> eliminarOperacion(Long idOperacion){
        final MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        this.api.eliminarOperacion(idOperacion).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Void>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al eliminar la operación" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

}
