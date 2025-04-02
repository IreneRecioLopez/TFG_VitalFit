package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.NutricionistaApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Nutricionista;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NutricionistaRepository {
    private static NutricionistaRepository repository;
    private final NutricionistaApi api;

    public NutricionistaRepository() {
        this.api = ConfigApi.getNutricionistaApi();
    }

    public static NutricionistaRepository getInstance(){
        if(repository == null){
            repository = new NutricionistaRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Nutricionista>> login(String dni, String password){
        final MutableLiveData<GenericResponse<Nutricionista>> mld = new MutableLiveData<>();
        this.api.login(dni, password).enqueue(new Callback<GenericResponse<Nutricionista>>() {
            @Override
            public void onResponse(Call<GenericResponse<Nutricionista>> call, Response<GenericResponse<Nutricionista>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Nutricionista>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se ha producido un error al iniciar sesión: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Nutricionista>> save(Nutricionista n){
        final MutableLiveData<GenericResponse<Nutricionista>> mld = new MutableLiveData<>();
        this.api.guardarNutricionista(n).enqueue(new Callback<GenericResponse<Nutricionista>>() {
            @Override
            public void onResponse(Call<GenericResponse<Nutricionista>> call, Response<GenericResponse<Nutricionista>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Nutricionista>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al guardar los datos" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Void>> asociarNutricionistaHospital(String dniNutricionista, Hospital hospital){
        MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        api.asociarNutricionistaHospital(dniNutricionista, hospital).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                if(response.isSuccessful()){
                    mld.setValue(new GenericResponse("Result", 1, "Nutricionista asociado al hospital correctamente", null));
                }

            }
            @Override
            public void onFailure(Call<GenericResponse<Void>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al asociar" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
