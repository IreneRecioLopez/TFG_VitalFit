package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Dieta;
import com.tfg.vitalfit.service.entity.Plato;
import com.tfg.vitalfit.service.entity.dto.DietaConPlatosDTO;
import com.tfg.vitalfit.service.entity.dto.GenerarDietaDTO;
import com.tfg.vitalfit.service.repository.DietasRepository;
import com.tfg.vitalfit.service.repository.PlatosRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class DietasService {
    private final DietasRepository repository;
    private final PlatosRepository platosRepository;
    private final PlatosService platosService;

    public DietasService(DietasRepository repository, PlatosRepository platosRepository, PlatosService platosService) {
        this.repository = repository;
        this.platosRepository = platosRepository;
        this.platosService = platosService;
    }

    public GenericResponse guardarDieta(GenerarDietaDTO dto){
        dto.getDieta().setDiaSemana(dto.getDieta().getDiaSemana());
        dto.getDieta().setPaciente(dto.getPaciente());
        this.repository.save(dto.getDieta());
        for(Plato p : dto.getPlatos()){
            p.setDieta(dto.getDieta());
        }
        this.platosService.guardarPlatos(dto.getPlatos());
        return new GenericResponse(TIPO_DATA, RPTA_OK, "Dieta guardada correctamente", dto);
    }

    //método para obtener la dieta de un dia dado
    public DietaConPlatosDTO obtenerDietaPorPacienteYDia(String dni, String diaSemana){
        Optional<Dieta> optD = repository.findByPacienteAndDiaSemana(dni, diaSemana);
        if(optD.isPresent()){
            Dieta dieta = optD.get();
            return new DietaConPlatosDTO(dieta, dieta.getPlatos());
        }
        return null;
    }

    public GenericResponse updateDieta(GenerarDietaDTO dto) {
        Dieta dieta = dto.getDieta();
        Iterable<Plato> nuevosPlatos = dto.getPlatos();

        // Verificar que la dieta existe
        Optional<Dieta> dietaExistenteOpt = repository.findById(dieta.getIdDieta());
        if (!dietaExistenteOpt.isPresent()) {
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado la dieta con ese id", null);

        }else{
            Dieta dietaExistente = dietaExistenteOpt.get();

            // Guardar nuevos platos
            for (Plato p : nuevosPlatos) {
                p.setDieta(dietaExistente);
                platosRepository.save(p);
            }
            return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, dto);
        }

    }
}
