package com.tfg.vitalfit.entity.service;


import java.io.Serializable;
import java.util.List;


public class Hospital implements Serializable {

    private Long idHospital;
    private String nombre;
    private String provincia;
    private String distrito;
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
    public void setNombre(String nombre){ this.nombre = nombre; }
}
