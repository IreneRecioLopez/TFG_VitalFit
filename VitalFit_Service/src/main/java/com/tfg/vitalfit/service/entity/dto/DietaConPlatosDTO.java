package com.tfg.vitalfit.service.entity.dto;

import com.tfg.vitalfit.service.entity.Dieta;
import com.tfg.vitalfit.service.entity.Plato;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DietaConPlatosDTO {
    private Dieta dieta;
    private Iterable<Plato> platos;

    public DietaConPlatosDTO(){
        this.dieta = new Dieta();
        this.platos = new ArrayList<>();
    }

    public DietaConPlatosDTO(Dieta dieta, Iterable<Plato> platos){
        this.dieta = dieta;
        this.platos = platos;
    }

}
