package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.ConsejosApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Consejo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsejosRepository {
    private static ConsejosRepository repository;
    private final ConsejosApi api;

    public ConsejosRepository(){ this.api = ConfigApi.getConsejosApi(); }

    public static ConsejosRepository getInstance(){
        if(repository == null){
            repository = new ConsejosRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Consejo>> save(Consejo c){
        final MutableLiveData<GenericResponse<Consejo>> mld = new MutableLiveData<>();
        this.api.guardarConsejo(c).enqueue(new Callback<GenericResponse<Consejo>>() {
            @Override
            public void onResponse(Call<GenericResponse<Consejo>> call, Response<GenericResponse<Consejo>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Consejo>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al guardar el consejo" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<List<Consejo>> consejosPorPaciente(String dni){
        final MutableLiveData<List<Consejo>> mld = new MutableLiveData<>();
        this.api.obtenerConsejosPorPaciente(dni).enqueue(new Callback<List<Consejo>>() {
            @Override
            public void onResponse(Call<List<Consejo>> call, Response<List<Consejo>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Consejo>> call, Throwable t) {
                mld.setValue(new ArrayList<>());
                System.out.println("Se ha producido un error al obtener los consejos de un paciente: " + t.getMessage());
                t.printStackTrace();
            }

        });
        return mld;
    }

    public LiveData<List<Consejo>> consejosPorNutricionista(String dni){
        final MutableLiveData<List<Consejo>> mld = new MutableLiveData<>();
        this.api.obtenerConsejosPorNutricionista(dni).enqueue(new Callback<List<Consejo>>() {
            @Override
            public void onResponse(Call<List<Consejo>> call, Response<List<Consejo>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Consejo>> call, Throwable t) {
                mld.setValue(new ArrayList<>());
                System.out.println("Se ha producido un error al obtener los consejos de un paciente: " + t.getMessage());
                t.printStackTrace();
            }

        });
        return mld;
    }

    public LiveData<GenericResponse<Void>> marcarComoLeido(Consejo c) {
        MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        api.marcarComoLeido(c.getIdConsejo()).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                if(response.isSuccessful()){
                    mld.setValue(new GenericResponse("Result", 1, "Actualizado el consejo", null));
                }

            }
            @Override
            public void onFailure(Call<GenericResponse<Void>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al actualizar el consejo" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
