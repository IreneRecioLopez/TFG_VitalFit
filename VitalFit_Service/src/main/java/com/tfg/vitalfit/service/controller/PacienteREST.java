package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.entity.Usuario;
import com.tfg.vitalfit.service.service.PacienteService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/paciente")
public class PacienteREST {
    private final PacienteService service;

    public PacienteREST(PacienteService service) {
        this.service = service;
    }

    /*@PostMapping("/login")
    public GenericResponse<Paciente> login(HttpServletRequest request){
        String dni = request.getParameter("dni");
        String password = request.getParameter("password");
        return this.service.login(dni, password);
    }*/

    @PostMapping("/save")
    public GenericResponse save(@RequestBody Paciente p){
        return this.service.guardarPaciente(p);
    }

    @PutMapping("/update")
    public GenericResponse actualizarPaciente(@RequestBody Paciente p){
        return this.service.actualizarPaciente(p);
    }

}
