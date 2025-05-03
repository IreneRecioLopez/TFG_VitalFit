package com.tfg.vitalfit.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.UsuarioApi;
import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Usuario;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {
    private static UsuarioRepository repository;
    private final UsuarioApi api;

    public UsuarioRepository() {
        this.api = ConfigApi.getUsuarioApi();
    }

    public static UsuarioRepository getInstance(){
        if(repository == null){
            repository = new UsuarioRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<Usuario>> login(String dni, String password){
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        this.api.login(dni, password).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse());
                System.out.println("Se ha producido un error al iniciar sesión: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Usuario>> save(Usuario u){
        final MutableLiveData<GenericResponse<Usuario>> mld = new MutableLiveData<>();
        this.api.guardarUsuario(u).enqueue(new Callback<GenericResponse<Usuario>>() {
            @Override
            public void onResponse(Call<GenericResponse<Usuario>> call, Response<GenericResponse<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<Usuario>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al guardar los datos " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<Usuario> usuarioByDni(String dni) {
        final MutableLiveData<Usuario> mld = new MutableLiveData<>();
        this.api.getUsuarioByDni(dni).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                mld.setValue(new Usuario());
                System.out.println("Se ha producido un error al obtener el usuario de dni dado: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Void>> asociarUsuarioHospital(String dniUsuario, Hospital hospital){
        MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        api.asociarUsuarioHospital(dniUsuario, hospital).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                if(response.isSuccessful()){
                    mld.setValue(new GenericResponse("Result", 1, "Usuario asociado al hospital correctamente", null));
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

    public LiveData<GenericResponse<Void>> asociarPacienteMedico(String dniPaciente, Usuario medico){
        MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        api.asociarPacienteMedico(dniPaciente, medico).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                if(response.isSuccessful()){
                    mld.setValue(new GenericResponse("Result", 1, "Paciente asociado al medico correctamente", null));
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

    public LiveData<GenericResponse<Void>> asociarPacienteNutricionista(String dniPaciente, Usuario nutricionista){
        MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        api.asociarPacienteNutricionista(dniPaciente, nutricionista).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                if(response.isSuccessful()){
                    mld.setValue(new GenericResponse("Result", 1, "Paciente asociado al nutricionista correctamente", null));
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

    public LiveData<GenericResponse<Void>> actualizarPassword(String dni, String nuevaPassword) {
        MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        RequestBody password = RequestBody.create(nuevaPassword, MediaType.parse("text/plain"));
        api.actualizarPassword(dni, password).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                if(response.isSuccessful()){
                    mld.setValue(new GenericResponse("Result", 1, "Actualizada la contraseña del usuario", null));
                }

            }
            @Override
            public void onFailure(Call<GenericResponse<Void>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al actualizar la contraseña" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<Void>> actualizarUsuario(Usuario u) {
        MutableLiveData<GenericResponse<Void>> mld = new MutableLiveData<>();
        api.actualizarUsuario(u).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                if(response.isSuccessful()){
                    mld.setValue(new GenericResponse("Result", 1, "Actualizado el usuario", null));
                }

            }
            @Override
            public void onFailure(Call<GenericResponse<Void>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                System.out.println("Se ha producido un error al actualizar el usuario" + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<List<Usuario>> medicosByHospital(Long idHospital) {
        final MutableLiveData<List<Usuario>> mld = new MutableLiveData<>();
        this.api.getMedicosByHospital(idHospital).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                mld.setValue(new ArrayList<>());
                System.out.println("Se ha producido un error al obtener los medicos de un hospital: " + t.getMessage());
                t.printStackTrace();
            }

        });
        return mld;
    }

    public LiveData<Usuario> medicoByNombreCompletoByHospital(String nombreCompleto, Long idHospital){
        final MutableLiveData<Usuario> mld = new MutableLiveData<>();
        this.api.getMedicoByNombreCompletoByHospital(nombreCompleto, idHospital).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                mld.setValue(new Usuario());
                System.out.println("Se ha producido un error al obtener el medico de un hospital a partir del nombre completo del médico: " + t.getMessage());
                t.printStackTrace();
            }

        });
        return mld;
    }

    public LiveData<List<Usuario>> pacientesByNutricionista(String dni) {
        final MutableLiveData<List<Usuario>> mld = new MutableLiveData<>();
        this.api.getPacientesByNutricionista(dni).enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                mld.setValue(new ArrayList<>());
                System.out.println("Se ha producido un error al obtener los pacientes de un nutricionista: " + t.getMessage());
                t.printStackTrace();
            }

        });
        return mld;
    }

    public LiveData<Usuario> pacienteByNombreCompletoByNutricionista(String nombreCompleto, String dni){
        final MutableLiveData<Usuario> mld = new MutableLiveData<>();
        this.api.getPacienteByNombreCompletoByNutricionista(nombreCompleto, dni).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                mld.setValue(new Usuario());
                System.out.println("Se ha producido un error al obtener el medico de un hospital a partir del nombre completo del médico: " + t.getMessage());
                t.printStackTrace();
            }

        });
        return mld;
    }
}
