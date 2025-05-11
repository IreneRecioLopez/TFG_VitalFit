package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Alergias;
import com.tfg.vitalfit.service.entity.Dieta;
import com.tfg.vitalfit.service.entity.Platos;
import com.tfg.vitalfit.service.entity.dto.DietaConPlatosDTO;
import com.tfg.vitalfit.service.entity.dto.GenerarDietaDTO;
import com.tfg.vitalfit.service.repository.AlergiasRepository;
import com.tfg.vitalfit.service.repository.DietasRepository;
import com.tfg.vitalfit.service.repository.PlatosRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    //método para guardar dieta
    /*public GenericResponse guardarDieta(Dieta d){
        if(d.getPaciente() == null){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: no se ha asignado un paciente.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Dieta registrada correctamente", this.repository.save(d));
        }
    }*/

    public GenericResponse guardarDieta(GenerarDietaDTO dto){
        dto.getDieta().setDiaSemana(dto.getDieta().getDiaSemana());
        dto.getDieta().setPaciente(dto.getPaciente());
        this.repository.save(dto.getDieta());
        for(Platos p : dto.getPlatos()){
            p.setDieta(dto.getDieta());
        }
        this.platosService.guardarPlatos(dto.getPlatos());
        return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, dto);
    }

    //método para obtener las dietas
   /* public GenericResponse<List<DietaConPlatosDTO>> devolverMisDietas(String dniPaciente){
        final List<DietaConPlatosDTO> dtos = new ArrayList<>();
        final Iterable<Dieta> dietas = repository.obtenerMiDieta(dniPaciente);
        dietas.forEach(d -> {
            dtos.add(new DietaConPlatosDTO(d, platosRepository.findByDieta(d.getIdDieta())));
        });
        return new GenericResponse(OPERACION_CORRECTA, RPTA_OK, "Petición encontrada", dtos);
    }*/

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
        Iterable<Platos> nuevosPlatos = dto.getPlatos();

        // Verificar que la dieta existe
        Optional<Dieta> dietaExistenteOpt = repository.findById(dieta.getIdDieta());
        if (!dietaExistenteOpt.isPresent()) {
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado la dieta con ese id", null);

        }else{
            Dieta dietaExistente = dietaExistenteOpt.get();

            // Guardar nuevos platos
            for (Platos p : nuevosPlatos) {
                p.setDieta(dietaExistente);
                platosRepository.save(p);
            }
            return new GenericResponse(TIPO_DATA, RPTA_OK, OPERACION_CORRECTA, dto);
        }

    }
}
