package com.tfg.vitalfit.entity.service;

import java.util.Date;
import java.util.List;

public class Paciente {

    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String contrasena;
    private String telefono;
    private String numSeguridadSocial;
    private Date fechaNacimiento;
    private Double pesoActual;
    private Double altura;
    private Double imc;
    private Medico medico;
    private Nutricionista nutricionista;
    private Hospital hospital;
    private List<Pesos> pesos;
    private List<Alergias> alergias;
    private List<Consejo> consejos;
    private List<Dieta> dietas;


}
