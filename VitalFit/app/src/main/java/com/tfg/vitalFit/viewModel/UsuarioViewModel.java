package com.tfg.vitalfit.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.repository.UsuarioRepository;

import org.jetbrains.annotations.NotNull;

public class UsuarioViewModel extends AndroidViewModel {
    private final UsuarioRepository repository;

    public UsuarioViewModel(@NonNull @NotNull Application application) {
        super(application);
        this.repository = UsuarioRepository.getInstance();
    }

    public LiveData<GenericResponse<Usuario>> login(String dni, String password){
        return this.repository.login(dni, password);
    }

    public LiveData<GenericResponse<Usuario>> save(Usuario u){
        return this.repository.save(u);
    }

    public LiveData<GenericResponse<Void>> asociarUsuarioHospital(String dniUsuario, Hospital hospital){
        return this.repository.asociarUsuarioHospital(dniUsuario, hospital);
    }

    public LiveData<GenericResponse<Void>> actualizarPassword(String dni, String password) {
        return this.repository.actualizarPassword(dni, password);
    }

    public LiveData<Usuario> getUsuarioByDni(String dni){
        return this.repository.UsuarioByDni(dni);
    }
}
