package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    @Query("SELECT u FROM Usuario u JOIN FETCH u.hospital WHERE u.dni=:dni AND u.contrasena=:password")
    Optional<Usuario> login(String dni, String password);

    // Método para asociar un usuario a un hospital
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.hospital = :hospital WHERE u.dni = :dniMedico")
    void asociarUsuarioHospital(@Param("dniMedico") String dniMedico, @Param("hospital") Hospital hospital);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.medico = :medico WHERE u.dni = :dni")
    void asociarPacienteMedico(@Param("dni") String dni, @Param("medico") Usuario medico);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.nutricionista = :nutricionista WHERE u.dni = :dni")
    void asociarPacienteNutricionista(@Param("dni") String dni, @Param("nutricionista") Usuario nutricionista);

    @Query("SELECT m from Usuario m WHERE m.rol='Médico' AND m.hospital.idHospital=:id")
    List<Usuario> obtenerMedicosHospital(Long id);

    @Query("SELECT m from Usuario m WHERE m.rol='Nutricionista' AND m.hospital.idHospital=:id")
    List<Usuario> obtenerNutricionistasHospital(Long id);

}
