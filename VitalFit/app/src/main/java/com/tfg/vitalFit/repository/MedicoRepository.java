package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.MedicoApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Medico;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicoRepository {
    private static MedicoRepository repository;
    private final MedicoApi api;

    public MedicoRepository() {
        this.api = ConfigApi.getMedicoApi();
    }

    public static MedicoRepository getInstance(){
        if(repository == null){
            repository = new MedicoRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Medico>> login(String dni, String password){
        final MutableLiveData<GenericResponse<Medico>> mld = new MutableLiveData<>();
        this.api.login(dni, password).enqueue(new Callback<GenericResponse<Medico>>() {
            @Override
            public void onResponse(Call<GenericResponse<Medico>> call, Response<GenericResponse<Medico>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Medico>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se ha producido un error al iniciar sesión: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Medico>> save(Medico m){
        final MutableLiveData<GenericResponse<Medico>> mld = new MutableLiveData<>();
        this.api.guardarMedico(m).enqueue(new Callback<GenericResponse<Medico>>() {
            @Override
            public void onResponse(Call<GenericResponse<Medico>> call, Response<GenericResponse<Medico>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Medico>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
