package com.tfg.vitalfit.entity.service;

import java.sql.Date;
import java.util.List;

public class Paciente {

    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String contrasena;
    private String telefono;
    private String numSeguridadSocial;
    private String fechaNacimiento;
    private Double pesoActual;
    private Double altura;
    private Double imc;
    private String provincia;
    private String cp;
    private String direccion;
    private Integer vegetariana;
    private Integer vegana;
    private Medico medico;
    private Nutricionista nutricionista;
    private Hospital hospital;
    private List<Pesos> pesos;
    private List<Alergias> alergias;
    private List<Consejo> consejos;
    private List<Dieta> dietas;

    //METODOS GETTERS Y SETTERS
    public String getDNI() {
        return dni;
    }
    public void setDNI(String dni) {
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
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

    public String getCP(){ return cp; }
    public void setCP(String cp){ this.cp = cp;}

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getVegetariana(){ return vegetariana; }
    public void setVegetariana(Integer vegetaria) { this.vegetariana = vegetariana; }

    public Integer getVegana(){ return vegana; }
    public void setVegana(Integer vegana) { this.vegana = vegana; }

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
