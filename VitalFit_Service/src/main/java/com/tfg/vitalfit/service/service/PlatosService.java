package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Plato;
import com.tfg.vitalfit.service.repository.PlatosRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class PlatosService {

    private final PlatosRepository repository;

    public PlatosService(PlatosRepository repository){
        this.repository = repository;
    }

    //metodo para guardar los platos de la dieta
    public GenericResponse guardarPlatos(Iterable<Plato> platos) {
        List<Plato> platosValidos = new ArrayList<>();

        for (Plato p : platos) {
            if (p.getDieta() == null) {
                return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: no se ha asignado una dieta a uno o más platos.", null);
            }
            platosValidos.add(p);
        }

        Iterable<Plato> guardados = this.repository.saveAll(platosValidos);
        return new GenericResponse(TIPO_DATA, RPTA_OK, "Platos registrados correctamente", guardados);
    }
}
