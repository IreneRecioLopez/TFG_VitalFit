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
    private List<Peso> pesos;
    private List<Alergia> alergias;
    private List<Consejo> consejos;
    private List<Dieta> dietas;
    private List<Operacion> operaciones;
    private List<Observacion> observaciones;

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
    public List<Peso> getPesos() { return pesos; }
    public List<Alergia> getAlergias() {
        return alergias;
    }
    public List<Consejo> getConsejos() {
        return consejos;
    }
    public List<Dieta> getDietas() {
        return dietas;
    }
    public List<Observacion> getObservaciones(){ return this.observaciones; }
    public List<Operacion> getOperaciones(){ return this.operaciones; }

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
    public void setPesos(List<Peso> pesos) {
        this.pesos = pesos;
    }
    public void setAlergias(List<Alergia> alergias) {
        this.alergias = alergias;
    }
    public void setConsejos(List<Consejo> consejos) {
        this.consejos = consejos;
    }
    public void setDietas(List<Dieta> dietas) {
        this.dietas = dietas;
    }
    public void setObservaciones(List<Observacion> observaciones){ this.observaciones = observaciones; }
    public void setOperaciones(List<Operacion> operaciones){ this.operaciones = operaciones; }
}
