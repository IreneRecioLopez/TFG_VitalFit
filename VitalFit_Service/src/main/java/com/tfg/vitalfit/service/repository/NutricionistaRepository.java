package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Nutricionista;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NutricionistaRepository extends CrudRepository<Nutricionista, Integer> {
    @Query("SELECT n FROM Nutricionista n WHERE n.dni=:dni AND n.contrasena=:password")
    Optional<Nutricionista> login(String dni, String password);

    @Query("SELECT n from Nutricionista n WHERE n.dni=:dni")
    Optional<Nutricionista> findByDNI(String dni);
}
