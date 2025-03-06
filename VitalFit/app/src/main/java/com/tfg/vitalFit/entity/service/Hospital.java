package com.tfg.vitalfit.entity.service;


import java.util.List;


public class Hospital {

    private HospitalID idHospital;
    private String localidad;
    private Long cp;
    private String direccion;
    private Long numero;

    private List<Paciente> pacientes;

    private List<Medico> medicos;

    private List<Nutricionista> nutricionistas;
}
