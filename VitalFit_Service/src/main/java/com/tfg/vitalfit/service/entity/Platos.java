package com.tfg.vitalfit.service.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Platos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlato;
    @Column(nullable = false)
    private String primerPlato;
    private String segundoPlato;
    private String postre;

    @ManyToOne
    @JoinColumn(name = "idDieta")
    private Dieta dieta;
}
