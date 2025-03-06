package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Nutricionista;
import com.tfg.vitalfit.service.repository.NutricionistaRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class NutricionistaService {
    private final NutricionistaRepository repository;

    public NutricionistaService(NutricionistaRepository repository) {
        this.repository = repository;
    }

    //método para iniciar sesión
    public GenericResponse<Nutricionista> login(String dni, String password){
        Optional<Nutricionista> optN = this.repository.login(dni, password);
        if(optN.isPresent()){
            return new GenericResponse<Nutricionista>(TIPO_AUTH, RPTA_OK, "Has iniciado sesión correctamente", optN.get());
        }else{
            return new GenericResponse<Nutricionista>(TIPO_AUTH, RPTA_WARNING, "Lo sentimos, ese usuario no existe", new Nutricionista());
        }
    }
}
