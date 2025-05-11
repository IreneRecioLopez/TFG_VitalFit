package com.tfg.vitalfit.entity.service.dto;

import com.tfg.vitalfit.entity.service.Dieta;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.entity.service.Platos;

import java.util.ArrayList;

public class GenerarDietaDTO {
    private Dieta dieta;
    private ArrayList<Platos> platos;
    private Paciente paciente;

    public GenerarDietaDTO(){ }

    public GenerarDietaDTO(Dieta dieta, ArrayList<Platos> platos, Paciente paciente) {
        this.dieta = dieta;
        this.platos = platos;
        this.paciente = paciente;
    }

    public Dieta getDieta() { return dieta; }
    public ArrayList<Platos> getPlatos() { return platos; }
    public Paciente getPaciente() { return paciente; }

    public void setDieta(Dieta dieta) { this.dieta = dieta; }
    public void setPlatos(ArrayList<Platos> platos) { this.platos = platos; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
}
