package com.tfg.vitalfit.service.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Pesos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPeso;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(nullable = false)
    private Double peso;

    @ManyToOne
    @JoinColumn(name = "dni_paciente")
    private Paciente paciente;
}
