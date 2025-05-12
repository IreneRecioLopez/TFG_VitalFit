package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @ManyToOne
    @JoinColumn(name = "dni_paciente")
    @JsonBackReference("paciente-dietas")
    private Paciente paciente;

    @OneToMany(mappedBy = "dieta")
    @JsonManagedReference(value = "dieta-platos")
    private List<Plato> platos;

}
