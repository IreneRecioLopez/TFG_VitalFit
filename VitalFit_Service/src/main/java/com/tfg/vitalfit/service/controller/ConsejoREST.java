package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Consejo;
import com.tfg.vitalfit.service.entity.Usuario;
import com.tfg.vitalfit.service.service.ConsejoService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/leido/{idConsejo}")
    public GenericResponse marcarLeido(@PathVariable("idConsejo") Long id){
        return this.service.marcarComoLeido(id);
    }
}
