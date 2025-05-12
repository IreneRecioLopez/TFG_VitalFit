package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Observacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObservacion;
    @Column(nullable = false)
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "dni_paciente")
    @JsonBackReference(value = "paciente-observaciones")
    private Paciente paciente;
}
