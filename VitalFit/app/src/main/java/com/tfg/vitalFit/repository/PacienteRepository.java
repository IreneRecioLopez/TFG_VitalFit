package com.tfg.vitalfit.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.PacienteApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;
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

    public LiveData<GenericResponse<Void>> asociarPacienteHospital(String dni, Hospital hospitalAsignado) {
        MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        api.asociarPacienteHospital(dni, hospitalAsignado).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                if(response.isSuccessful()){
                    Log.e("Repositorio", "Respuesta API: " + response.body().getRpta());
                    mld.setValue(new GenericResponse("Result", 1, "Nutricionista asociado al hospital correctamente", null));
                }

            }
            @Override
            public void onFailure(Call<GenericResponse<Void>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                Log.e("Repositorio", "Error en la API: " + t.getMessage());
                System.out.println("Se ha producido un error al asociar" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
