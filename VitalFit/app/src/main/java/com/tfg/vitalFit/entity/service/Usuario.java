package com.tfg.vitalfit.entity.service;

import java.io.Serializable;
import java.util.List;


public class Usuario implements Serializable {
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String contrasena;
    private String telefono;
    private String rol;
    private String provincia;
    private List<Usuario> pacientesMedico;
    private Paciente paciente;
    private Usuario medico;
    private Hospital hospital;

    public String getDni(){ return dni; }
    public String getNombre(){ return nombre; }
    public String getApellido1(){ return apellido1; }
    public String getApellido2(){ return apellido2; }
    public String getTelefono(){ return telefono; }
    public String getContrasena(){ return contrasena; }
    public String getRol(){ return rol; }
    public String getProvincia(){ return provincia; }
    public List<Usuario> getPacientesMedico(){ return pacientesMedico; }
    public Paciente getPaciente(){ return paciente; }
    public Hospital getHospital(){ return hospital; }
    public Usuario getMedico(){ return medico; }
    public String getNombreCompleto() {return nombre + " " + apellido1 + " " + apellido2; }

    public void setDni(String DNI){ this.dni = DNI;}
    public void setNombre(String nombre){ this.nombre = nombre;}
    public void setApellido1(String apellido1){ this.apellido1 = apellido1; }
    public void setApellido2(String apellido2){ this.apellido2 = apellido2;}
    public void setTelefono(String telefono){ this.telefono = telefono; }
    public void setContrasena(String contrasena){ this.contrasena = contrasena; }
    public void setRol(String rol) { this.rol = rol; }
    public void setProvincia(String provincia){ this.provincia = provincia; }
    public void setPacientesMedico(List<Usuario> pacientesMedico){ this.pacientesMedico = pacientesMedico; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setHospital(Hospital hospital){ this.hospital = hospital; }
    public void setMedico(Usuario medico) { this.medico = medico; }


}
