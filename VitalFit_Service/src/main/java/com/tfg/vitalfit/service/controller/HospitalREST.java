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

    @GetMapping("/{idHospital}")
    public Hospital getHospitalById(@PathVariable("idHospital") Long idHospital){
        return service.getHospitalById(idHospital);
    }

    @GetMapping("/provincia/{provincia}")
    public List<Hospital> getHospitalsByProvincia(@PathVariable("provincia") String provincia) {
        return service.getHospitalsByProvincia(provincia);
    }



}
