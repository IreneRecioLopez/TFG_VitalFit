package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.entity.Peso;
import com.tfg.vitalfit.service.service.PesosService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pesos")
public class PesosREST {
    private final PesosService service;
    public PesosREST(PesosService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public GenericResponse save(@RequestBody Peso p){
        return this.service.guardarPeso(p);
    }

    @PutMapping("/update")
    public GenericResponse actualizarPeso(@RequestBody Peso p){
        return this.service.actualizarPeso(p);
    }

    @GetMapping("/ultimo")
    Peso getUltimoPeso(@RequestParam String dni){ return this.service.getUltimoPeso(dni); }
}
