package com.tfg.vitalfit.service.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class HospitalID implements Serializable {
    private String nombre;
    private String provincia;

}
