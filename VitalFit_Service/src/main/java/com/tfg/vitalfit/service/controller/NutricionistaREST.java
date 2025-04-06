package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Medico;
import com.tfg.vitalfit.service.entity.Nutricionista;
import com.tfg.vitalfit.service.service.NutricionistaService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public GenericResponse save(@RequestBody Nutricionista n){
        return this.service.guardarNutricionista(n);
    }

    @PutMapping("/{dni}/hospital")
    public GenericResponse asociarNutricionistaHospital(@PathVariable String dni, @RequestBody Hospital hospital){
        return this.service.asociarNutricionistaHospital(dni, hospital);
    }

    @PutMapping("/{dni}/password")
    public GenericResponse actualizarPassword(@PathVariable String dni, @RequestBody String password){
        return this.service.actualizarPassword(dni, password);
    }
}
