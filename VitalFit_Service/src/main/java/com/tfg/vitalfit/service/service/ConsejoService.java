package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Consejo;
import com.tfg.vitalfit.service.repository.ConsejoRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class ConsejoService {

    private final ConsejoRepository repository;

    public ConsejoService(ConsejoRepository repository) { this.repository = repository; }

    public GenericResponse guardarConsejo(Consejo c) {
        if(c.getPaciente() == null || c.getNutricionista() == null){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: no se ha asignado un paciente o nutricionista.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Consejo registrado correctamente", this.repository.save(c));
        }
    }

    public List<Consejo> getConsejosByPaciente(String dni) {
        return repository.findByPaciente(dni);
    }

    public List<Consejo> getConsejosByNutricionistaAndPaciente(String dni, String dniPaciente){
        return repository.findByNutricionistaAndPaciente(dni, dniPaciente);
    }

    public GenericResponse marcarComoLeido(Long id) {
        Optional<Consejo> optC = this.repository.findById(id);
        if(optC.isPresent()){
            this.repository.marcarComoLeido(optC.get().getIdConsejo(), 1);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Consejo actualizado correctamente", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el consejo con ese id", null);
        }
    }


}
