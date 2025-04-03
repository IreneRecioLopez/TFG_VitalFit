package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Pesos;
import com.tfg.vitalfit.service.repository.PesosRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.tfg.vitalfit.service.utils.Global.*;
@Service
@Transactional
public class PesosService {
    private final PesosRepository repository;

    public PesosService(PesosRepository repository) {
        this.repository = repository;
    }

    //método para guardar peso
    public GenericResponse guardarPeso(Pesos p){
        if(p.getPaciente() == null){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: no se ha asignado un paciente.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Peso registrado correctamente", this.repository.save(p));
        }
    }

}
