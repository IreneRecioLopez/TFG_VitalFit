package com.tfg.vitalfit.entity.service;


import java.io.Serializable;

public class Alergias implements Serializable {
    private Long idAlergia;
    private String tipo;
    private String alergia;
    private Paciente paciente;

    public Long getIdAlergia() {
        return idAlergia;
    }
    public String getTipo() {
        return tipo;
    }
    public String getAlergia() {
        return alergia;
    }
    public Paciente getPaciente(){ return this.paciente; }

    public void setIdAlergia(Long idAlergia) {
        this.idAlergia = idAlergia;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }
    public void setPaciente(Paciente p){ this.paciente = p; }

}
