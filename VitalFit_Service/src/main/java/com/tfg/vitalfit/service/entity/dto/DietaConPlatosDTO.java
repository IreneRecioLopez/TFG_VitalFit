package com.tfg.vitalfit.service.entity.dto;

import com.tfg.vitalfit.service.entity.Dieta;
import com.tfg.vitalfit.service.entity.Platos;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DietaConPlatosDTO {
    private Dieta dieta;
    private Iterable<Platos> platos;

    public DietaConPlatosDTO(){
        this.dieta = new Dieta();
        this.platos = new ArrayList<>();
    }

    public DietaConPlatosDTO(Dieta dieta, Iterable<Platos> platos){
        this.dieta = dieta;
        this.platos = platos;
    }

}
