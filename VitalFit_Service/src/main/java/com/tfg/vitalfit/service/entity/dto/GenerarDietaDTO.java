package com.tfg.vitalfit.service.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tfg.vitalfit.service.entity.Dieta;
import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.entity.Plato;
import lombok.Data;

@Data
public class GenerarDietaDTO {
    private Dieta dieta;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Iterable<Plato> platos;
    private Paciente paciente;


}
