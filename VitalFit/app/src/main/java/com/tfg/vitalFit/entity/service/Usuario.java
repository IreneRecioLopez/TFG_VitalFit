package com.tfg.vitalfit.entity.service;

import java.util.List;


public class Usuario {
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String contrasena;
    private String telefono;
    private String rol;
    private List<Paciente> pacientes;
    private Hospital hospital;

    public String getDni(){ return dni; }
    public String getNombre(){ return nombre; }
    public String getApellido1(){ return apellido1; }
    public String getApellido2(){ return apellido2; }
    public String getTelefono(){ return telefono; }
    public String getPassword(){ return contrasena; }
    public String getRol(){ return rol; }
    public List<Paciente> getPacientes(){ return pacientes; }
    public Hospital getHospital(){ return hospital; }

    public void setDNI(String DNI){ this.dni = DNI;}
    public void setNombre(String nombre){ this.nombre = nombre;}
    public void setApellido1(String apellido1){ this.apellido1 = apellido1; }
    public void setApellido2(String apellido2){ this.apellido2 = apellido2;}
    public void setTelefono(String telefono){ this.telefono = telefono; }
    public void setContrasena(String contrasena){ this.contrasena = contrasena; }
    public void setRol(String rol) { this.rol = rol; }
    public void setPacientes(List<Paciente> pacientes){ this.pacientes = pacientes; }
    public void setHospital(Hospital hospital){ this.hospital = hospital; }
}
