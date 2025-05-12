package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Observacion;
import com.tfg.vitalfit.service.service.ObservacionesService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/observaciones")
public class ObservacionesREST {
    private final ObservacionesService service;
    public ObservacionesREST(ObservacionesService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public GenericResponse save(@RequestBody Observacion o){
        return this.service.guardarObservacion(o);
    }

    @DeleteMapping("/delete/{idObservacion}")
    public GenericResponse delete(@PathVariable("idObservacion") Long idObservacion){
        return this.service.eliminarObservacion(idObservacion);
    }
}
