package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Nutricionista;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface NutricionistaRepository extends CrudRepository<Nutricionista, Integer> {
    @Query("SELECT n FROM Nutricionista n WHERE n.dni=:dni AND n.contrasena=:password")
    Optional<Nutricionista> login(String dni, String password);

    @Query("SELECT n from Nutricionista n WHERE n.dni=:dni")
    Optional<Nutricionista> findByDNI(String dni);

    // Método para asociar un médico a un hospital
    @Modifying
    @Transactional
    @Query("UPDATE Nutricionista n SET n.hospital = :hospital WHERE n.dni = :dniNutricionista")
    void asociarNutricionistaHospital(@Param("dniNutricionista") String dniNutricionista, @Param("hospital") Hospital hospital);
}
