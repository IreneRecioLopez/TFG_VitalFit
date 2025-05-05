package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Alergias;
import com.tfg.vitalfit.service.entity.Observaciones;
import com.tfg.vitalfit.service.repository.AlergiasRepository;
import com.tfg.vitalfit.service.repository.ObservacionesRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class ObservacionesService {
    private final ObservacionesRepository repository;

    public ObservacionesService(ObservacionesRepository repository) {
        this.repository = repository;
    }

    //método para guardar observación
    public GenericResponse guardarObservacion(Observaciones o){
        if(o.getPaciente() == null){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: no se ha asignado un paciente.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Observación registrada correctamente", this.repository.save(o));
        }
    }
}
