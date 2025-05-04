package com.tfg.vitalfit.entity.service;

import java.io.Serializable;
import java.util.Date;

public class Operaciones implements Serializable {
    private Long idOperacion;
    private String fecha;
    private String nombre;
    private Paciente paciente;

    public Long getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Long idOperacion) {
        this.idOperacion = idOperacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
