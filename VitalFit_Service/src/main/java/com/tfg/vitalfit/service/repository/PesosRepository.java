package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Peso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PesosRepository extends CrudRepository<Peso, Long> {
    @Query("SELECT p FROM Peso p WHERE p.paciente.dni = :dni ORDER BY p.fecha DESC")
    List<Peso> getUltimoPeso(String dni);
}
