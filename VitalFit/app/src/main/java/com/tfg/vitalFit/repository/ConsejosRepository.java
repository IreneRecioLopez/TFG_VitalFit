package com.tfg.vitalfit.repository;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.ConsejosApi;
import com.tfg.vitalfit.entity.service.Consejo;
import com.tfg.vitalfit.entity.service.Hospital;

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
}
