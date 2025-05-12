package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.PacienteApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Paciente;

import java.util.ArrayList;

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


    public LiveData<GenericResponse<Paciente>> save(Paciente p){
        final MutableLiveData<GenericResponse<Paciente>> mld = new MutableLiveData<>();
        this.api.guardarPaciente(p).enqueue(new Callback<GenericResponse<Paciente>>() {
            @Override
            public void onResponse(Call<GenericResponse<Paciente>> call, Response<GenericResponse<Paciente>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Paciente>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al guardar los datos" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Void>> actualizarPaciente(Paciente p) {
        MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        api.actualizarPaciente(p).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                if(response.isSuccessful()){
                    mld.setValue(new GenericResponse("Result", 1, "Actualizado el paciente", null));
                }

            }
            @Override
            public void onFailure(Call<GenericResponse<Void>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al actualizar el paciente" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<Paciente> pacienteByDNI(String dni) {
        MutableLiveData<Paciente> mld = new MutableLiveData<>();
        api.pacienteByDNI(dni).enqueue(new Callback<Paciente>() {
            @Override
            public void onResponse(Call<Paciente> call, Response<Paciente> response) {
                if(response.isSuccessful()){
                    mld.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Paciente> call, Throwable t) {
                mld.setValue(new Paciente());
                System.out.println("Se ha producido un error al obtener el paciente: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
