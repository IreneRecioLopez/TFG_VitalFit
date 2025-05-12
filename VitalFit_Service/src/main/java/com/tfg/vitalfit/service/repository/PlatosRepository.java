package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Plato;
import org.springframework.data.repository.CrudRepository;

public interface PlatosRepository extends CrudRepository<Plato, Long> {
   /* @Query("SELECT p FROM Platos WHERE p.dieta.idDieta=:idDieta")
    Iterable<Platos> findByDieta(Long idDieta);*/
}
