package com.tfg.vitalfit.service.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Paciente {
    @Id
    private String dni;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido1;
    private String apellido2;
    @Column(nullable = false)
    private String contrasena;
    @Column(length = 9, nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String numSeguridadSocial;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(nullable = false)
    private Double pesoActual;
    @Column(nullable = false)
    private Double altura;
    @Column(nullable = false)
    private Double imc;

    @ManyToOne
    @JoinColumn (name = "dni_medico")
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "dni_nutricionista")
    private Nutricionista nutricionista;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "nombre_hospital", referencedColumnName = "nombre"),
            @JoinColumn(name = "provincia_hospital", referencedColumnName = "provincia")
    })
    private Hospital hospital;

    @OneToMany(mappedBy = "paciente")
    private List<Pesos> pesos;

    @OneToMany(mappedBy = "paciente")
    private List<Alergias> alergias;

    @OneToMany(mappedBy = "paciente")
    private List<Consejo> consejos;

    @OneToMany(mappedBy = "paciente")
    private List<Dieta> dietas;


}
