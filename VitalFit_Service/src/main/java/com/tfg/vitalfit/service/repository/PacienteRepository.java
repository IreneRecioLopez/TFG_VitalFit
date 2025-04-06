package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Paciente;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PacienteRepository extends CrudRepository<Paciente, Integer> {
    @Query("SELECT p FROM Paciente p WHERE p.dni=:dni AND p.contrasena=:password")
    Optional<Paciente> login(String dni, String password);

    @Query("SELECT p from Paciente p WHERE p.dni=:dni")
    Optional<Paciente> findByDNI(String dni);

    @Query(value = "SELECT EXISTS(SELECT c.dni FROM Cliente c WHERE c.dni=:dni)", nativeQuery = true)
    int existsDNI(String dni);

    // Método para asociar un paciente a un hospital
    @Modifying
    @Transactional
    @Query("UPDATE Paciente p SET p.hospital=:hospital WHERE p.dni=:dniPaciente")
    void asociarPacienteHospital(@Param("dniPaciente") String deniPaciente, @Param("hospital") Hospital hospital);

    //Método para actualizar la contraseña de un paciente
    @Modifying
    @Transactional
    @Query("UPDATE Paciente p SET p.contrasena=:password WHERE p.dni=:dniPaciente")
    void actualizarPasswordPaciente(@Param("dniPaciente") String dniPaciente, @Param("password") String password);

}
