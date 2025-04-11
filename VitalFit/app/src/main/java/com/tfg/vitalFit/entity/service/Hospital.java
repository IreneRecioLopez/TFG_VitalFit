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
    private List<Usuario> pacientes;
    private List<Usuario> medicos;
    private List<Usuario> nutricionistas;

    public Hospital(){}

    public Hospital(Long id){
        this.idHospital = id;
    }
    public Long getIdHospital(){return idHospital;}
    public String getNombre(){
        return nombre;
    }

    public String getProvincia(){
        return provincia;
    }

    public void setIdHospital(Long id){ this.idHospital = id;}
}
