package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Paciente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.Optional;

public interface PacienteRepository extends CrudRepository<Paciente, String> {
    @Query("SELECT p from Paciente p WHERE p.dni=:dni")
    Optional<Paciente> findByDNI(String dni);


}
