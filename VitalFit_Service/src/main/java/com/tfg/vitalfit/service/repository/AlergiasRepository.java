package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Alergias;
import com.tfg.vitalfit.service.entity.Consejo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AlergiasRepository extends CrudRepository<Alergias, Long> {
    @Query("SELECT a from Alergias a WHERE a.idAlergia=:id")
    Optional<Alergias> findById(Long id);

}
