package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE u.dni=:dni AND u.contrasena=:password")
    Optional<Usuario> login(String dni, String password);

    @Query("SELECT u from Usuario u WHERE u.dni=:dni")
    Optional<Usuario> findByDNI(String dni);

    // Método para asociar un usuario a un hospital
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.hospital = :hospital WHERE u.dni = :dniMedico")
    void asociarUsuarioHospital(@Param("dniMedico") String dniMedico, @Param("hospital") Hospital hospital);

}
