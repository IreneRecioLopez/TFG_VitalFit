package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHospital;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String provincia;
    @Column(nullable = false)
    private String distrito;
    @Column(nullable = false)
    private Long cp;
    @Column(nullable = false)
    private String direccion;
    private Long numero;

    @OneToMany(mappedBy = "hospital")
    @JsonManagedReference
    private List<Usuario> usuarios;

}
