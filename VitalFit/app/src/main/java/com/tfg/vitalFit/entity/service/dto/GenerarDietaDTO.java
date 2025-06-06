package com.tfg.vitalfit.entity.service.dto;

import com.tfg.vitalfit.entity.service.Dieta;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.entity.service.Plato;

import java.util.ArrayList;

public class GenerarDietaDTO {
    private Dieta dieta;
    private ArrayList<Plato> platos;
    private Paciente paciente;

    public GenerarDietaDTO(){ }

    public Dieta getDieta() { return dieta; }
    public ArrayList<Plato> getPlatos() { return platos; }
    public Paciente getPaciente() { return paciente; }

    public void setDieta(Dieta dieta) { this.dieta = dieta; }
    public void setPlatos(ArrayList<Plato> platos) { this.platos = platos; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
}
