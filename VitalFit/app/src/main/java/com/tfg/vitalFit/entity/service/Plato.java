package com.tfg.vitalfit.entity.service;


import java.io.Serializable;

public class Plato implements Serializable {
    private Long idPlato;
    private String tramoDia;
    private String primerPlato;
    private String segundoPlato;
    private String postre;

    private Dieta dieta;

    public Long getIdPlato() { return idPlato; }
    public String getTramoDia() { return tramoDia; }
    public String getPrimerPlato() { return primerPlato; }
    public String getSegundoPlato() { return segundoPlato; }
    public String getPostre() { return postre; }
    public Dieta getDieta() { return dieta; }

    public void setIdPlato(Long idPlato) { this.idPlato = idPlato; }
    public void setTramoDia(String tramoDia) { this.tramoDia = tramoDia; }
    public void setPrimerPlato(String primerPlato) { this.primerPlato = primerPlato; }
    public void setSegundoPlato(String segundoPlato) { this.segundoPlato = segundoPlato; }
    public void setPostre(String postre) { this.postre = postre; }
    public void setDieta(Dieta dieta) { this.dieta = dieta; }
}
