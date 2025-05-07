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

    @Query("SELECT u from Usuario u WHERE u.dni=:dni")
    Optional<Usuario> findByDNI(String dni);

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
    List<Usuario> obtenerMedicoHospital(Long id);

    @Query("SELECT m FROM Usuario m WHERE m.rol = 'Médico' AND CONCAT(m.nombre, ' ', m.apellido1, ' ', m.apellido2) = :nombreCompleto AND m.hospital.idHospital = :idHospital")
    Usuario obtenerMedicoPorNombreCompleto(@Param("nombreCompleto") String nombreCompleto, @Param("idHospital") Long idHospital);

    @Query("SELECT p from Usuario p WHERE p.rol='Paciente' AND p.nutricionista.dni=:dni")
    List<Usuario> obtenerPacienteNutricionista(String dni);

    @Query("SELECT p FROM Usuario p WHERE p.rol = 'Paciente' AND CONCAT(p.nombre, ' ', p.apellido1, ' ', p.apellido2) = :nombreCompleto AND p.nutricionista.dni = :dni")
    Usuario obtenerPacientePorNombreCompleto(@Param("nombreCompleto") String nombreCompleto, @Param("dni") String dni);

    @Query("SELECT p from Usuario p WHERE p.rol='Paciente' AND p.medico.dni=:dni")
    List<Usuario> obtenerPacienteMedico(String dni);


}
