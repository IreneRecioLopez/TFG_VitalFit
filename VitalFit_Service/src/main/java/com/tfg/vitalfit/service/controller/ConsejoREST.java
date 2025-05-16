package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Consejo;
import com.tfg.vitalfit.service.service.ConsejoService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/consejo")
public class ConsejoREST {
    private final ConsejoService service;

    public ConsejoREST(ConsejoService service) { this.service = service; }

    @PostMapping("/save")
    public GenericResponse save(@RequestBody Consejo c) { return this.service.guardarConsejo(c); }

    @GetMapping("/paciente/{dni}")
    public List<Consejo> getConsejosByPaciente(@PathVariable("dni") String dni){
        return service.getConsejosByPaciente(dni);
    }

    @GetMapping("/nutricionista/{dni}/paciente")
    public List<Consejo> getConsejosByNutricionista(@PathVariable("dni") String dni, @RequestParam String dniPaciente){
        return service.getConsejosByNutricionistaAndPaciente(dni, dniPaciente);
    }

    @GetMapping("/noLeidos")
    public List<Consejo> getConsejosNoLeidos(@RequestParam String dniPaciente){
        return service.getConsejosNoLeidos(dniPaciente);
    }

    @PutMapping("/leido/{idConsejo}")
    public GenericResponse marcarLeido(@PathVariable("idConsejo") Long id){
        return this.service.marcarComoLeido(id);
    }
}
