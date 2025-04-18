package com.tfg.vitalfit.entity.service;


public class Alergias {
    private Long idAlergia;
    private String tipo;
    private String alergia;
    private Paciente paciente;

    public Long getIdAlergia() {
        return idAlergia;
    }

    public void setIdAlergia(Long idAlergia) {
        this.idAlergia = idAlergia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public Paciente getPaciente(){ return this.paciente; }
    public void setPaciente(Paciente p){ this.paciente = p; }

}
