package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Medico;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MedicoRepository extends CrudRepository<Medico, Integer> {
    @Query("SELECT m FROM Medico m WHERE m.dni=:dni AND m.contrasena=:password")
    Optional<Medico> login(String dni, String password);

    @Query("SELECT m from Medico m WHERE m.dni=:dni")
    Optional<Medico> findByDNI(String dni);

    // Método para asociar un médico a un hospital
    @Modifying
    @Transactional
    @Query("UPDATE Medico m SET m.hospital = :hospital WHERE m.dni = :dniMedico")
    void asociarMedicoHospital(@Param("dniMedico") String dniMedico, @Param("hospital") Hospital hospital);

}
