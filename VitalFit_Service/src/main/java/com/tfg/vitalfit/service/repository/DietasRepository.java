package com.tfg.vitalfit.service.repository;

import com.tfg.vitalfit.service.entity.Dieta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DietasRepository extends CrudRepository<Dieta, Long> {
    @Query("SELECT d from Dieta d WHERE d.paciente.dni=:dniPaciente")
    Iterable<Dieta> obtenerMiDieta(String dniPaciente);

    @Query("SELECT d from Dieta d WHERE d.paciente.dni=:dniPaciente AND d.diaSemana=:diaSemana")
    Optional<Dieta> findByPacienteAndDiaSemana(String dniPaciente, String diaSemana);
}
