package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Platos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PlatosRepository extends CrudRepository<Platos, Long> {
   /* @Query("SELECT p FROM Platos WHERE p.dieta.idDieta=:idDieta")
    Iterable<Platos> findByDieta(Long idDieta);*/
}
