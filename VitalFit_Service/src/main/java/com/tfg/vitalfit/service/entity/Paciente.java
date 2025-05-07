package com.tfg.vitalfit.service.entity;

import com.fasterxml.jackson.annotation.*;
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
    private String numSeguridadSocial;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    @Column(nullable = false)
    private Double pesoActual;
    @Column(nullable = false)
    private Double altura;
    @Column(nullable = false)
    private Double imc;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private String cp;

    @ManyToOne
    @JoinColumn(name = "dni_medico")
    //@JsonIgnore
    @JsonBackReference("medico-paciente")
    private Usuario medico;

    @ManyToOne
    @JoinColumn(name = "dni_nutricionista")
    //@JsonIgnore
    @JsonBackReference(value = "nutricionista-paciente")
    private Usuario nutricionista;

    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference(value = "paciente-pesos")
    private List<Pesos> pesos;

    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference(value = "paciente-alergias")
    private List<Alergias> alergias;

    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference(value = "paciente-operaciones")
    private List<Operaciones> operaciones;

    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference(value = "paciente-consejos")
    private List<Consejo> consejos;

    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference(value = "paciente-dietas")
    private List<Dieta> dietas;

    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference(value = "paciente-observaciones")
    private List<Observaciones> observaciones;

}