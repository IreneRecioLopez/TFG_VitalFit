package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Dieta;

import com.tfg.vitalfit.service.entity.dto.DietaConPlatosDTO;
import com.tfg.vitalfit.service.entity.dto.GenerarDietaDTO;
import com.tfg.vitalfit.service.service.DietasService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/dietas")
public class DietasREST {
    private final DietasService service;
    public DietasREST(DietasService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public GenericResponse save(@RequestBody GenerarDietaDTO dto){
        return this.service.guardarDieta(dto);
    }


    /*@GetMapping("/misDietas/{dni}")
    public GenericResponse<List<DietaConPlatosDTO>> obtenerMisDietasConPlatos(@PathVariable String dni){
        return this.service.devolverMisDietas(dni);
    }*/

    @GetMapping("/{dni}/{diaSemana}")
    public DietaConPlatosDTO obtenerDietaPorPacienteYDiaSemana(@PathVariable String dni, @PathVariable String diaSemana){
        return service.obtenerDietaPorPacienteYDia(dni, diaSemana);
    }

    @PutMapping("/update")
    public GenericResponse updateDieta(@RequestBody GenerarDietaDTO dto){ return this.service.updateDieta(dto); }
}
