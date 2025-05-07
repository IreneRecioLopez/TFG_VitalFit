package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Alergias;
import com.tfg.vitalfit.service.repository.AlergiasRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

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

    public GenericResponse eliminarAlergia(Long idAlergia) {
        Optional<Alergias> optA = this.repository.findById(idAlergia);
        if(optA.isPresent()){
            this.repository.deleteById(idAlergia);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Alergia eliminada correctamente", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "No se ha encontrado la alergia a eliminar", null);
        }

    }
}
