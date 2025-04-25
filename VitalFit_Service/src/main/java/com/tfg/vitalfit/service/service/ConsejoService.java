package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Consejo;
import com.tfg.vitalfit.service.repository.ConsejoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class ConsejoService {

    private final ConsejoRepository repository;

    public ConsejoService(ConsejoRepository repository) { this.repository = repository; }
    public List<Consejo> getConsejosByPaciente(String dni) {
        return repository.findByPaciente(dni);
    }
}
