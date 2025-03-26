package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Medico {
    @Id
    private String dni;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido1;
    private String apellido2;
    @Column(nullable = false)
    private String contrasena;
    @Column(nullable = false, length = 9)
    private String telefono;

    @OneToMany (mappedBy = "medico")
    private List<Paciente> pacientes;

    @ManyToOne
    @JoinColumn(name = "idHospital")
    @JsonIgnore
    private Hospital hospital;

}
