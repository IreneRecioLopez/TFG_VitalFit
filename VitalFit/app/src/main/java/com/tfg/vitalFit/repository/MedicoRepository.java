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
/*
    public LiveData<GenericResponse<Medico>> asociarMedicoHospital(String dniMedico, Hospital hospital){
        MutableLiveData<GenericResponse<Medico>> mld = new MutableLiveData<>();

        // Realizamos la llamada a la API (el backend retorna un int que indica el número de registros afectados)
        api.asociarMedicoHospital(dniMedico, hospital).enqueue(new Callback<Integer>() { // Cambiar el tipo a Integer
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int result = response.body(); // Aquí obtenemos el número de registros afectados (int)

                    GenericResponse<Medico> genericResponse = new GenericResponse<>();

                    if (result > 0) {
                        // Si la actualización fue exitosa (al menos un registro fue afectado)
                        genericResponse.setRpta(1); // Código de éxito
                        genericResponse.setBody(null); // No necesitamos devolver un Medico (porque solo fue una actualización)
                        Log.d("Success", "Médico asociado al hospital correctamente.");
                    } else {
                        // Si no se actualizó ningún registro
                        genericResponse.setRpta(0); // Código de error
                        genericResponse.setBody(null); // De nuevo, no necesitamos un Medico
                        Log.d("Error", "No se pudo asociar el médico al hospital.");
                    }

                    mld.setValue(genericResponse); // Establecemos la respuesta final
                } else {
                    // Si la respuesta no es exitosa o está vacía
                    GenericResponse<Medico> errorResponse = new GenericResponse<>();
                    errorResponse.setRpta(0); // Error del servidor
                    errorResponse.setBody(null);
                    mld.setValue(errorResponse);
                    Log.d("Error", "Error de conexión o servidor: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error " + t.getMessage());
                t.printStackTrace();
            }
        });

        return mld;
    }
*/
}
