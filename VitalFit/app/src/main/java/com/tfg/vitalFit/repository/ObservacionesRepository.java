package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.ObservacionesApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Observacion;

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

    public LiveData<GenericResponse<Observacion>> save(Observacion o){
        final MutableLiveData<GenericResponse<Observacion>> mld = new MutableLiveData<>();
        this.api.guardarObservacion(o).enqueue(new Callback<GenericResponse<Observacion>>() {
            @Override
            public void onResponse(Call<GenericResponse<Observacion>> call, Response<GenericResponse<Observacion>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Observacion>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al guardar la observación" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Void>> eliminarOperacion(Long idAlergia){
        final MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        this.api.eliminarObservacion(idAlergia).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Void>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al eliminar la observación" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
