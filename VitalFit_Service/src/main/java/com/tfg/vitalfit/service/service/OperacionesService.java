package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Operacion;
import com.tfg.vitalfit.service.repository.OperacionesRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class OperacionesService {
    private final OperacionesRepository repository;

    public OperacionesService(OperacionesRepository repository) {
        this.repository = repository;
    }

    //método para guardar operacion
    public GenericResponse guardarOperacion(Operacion op){
        if(op.getPaciente() == null){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: no se ha asignado un paciente.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Operacion registrado correctamente", this.repository.save(op));
        }
    }

    public GenericResponse eliminarOperacion(Long idOperacion){
        Optional<Operacion> optO = this.repository.findById(idOperacion);
        if(optO.isPresent()){
            this.repository.deleteById(idOperacion);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Operación eliminada correctamente", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "No se ha encontrado la operación a eliminar", null);
        }
    }
}
