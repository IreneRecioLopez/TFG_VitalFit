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

import java.util.List;

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

    public LiveData<GenericResponse<Void>> asociarPacienteMedico(String dniPaciente, Usuario medico){
        return this.repository.asociarPacienteMedico(dniPaciente, medico);
    }

    public LiveData<GenericResponse<Void>> asociarPacienteNutricionista(String dniPaciente, Usuario nutricionista){
        return this.repository.asociarPacienteNutricionista(dniPaciente, nutricionista);
    }

    public LiveData<GenericResponse<Void>> actualizarPassword(String dni, String password) {
        return this.repository.actualizarPassword(dni, password);
    }

    public LiveData<GenericResponse<Void>> actualizarUsuario(Usuario u){
        return this.repository.actualizarUsuario(u);
    }

    public LiveData<Usuario> getUsuarioByDni(String dni){
        return this.repository.usuarioByDni(dni);
    }

    public LiveData<List<Usuario>> getMedicosByHospital(Long idHospital){
        return this.repository.medicosByHospital(idHospital);
    }

    public LiveData<List<Usuario>> getNutricionistasByHospital(Long idHospital){
        return this.repository.nutricionistasByHospital(idHospital);
    }

}
