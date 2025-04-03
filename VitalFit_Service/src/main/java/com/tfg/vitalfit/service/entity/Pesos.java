package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fecha;
    @Column(nullable = false)
    private Double peso;

    @ManyToOne
    @JoinColumn(name = "dni_paciente")
    private Paciente paciente;
}
