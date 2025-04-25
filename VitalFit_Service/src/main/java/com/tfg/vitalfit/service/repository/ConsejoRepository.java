package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Consejo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConsejoRepository extends CrudRepository<Consejo, Integer> {
    @Query("SELECT c from Consejo c WHERE c.paciente.dni=:dni")
    List<Consejo> findByPaciente(String dni);
}
