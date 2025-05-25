package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.PesosApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.entity.service.Peso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesosRepository {
    private static PesosRepository repository;
    private final PesosApi api;

    public PesosRepository() {
        this.api = ConfigApi.getPesosApi();
    }

    public static PesosRepository getInstance(){
        if(repository == null){
            repository = new PesosRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Peso>> save(Peso p){
        final MutableLiveData<GenericResponse<Peso>> mld = new MutableLiveData<>();
        this.api.guardarPeso(p).enqueue(new Callback<GenericResponse<Peso>>() {
            @Override
            public void onResponse(Call<GenericResponse<Peso>> call, Response<GenericResponse<Peso>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Peso>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al guardar el peso" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Void>> actualizarPeso(Peso p) {
        MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        api.actualizarPeso(p).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                if(response.isSuccessful()){
                    mld.setValue(new GenericResponse("Result", 1, "Actualizado el peso", null));
                }

            }
            @Override
            public void onFailure(Call<GenericResponse<Void>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al actualizar el peso" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<Peso> getPesoHoy(String dniPaciente){
        final MutableLiveData<Peso> mld = new MutableLiveData<>();
        this.api.getPesoHoy(dniPaciente).enqueue(new Callback<Peso>() {
            @Override
            public void onResponse(Call<Peso> call, Response<Peso> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Peso> call, Throwable t) {
                mld.setValue(new Peso());
                System.out.println("Se ha producido un error al actualizar el peso" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }


}
