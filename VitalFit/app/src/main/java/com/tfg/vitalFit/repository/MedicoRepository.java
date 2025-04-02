package com.tfg.vitalfit.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.MedicoApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;
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
                System.out.println("Se ha producido un error al guardar los datos " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Void>> asociarMedicoHospital(String dniMedico, Hospital hospital){
        MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        api.asociarMedicoHospital(dniMedico, hospital).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                if(response.isSuccessful()){
                    mld.setValue(new GenericResponse("Result", 1, "Medico asociado al hospital correctamente", null));
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
