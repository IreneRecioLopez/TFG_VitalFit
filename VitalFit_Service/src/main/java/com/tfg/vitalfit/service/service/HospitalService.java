package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class HospitalService {

    private final HospitalRepository repository;

    public HospitalService(HospitalRepository repository){
        this.repository = repository;
    }

    // Obtener hospitales por provincia
    public List<Hospital> getHospitalsByProvincia(String provincia) {
        return repository.findByProvincia(provincia);
    }
}
