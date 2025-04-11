package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Usuario {
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
    @Column(nullable = false)
    private String rol;

    @ManyToOne
    @JoinColumn(name = "medico_dni")
    private Usuario medico;

    @ManyToOne
    @JoinColumn(name = "nutricionista_dni")
    private Usuario nutricionista;

    @OneToMany(mappedBy = "medico")
    private List<Usuario> pacientesMedico;

    // Lista de pacientes asignados a un nutricionista (relación inversa)
    @OneToMany(mappedBy = "nutricionista")
    private List<Usuario> pacientesNutricionista;

    @ManyToOne
    @JoinColumn(name = "idHospital")
    //@JsonIgnore
    @JsonBackReference
    private Hospital hospital;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Paciente paciente;

}
