package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Alergias;
import com.tfg.vitalfit.service.service.AlergiasService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/alergias")
public class AlergiasREST {
    private final AlergiasService service;
    public AlergiasREST(AlergiasService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public GenericResponse save(@RequestBody Alergias a){
        return this.service.guardarAlergia(a);
    }
}
