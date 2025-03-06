package com.tfg.vitalfit.service.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Alergias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlergia;
    @Column(nullable = false)
    private String tipo;
    @Column(nullable = false)
    private String alergia;

    @ManyToOne
    @JoinColumn(name = "dni_paciente")
    private Paciente paciente;
}
