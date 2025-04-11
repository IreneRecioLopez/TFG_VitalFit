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
    private String provincia;
    @Column(nullable = false)
    private String direccion;
    private int vegetariana;
    private int vegana;

    @ManyToOne
    @JoinColumn (name = "dni_medico")
    //@JsonIgnore
    @JsonBackReference("medico-paciente")
    private Usuario medico;

    @ManyToOne
    @JoinColumn(name = "dni_nutricionista")
    //@JsonIgnore
    @JsonBackReference("nutricionista-paciente")
    private Usuario nutricionista;

    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference("paciente-pesos")
    private List<Pesos> pesos;

    @OneToMany(mappedBy = "paciente")
    private List<Alergias> alergias;

    @OneToMany(mappedBy = "paciente")
    private List<Consejo> consejos;

    @OneToMany(mappedBy = "paciente")
    private List<Dieta> dietas;

    @OneToOne
    @JoinColumn(name = "dni")
    @MapsId
    @JsonBackReference
    private Usuario usuario;

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNumSeguridadSocial() {
        return numSeguridadSocial;
    }
    public void setNumSeguridadSocial(String numSeguridadSocial) {
        this.numSeguridadSocial = numSeguridadSocial;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Double getPesoActual() {
        return pesoActual;
    }
    public void setPesoActual(Double pesoActual) {
        this.pesoActual = pesoActual;
    }

    public Double getAltura() {
        return altura;
    }
    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getImc() {
        return imc;
    }
    public void setImc(Double imc) {
        this.imc = imc;
    }

    public String getProvincia() {
        return provincia;
    }
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Usuario getMedico() {
        return medico;
    }
    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public Usuario getNutricionista() {
        return nutricionista;
    }
    public void setNutricionista(Usuario nutricionista) {
        this.nutricionista = nutricionista;
    }


    public List<Pesos> getPesos() {
        return pesos;
    }
    public void setPesos(List<Pesos> pesos) {
        this.pesos = pesos;
    }

    public List<Alergias> getAlergias() {
        return alergias;
    }
    public void setAlergias(List<Alergias> alergias) {
        this.alergias = alergias;
    }

    public List<Consejo> getConsejos() {
        return consejos;
    }
    public void setConsejos(List<Consejo> consejos) {
        this.consejos = consejos;
    }

    public List<Dieta> getDietas() {
        return dietas;
    }
    public void setDietas(List<Dieta> dietas) {
        this.dietas = dietas;
    }
}
