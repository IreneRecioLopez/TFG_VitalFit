package com.tfg.vitalfit.service.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Consejo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsejo;
    @Column(nullable = false)
    private String mensaje;

    @ManyToOne
    @JoinColumn(name = "dni_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "dni_nutricionista")
    private Nutricionista nutricionista;
}
