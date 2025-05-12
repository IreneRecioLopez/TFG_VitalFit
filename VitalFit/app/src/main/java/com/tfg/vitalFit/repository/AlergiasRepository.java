package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.AlergiasApi;
import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Alergia;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlergiasRepository {
    private static AlergiasRepository repository;
    private final AlergiasApi api;

    public AlergiasRepository() {
        this.api = ConfigApi.getAlergiasApi();
    }

    public static AlergiasRepository getInstance(){
        if(repository == null){
            repository = new AlergiasRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Alergia>> save(Alergia a){
        final MutableLiveData<GenericResponse<Alergia>> mld = new MutableLiveData<>();
        this.api.guardarAlergia(a).enqueue(new Callback<GenericResponse<Alergia>>() {
            @Override
            public void onResponse(Call<GenericResponse<Alergia>> call, Response<GenericResponse<Alergia>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Alergia>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al guardar la alergia" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Void>> eliminarAlergia(Long idAlergia){
        final MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        this.api.eliminarAlergia(idAlergia).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Void>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al eliminar la alergia" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
