package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Consejo;
import com.tfg.vitalfit.service.service.ConsejoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/consejo")
public class ConsejoREST {
    private final ConsejoService service;

    public ConsejoREST(ConsejoService service) { this.service = service; }

    @GetMapping("/paciente/{dni}")
    public List<Consejo> getConsejosByPaciente(@PathVariable("dni") String dni){
        return service.getConsejosByPaciente(dni);
    }
}
