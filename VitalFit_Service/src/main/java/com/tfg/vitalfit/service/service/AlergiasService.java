package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Alergias;
import com.tfg.vitalfit.service.entity.Pesos;
import com.tfg.vitalfit.service.repository.AlergiasRepository;
import com.tfg.vitalfit.service.repository.PesosRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class AlergiasService {
    private final AlergiasRepository repository;

    public AlergiasService(AlergiasRepository repository) {
        this.repository = repository;
    }

    //método para guardar alergia
    public GenericResponse guardarAlergia(Alergias a){
        if(a.getPaciente() == null){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: no se ha asignado un paciente.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Alergia registrada correctamente", this.repository.save(a));
        }
    }
}
