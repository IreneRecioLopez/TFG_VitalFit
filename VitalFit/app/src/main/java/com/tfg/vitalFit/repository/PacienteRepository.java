package com.tfg.vitalfit.repository;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.PacienteApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Paciente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacienteRepository {
    private static PacienteRepository repository;
    private final PacienteApi api;

    public PacienteRepository() {
        this.api = ConfigApi.getPacienteApi();
    }

    public static PacienteRepository getInstance(){
        if(repository == null){
            repository = new PacienteRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Paciente>> login(String dni, String password){
        final MutableLiveData<GenericResponse<Paciente>> mld = new MutableLiveData<>();
        this.api.login(dni, password).enqueue(new Callback<GenericResponse<Paciente>>() {
            @Override
            public void onResponse(Call<GenericResponse<Paciente>> call, Response<GenericResponse<Paciente>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Paciente>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se ha producido un error al iniciar sesión: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

}
