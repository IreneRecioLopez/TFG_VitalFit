package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Medico;
import com.tfg.vitalfit.service.entity.Nutricionista;
import com.tfg.vitalfit.service.repository.NutricionistaRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class NutricionistaService {
    private final NutricionistaRepository repository;

    public NutricionistaService(NutricionistaRepository repository) {
        this.repository = repository;
    }

    //método para iniciar sesión
    public GenericResponse<Nutricionista> login(String dni, String password){
        Optional<Nutricionista> optN = this.repository.login(dni, password);
        if(optN.isPresent()){
            return new GenericResponse<Nutricionista>(TIPO_AUTH, RPTA_OK, "Has iniciado sesión correctamente", optN.get());
        }else{
            return new GenericResponse<Nutricionista>(TIPO_AUTH, RPTA_WARNING, "Lo sentimos, ese usuario no existe", new Nutricionista());
        }
    }

    //método para guardar los datos del nutricionista
    public GenericResponse guardarNutricionista(Nutricionista n){
        Optional<Nutricionista> optN = this.repository.findByDNI(n.getDni());
        String idf = optN.isPresent()? optN.get().getDni() : "";
        if(!idf.equals("")){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: Ya exite un paciente con el mismo número de DNI.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Paciente registrado correctamente", this.repository.save(n));
        }
    }

    //método para asocial al nutricionista un hospital
    public GenericResponse asociarNutricionistaHospital(String dni, Hospital hospital) {
        Optional<Nutricionista> optN = this.repository.findByDNI(dni);
        Nutricionista n = optN.get();
        String idf = optN.isPresent()? optN.get().getDni() : "";
        if(!idf.equals("")){
            this.repository.asociarNutricionistaHospital(dni, hospital);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Medico asociado correctamente", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el médico con ese dni", null);

        }
    }
}
