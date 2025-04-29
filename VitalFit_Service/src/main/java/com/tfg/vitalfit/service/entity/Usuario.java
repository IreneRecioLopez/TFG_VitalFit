package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(nullable = false)
    private String provincia;

    @ManyToOne
    @JoinColumn(name = "medico_dni")
    @JsonIgnoreProperties("pacientesMedico")
    private Usuario medico;

    @ManyToOne
    @JoinColumn(name = "nutricionista_dni")
    private Usuario nutricionista;

    @OneToMany(mappedBy = "medico")
    private List<Usuario> pacientesMedico;

    // Lista de pacientes asignados a un nutricionista (relación inversa)
    @OneToMany(mappedBy = "nutricionista")
    private List<Usuario> pacientesNutricionista;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idHospital")
    //@JsonIgnore
    //@JsonBackReference(value = "hospital-usuario")
    @JsonIgnoreProperties("usuarios")
    private Hospital hospital;

    @OneToMany(mappedBy = "nutricionista")
    @JsonManagedReference(value = "nutricionista-consejos")
    private List<Consejo> consejos;

    @OneToOne(fetch = FetchType.EAGER)
    private Paciente paciente;

}
