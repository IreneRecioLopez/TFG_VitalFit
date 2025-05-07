package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Alergias;
import com.tfg.vitalfit.service.entity.Observaciones;
import com.tfg.vitalfit.service.service.AlergiasService;
import com.tfg.vitalfit.service.service.ObservacionesService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/observaciones")
public class ObservacionesREST {
    private final ObservacionesService service;
    public ObservacionesREST(ObservacionesService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public GenericResponse save(@RequestBody Observaciones o){
        return this.service.guardarObservacion(o);
    }
}
