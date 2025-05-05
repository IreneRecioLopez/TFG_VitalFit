package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Pesos;
import com.tfg.vitalfit.service.service.PesosService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pesos")
public class PesosREST {
    private final PesosService service;
    public PesosREST(PesosService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public GenericResponse save(@RequestBody Pesos p){
        return this.service.guardarPeso(p);
    }
}
