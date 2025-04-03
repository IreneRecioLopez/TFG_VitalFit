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
    @JsonIgnore
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "dni_nutricionista")
    @JsonIgnore
    private Nutricionista nutricionista;

    @ManyToOne
    @JoinColumn(name = "idHospital")
    //@JsonIgnore
    @JsonBackReference
    private Hospital hospital;

    @OneToMany(mappedBy = "paciente")
    @JsonManagedReference
    private List<Pesos> pesos;

    @OneToMany(mappedBy = "paciente")
    private List<Alergias> alergias;

    @OneToMany(mappedBy = "paciente")
    private List<Consejo> consejos;

    @OneToMany(mappedBy = "paciente")
    private List<Dieta> dietas;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Nutricionista getNutricionista() {
        return nutricionista;
    }

    public void setNutricionista(Nutricionista nutricionista) {
        this.nutricionista = nutricionista;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
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
