package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    //@JsonIgnore
    @JsonBackReference(value = "dieta-platos")
    private Dieta dieta;
}
