package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Nutricionista;
import com.tfg.vitalfit.service.service.NutricionistaService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/nutricionista")
public class NutricionistaREST {
    private final NutricionistaService service;

    public NutricionistaREST(NutricionistaService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public GenericResponse<Nutricionista> login(HttpServletRequest request){
        String dni = request.getParameter("dni");
        String password = request.getParameter("password");
        return this.service.login(dni, password);
    }
}
