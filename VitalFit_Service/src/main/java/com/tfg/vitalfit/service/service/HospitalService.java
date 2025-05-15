package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HospitalService {

    private final HospitalRepository repository;

    public HospitalService(HospitalRepository repository){
        this.repository = repository;
    }

    public Hospital getHospitalById(Long idHospital) {
        return this.repository.findById(idHospital).get();
    }

    // Obtener hospitales por provincia
    public List<Hospital> getHospitalsByProvincia(String provincia) {
        return repository.findByProvincia(provincia);
    }

}
