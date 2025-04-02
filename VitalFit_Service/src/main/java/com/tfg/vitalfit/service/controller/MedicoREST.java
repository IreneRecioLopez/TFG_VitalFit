package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Medico;
import com.tfg.vitalfit.service.service.MedicoService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/medico")
public class MedicoREST {
    private final MedicoService service;

    public MedicoREST(MedicoService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public GenericResponse<Medico> login(HttpServletRequest request){
        String dni = request.getParameter("dni");
        String password = request.getParameter("password");
        return this.service.login(dni, password);
    }

    @PostMapping("/save")
    public GenericResponse save(@RequestBody Medico m){
        return this.service.guardarMedico(m);
    }


    @PutMapping("/{dni}/hospital")
    public GenericResponse asociarMedicoHospital(@PathVariable String dni, @RequestBody Hospital hospital){
        return this.service.asociarMedicoHospital(dni, hospital);
    }


}
