package com.tfg.vitalfit.entity.service;


import java.io.Serializable;

public class Consejo implements Serializable {
    private Long idConsejo;
    private String titulo;
    private String mensaje;
    private Integer leido;

    private Paciente paciente;
    private Usuario nutricionista;

    public Long getIdConsejo() { return idConsejo; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public Integer getLeido() { return leido; }
    public Paciente getPaciente() { return paciente; }
    public Usuario getNutricionista() { return nutricionista; }

    public void setIdConsejo(Long idConsejo) { this.idConsejo = idConsejo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setLeido(Integer leido) { this.leido = leido; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setNutricionista(Usuario nutricionista) { this.nutricionista = nutricionista; }
}
