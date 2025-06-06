package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlato;
    @Column(nullable = false)
    private String tramoDia;
    private String primerPlato;
    private String segundoPlato;
    private String postre;

    @ManyToOne
    @JoinColumn(name = "idDieta")
    @JsonBackReference(value = "dieta-platos")
    private Dieta dieta;
}
