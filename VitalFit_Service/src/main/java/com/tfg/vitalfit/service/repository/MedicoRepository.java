package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Medico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MedicoRepository extends CrudRepository<Medico, Integer> {
    @Query("SELECT m FROM Medico m WHERE m.dni=:dni AND m.contrasena=:password")
    Optional<Medico> login(String dni, String password);
}
