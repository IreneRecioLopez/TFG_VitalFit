package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Consejo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ConsejoRepository extends CrudRepository<Consejo, Long> {
    @Query("SELECT c from Consejo c WHERE c.paciente.dni=:dni")
    List<Consejo> findByPaciente(String dni);

    @Query("SELECT c from Consejo c WHERE c.nutricionista.dni=:dni AND c.paciente.dni =:dniPaciente")
    List<Consejo> findByNutricionistaAndPaciente(String dni, String dniPaciente);

    @Modifying
    @Transactional
    @Query("UPDATE Consejo c SET c.leido = :leido WHERE c.idConsejo = :idConsejo")
    void marcarComoLeido(@Param("idConsejo") Long idConsejo, @Param("leido") Integer leido);

}
