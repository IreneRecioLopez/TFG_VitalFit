package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Medico;
import com.tfg.vitalfit.service.repository.MedicoRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class MedicoService {
    private final MedicoRepository repository;

    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    //método para iniciar sesión
    public GenericResponse<Medico> login(String dni, String password){
        Optional<Medico> optM = this.repository.login(dni, password);
        if(optM.isPresent()){
            return new GenericResponse<Medico>(TIPO_AUTH, RPTA_OK, "Has iniciado sesión correctamente", optM.get());
        }else{
            return new GenericResponse<Medico>(TIPO_AUTH, RPTA_WARNING, "Lo sentimos, ese usuario no existe", new Medico());

        }
    }
}
