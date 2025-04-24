package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference(value = "paciente-alergias")
    private Paciente paciente;
}
