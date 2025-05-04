package com.tfg.vitalfit.entity.service;

import java.io.Serializable;
import java.util.List;

public class Dieta implements Serializable {
    private Long idDieta;
    private String diaSemana;
    private String tramoDia;

    private Paciente paciente;

    private List<Platos> platos;

}
