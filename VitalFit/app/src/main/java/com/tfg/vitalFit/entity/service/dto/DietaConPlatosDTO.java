package com.tfg.vitalfit.entity.service.dto;

import com.tfg.vitalfit.entity.service.Dieta;
import com.tfg.vitalfit.entity.service.Platos;

import java.util.ArrayList;
import java.util.List;

public class DietaConPlatosDTO {
    private Dieta dieta;
    private List<Platos> platos;

    public DietaConPlatosDTO() {
        this.dieta = new Dieta();
        this.platos = new ArrayList<>();
    }

    public DietaConPlatosDTO(Dieta dieta, List<Platos> platos) {
        this.dieta = dieta;
        this.platos = platos;
    }

    public Dieta getDieta() { return dieta; }
    public List<Platos> getPlatos() { return platos; }

    public void setDieta(Dieta dieta) { this.dieta = dieta; }
    public void setPlatos(List<Platos> platos) { this.platos = platos; }
}
