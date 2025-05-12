package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Hospital;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HospitalRepository extends CrudRepository<Hospital, Long> {

    @Query("SELECT h from Hospital h WHERE h.provincia=:provincia")
    List<Hospital> findByProvincia(String provincia);

    @Query("SELECT h from Hospital h WHERE h.provincia=:provincia AND h.nombre=:name")
    Hospital findByNameAndProvincia(String name, String provincia);

    @Query("SELECT h from Hospital h WHERE h.nombre=:name")
    Hospital findByName(String name);
}
