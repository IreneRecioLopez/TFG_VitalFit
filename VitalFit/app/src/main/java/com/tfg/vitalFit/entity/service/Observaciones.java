package com.tfg.vitalfit.entity.service;

import java.io.Serializable;

public class Observaciones implements Serializable {
    private Long idObservacion;
    private String observacion;
    private Paciente paciente;

    public Long getIdObservacion() {
        return idObservacion;
    }
    public String getObservacion() {
        return observacion;
    }
    public Paciente getPaciente(){ return this.paciente; }

    public void setIdObservacion(Long idObservacion) {
        this.idObservacion = idObservacion;
    }
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    public void setPaciente(Paciente p){ this.paciente = p; }
}
