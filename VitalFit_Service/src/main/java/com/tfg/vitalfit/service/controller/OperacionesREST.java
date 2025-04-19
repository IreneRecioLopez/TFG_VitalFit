package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Operaciones;
import com.tfg.vitalfit.service.service.OperacionesService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/operaciones")
public class OperacionesREST {
    private final OperacionesService service;
    public OperacionesREST(OperacionesService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public GenericResponse save(@RequestBody Operaciones op){
        return this.service.guardarOperacion(op);
    }
}
