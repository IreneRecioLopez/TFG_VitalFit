package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.repository.PacienteRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class PacienteService {
    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    //método para iniciar sesión
    public GenericResponse<Paciente> login(String dni, String password){
        Optional<Paciente> optP = this.repository.login(dni, password);
        if(optP.isPresent()){
            return new GenericResponse<Paciente>(TIPO_AUTH, RPTA_OK, "Has iniciado sesión correctamente", optP.get());
        }else{
            return new GenericResponse<Paciente>(TIPO_AUTH, RPTA_WARNING, "Lo sentimos, ese usuario no existe", new Paciente());

        }
    }
}
