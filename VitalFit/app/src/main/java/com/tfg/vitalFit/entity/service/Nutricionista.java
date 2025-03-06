package com.tfg.vitalfit.entity.service;


import java.util.List;

public class Nutricionista {
    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String contrasena;
    private String telefono;

    private Hospital hospital;

    private List<Paciente> pacientes;

    private List<Consejo> consejos;
}
