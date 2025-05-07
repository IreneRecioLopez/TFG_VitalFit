package com.tfg.vitalfit.entity.service;

import java.io.Serializable;
import java.util.List;

public class Paciente implements Serializable {

    private String dni;
    private String numSeguridadSocial;
    private String fechaNacimiento;
    private Double pesoActual;
    private Double altura;
    private Double imc;
    private String cp;
    private String direccion;
    private Usuario medico;
    private Usuario nutricionista;
    //private Usuario paciente;
    private List<Pesos> pesos;
    private List<Alergias> alergias;
    private List<Consejo> consejos;
    private List<Dieta> dietas;
    private List<Operaciones> operaciones;
    private List<Observaciones> observaciones;

    public Paciente(){ }
    public Paciente(String dni){ this.dni = dni;}

    //METODOS GETTERS Y SETTERS
    public String getDni() {
        return dni;
    }
    public String getNumSeguridadSocial() {
        return numSeguridadSocial;
    }
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public Double getPesoActual() {
        return pesoActual;
    }
    public Double getAltura() {
        return altura;
    }
    public Double getImc() {
        return imc;
    }
    public String getCP(){ return cp; }
    public String getDireccion() {
        return direccion;
    }
    public Usuario getMedico() {
        return medico;
    }
    public Usuario getNutricionista() {
        return nutricionista;
    }
    public List<Pesos> getPesos() { return pesos; }
    public List<Alergias> getAlergias() {
        return alergias;
    }
    public List<Consejo> getConsejos() {
        return consejos;
    }
    public List<Dieta> getDietas() {
        return dietas;
    }
    public List<Observaciones> getObservaciones(){ return this.observaciones; }
    public List<Operaciones> getOperaciones(){ return this.operaciones; }



    public void setDni(String dni) {
        this.dni = dni;
    }
    public void setNumSeguridadSocial(String numSeguridadSocial) { this.numSeguridadSocial = numSeguridadSocial; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setPesoActual(Double pesoActual) {
        this.pesoActual = pesoActual;
    }
    public void setAltura(Double altura) {
        this.altura = altura;
    }
    public void setImc(Double imc) {
        this.imc = imc;
    }
    public void setCP(String cp){ this.cp = cp;}
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public void setMedico(Usuario medico) {
        this.medico = medico;
    }
    public void setNutricionista(Usuario nutricionista) {
        this.nutricionista = nutricionista;
    }
    public void setPesos(List<Pesos> pesos) {
        this.pesos = pesos;
    }
    public void setAlergias(List<Alergias> alergias) {
        this.alergias = alergias;
    }
    public void setConsejos(List<Consejo> consejos) {
        this.consejos = consejos;
    }
    public void setDietas(List<Dieta> dietas) {
        this.dietas = dietas;
    }
    public void setObservaciones(List<Observaciones> observaciones){ this.observaciones = observaciones; }
    public void setOperaciones(List<Operaciones> operaciones){ this.operaciones = operaciones; }

    //public void setUsuario(Usuario usuario) { this.paciente = usuario; }
}
