package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.service.PacienteService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/paciente")
public class PacienteREST {
    private final PacienteService service;

    public PacienteREST(PacienteService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public GenericResponse<Paciente> login(HttpServletRequest request){
        String dni = request.getParameter("dni");
        String password = request.getParameter("password");
        return this.service.login(dni, password);
    }

    @PostMapping("/save")
    public GenericResponse save(@RequestBody Paciente p){
        return this.service.guardarPaciente(p);
    }

    @PutMapping("/{dni}")
    public GenericResponse update(@PathVariable String dni, @RequestBody Paciente p){
        return this.service.guardarPaciente(p);
    }

    @PutMapping("/{dni}/hospital")
    public GenericResponse asociarPacienteHospital(@PathVariable String dni, @RequestBody Hospital hospital){
        return this.service.asociarPacienteHospital(dni, hospital);
    }

    @PutMapping("/{dni}/password")
    public GenericResponse actualizarPassword(@PathVariable String dni, @RequestBody String password){
        return this.service.actualizarPassword(dni, password);
    }
}
