package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Observacion;
import com.tfg.vitalfit.service.repository.ObservacionesRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class ObservacionesService {
    private final ObservacionesRepository repository;

    public ObservacionesService(ObservacionesRepository repository) {
        this.repository = repository;
    }

    //método para guardar observación
    public GenericResponse guardarObservacion(Observacion o){
        if(o.getPaciente() == null){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: no se ha asignado un paciente.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Observación registrada correctamente", this.repository.save(o));
        }
    }

    public GenericResponse eliminarObservacion(Long idObservacion) {
        Optional<Observacion> optO = this.repository.findById(idObservacion);
        if(optO.isPresent()){
            this.repository.deleteById(idObservacion);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Observación eliminada correctamente", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "No se ha encontrado la observación a eliminar", null);
        }
    }
}
