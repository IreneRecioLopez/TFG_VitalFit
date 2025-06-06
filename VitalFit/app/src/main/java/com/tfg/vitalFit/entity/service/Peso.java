package com.tfg.vitalfit.entity.service;


import java.io.Serializable;

public class Peso implements Serializable {
    private Long idPeso;
    private String fecha;
    private Double peso;
    private Paciente paciente;

    public Long getIdPeso() {
        return idPeso;
    }
    public String getFecha() {
        return fecha;
    }
    public Double getPeso() {
        return peso;
    }
    public Paciente getPaciente() {
        return paciente;
    }

    public void setIdPeso(Long idPeso) {
        this.idPeso = idPeso;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public void setPeso(Double peso) {
        this.peso = peso;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
