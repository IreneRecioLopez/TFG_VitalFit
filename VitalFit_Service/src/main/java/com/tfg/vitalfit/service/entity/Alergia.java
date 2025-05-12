package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Alergia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlergia;
    @Column(nullable = false)
    private String tipo;
    @Column(nullable = false)
    private String alergia;

    @ManyToOne
    @JoinColumn(name = "dni_paciente")
    @JsonBackReference(value = "paciente-alergias")
    private Paciente paciente;
}
