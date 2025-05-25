package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.entity.Peso;
import com.tfg.vitalfit.service.repository.PesosRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;
@Service
@Transactional
public class PesosService {
    private final PesosRepository repository;

    public PesosService(PesosRepository repository) {
        this.repository = repository;
    }

    //método para guardar peso
    public GenericResponse guardarPeso(Peso p){
        if(p.getPaciente() == null){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: no se ha asignado un paciente.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Peso registrado correctamente", this.repository.save(p));
        }
    }

    public Peso getUltimoPeso(String dni) {
        List<Peso> resultados = this.repository.getUltimoPeso(dni);
        if(resultados.isEmpty()){
            return null;
        }else{
            return resultados.get(0);
        }
    }

    public GenericResponse actualizarPeso(Peso p){
        Optional<Peso> optP = this.repository.findById(p.getIdPeso());
        if(optP.isPresent()){
            this.repository.save(p);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Peso actualizado correctamente", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el peso con ese id", null);
        }
    }
}
