package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Consejo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ConsejoRepository extends CrudRepository<Consejo, Integer> {
    @Query("SELECT c from Consejo c WHERE c.paciente.dni=:dni")
    List<Consejo> findByPaciente(String dni);

    @Query("SELECT c from Consejo c WHERE c.nutricionista.dni=:dni")
    List<Consejo> findByNutricionista(String dni);

    @Query("SELECT c from Consejo c WHERE c.idConsejo=:id")
    Optional<Consejo> findById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Consejo c SET c.leido = :leido WHERE c.idConsejo = :idConsejo")
    void marcarComoLeido(@Param("idConsejo") Long idConsejo, @Param("leido") Integer leido);

}
