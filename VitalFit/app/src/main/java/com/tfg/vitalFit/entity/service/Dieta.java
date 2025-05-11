package com.tfg.vitalfit.entity.service;

import java.io.Serializable;
import java.util.List;

public class Dieta implements Serializable {
    private Long idDieta;
    private String diaSemana;
    private Paciente paciente;
    private List<Platos> platos;

    public Long getIdDieta() {
        return idDieta;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public List<Platos> getPlatos() {
        return platos;
    }

    public void setIdDieta(Long idDieta) {
        this.idDieta = idDieta;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setPlatos(List<Platos> platos) {
        this.platos = platos;
    }
}
