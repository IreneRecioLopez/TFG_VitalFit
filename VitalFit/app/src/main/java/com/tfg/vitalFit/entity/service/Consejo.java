package com.tfg.vitalfit.entity.service;


public class Consejo {
    private Long idConsejo;
    private String titulo;
    private String mensaje;

    private Paciente paciente;
    private Nutricionista nutricionista;

    public Long getIdConsejo() { return idConsejo; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public Paciente getPaciente() { return paciente; }
    public Nutricionista getNutricionista() { return nutricionista; }

    public void setIdConsejo(Long idConsejo) { this.idConsejo = idConsejo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setNutricionista(Nutricionista nutricionista) { this.nutricionista = nutricionista; }
}
