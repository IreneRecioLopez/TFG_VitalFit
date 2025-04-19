package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Dieta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDieta;
    @Column(nullable = false)
    private String diaSemana;
    @Column(nullable = false)
    private String tramoDia;

    @ManyToOne
    @JoinColumn(name = "dni_paciente")
    @JsonBackReference("paciente-dietas")
    private Paciente paciente;

    @OneToMany(mappedBy = "dieta")
    private List<Platos> platos;

}
