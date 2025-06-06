package com.tfg.vitalfit.entity.service;

import java.io.Serializable;

public class Operacion implements Serializable {
    private Long idOperacion;
    private String fecha;
    private String nombre;
    private Paciente paciente;

    public Long getIdOperacion() {
        return idOperacion;
    }
    public String getFecha() {
        return fecha;
    }
    public String getNombre() {
        return nombre;
    }
    public Paciente getPaciente() {
        return paciente;
    }


    public void setIdOperacion(Long idOperacion) {
        this.idOperacion = idOperacion;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
