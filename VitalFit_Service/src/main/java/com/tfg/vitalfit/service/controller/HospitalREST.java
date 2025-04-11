package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.service.HospitalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/hospital")
public class HospitalREST {
    private final HospitalService service;

    public HospitalREST(HospitalService service){
        this.service = service;
    }

    // 🔹 Obtener hospitales por provincia
    @GetMapping("/provincia/{provincia}")
    public List<Hospital> getHospitalsByProvincia(@PathVariable("provincia") String provincia) {
        return service.getHospitalsByProvincia(provincia);
    }

    @GetMapping("/{nombre}/{provincia}")
    public Hospital getHospitalByNameAndProvincia(@PathVariable("nombre") String name, @PathVariable("provincia") String provincia){
        return service.getHospitalsByNameAndProvincia(name, provincia);
    }



}
