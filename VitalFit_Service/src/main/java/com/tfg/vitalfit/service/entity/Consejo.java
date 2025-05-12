package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Consejo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsejo;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String mensaje;
    @Column(nullable = false)
    private Integer leido;


    @ManyToOne
    @JoinColumn(name = "dni_paciente")
    @JsonBackReference("paciente-consejos")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "dni_nutricionista")
    @JsonBackReference("nutricionista-consejos")
    private Usuario nutricionista;
}
