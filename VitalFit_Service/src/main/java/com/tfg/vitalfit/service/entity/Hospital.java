package com.tfg.vitalfit.service.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Hospital {
    @EmbeddedId
    private HospitalID idHospital;
    @Column(nullable = false)
    private String localidad;
    @Column(nullable = false)
    private Long cp;
    @Column(nullable = false)
    private String direccion;
    private Long numero;

    @OneToMany(mappedBy = "hospital")
    private List<Paciente> pacientes;

    @OneToMany(mappedBy = "hospital")
    private List<Medico> medicos;

    @OneToMany(mappedBy = "hospital")
    private List<Nutricionista> nutricionistas;
}
