package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Operacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperacion;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fecha;
    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "dni_paciente")
    @JsonBackReference(value = "paciente-operaciones")
    private Paciente paciente;
}
