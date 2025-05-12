package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Plato;
import com.tfg.vitalfit.service.repository.PlatosRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PlatosService {

    private final PlatosRepository repository;

    public PlatosService(PlatosRepository repository){
        this.repository = repository;
    }

    //metodo para guardar los platos de la dieta
    public void guardarPlatos(Iterable<Plato> platos) {
        this.repository.saveAll(platos);
    }
}
