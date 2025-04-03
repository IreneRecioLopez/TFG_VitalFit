package com.tfg.vitalfit.entity.service;


import java.util.Date;


public class Pesos {
    private Long idPeso;
    private String fecha;
    private Double peso;
    private Paciente paciente;

    public Long getIdPeso() {
        return idPeso;
    }
    public void setIdPeso(Long idPeso) {
        this.idPeso = idPeso;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getPeso() {
        return peso;
    }
    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
