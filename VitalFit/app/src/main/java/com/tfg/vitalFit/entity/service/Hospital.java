package com.tfg.vitalfit.entity.service;


import java.util.List;


public class Hospital {

    private Long idHospital;
    private String nombre;
    private String provincia;
    private String localidad;
    private Long cp;
    private String direccion;
    private Long numero;
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<Nutricionista> nutricionistas;

    public String getNombre(){
        return nombre;
    }

    public String getProvincia(){
        return provincia;
    }
}
