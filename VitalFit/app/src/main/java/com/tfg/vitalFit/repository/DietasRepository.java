package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.DietasApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Dieta;
import com.tfg.vitalfit.entity.service.dto.DietaConPlatosDTO;
import com.tfg.vitalfit.entity.service.dto.GenerarDietaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DietasRepository {
    private static DietasRepository repository;
    private final DietasApi api;

    public DietasRepository(){ this.api = ConfigApi.getDietasApi(); }

    public static DietasRepository getInstance(){
        if(repository == null){
            repository = new DietasRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<GenerarDietaDTO>> save(GenerarDietaDTO d){
        final MutableLiveData<GenericResponse<GenerarDietaDTO>> mld = new MutableLiveData<>();
        this.api.guardarDieta(d).enqueue(new Callback<GenericResponse<GenerarDietaDTO>>() {
            @Override
            public void onResponse(Call<GenericResponse<GenerarDietaDTO>> call, Response<GenericResponse<GenerarDietaDTO>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<GenerarDietaDTO>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al guardar la dieta" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    /*public LiveData<GenericResponse<List<DietaConPlatosDTO>>> obtenerDietaPorPaciente(String dniPaciente){
        final MutableLiveData<GenericResponse<List<DietaConPlatosDTO>>> mld = new MutableLiveData<>();
        this.api.obtenerDietasPorPaciente(dniPaciente).enqueue(new Callback<GenericResponse<List<DietaConPlatosDTO>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<DietaConPlatosDTO>>> call, Response<GenericResponse<List<DietaConPlatosDTO>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<DietaConPlatosDTO>>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al obtener las dietas" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }*/

    public LiveData<DietaConPlatosDTO> getDietaPorPacienteYDia(String dniPaciente, String diaSemana) {
        final MutableLiveData<DietaConPlatosDTO> mld = new MutableLiveData<>();
        this.api.getDietaPorPacienteYDia(dniPaciente, diaSemana).enqueue(new Callback<DietaConPlatosDTO>() {
            @Override
            public void onResponse(Call<DietaConPlatosDTO> call, Response<DietaConPlatosDTO> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<DietaConPlatosDTO> call, Throwable t) {
                mld.setValue(new DietaConPlatosDTO());
                System.out.println("Se ha producido un error al obtener la dieta" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
