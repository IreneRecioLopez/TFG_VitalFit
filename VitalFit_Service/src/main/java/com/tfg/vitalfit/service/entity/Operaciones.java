package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Operaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperacion;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "dni_paciente")
    private Paciente paciente;
}
