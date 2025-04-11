package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.repository.PacienteRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class PacienteService {
    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    //método para iniciar sesión
   /* public GenericResponse<Paciente> login(String dni, String password){
        Optional<Paciente> optP = this.repository.login(dni, password);
        if(optP.isPresent()){
            return new GenericResponse<Paciente>(TIPO_AUTH, RPTA_OK, "Has iniciado sesión correctamente", optP.get());
        }else{
            return new GenericResponse<Paciente>(TIPO_AUTH, RPTA_WARNING, "Lo sentimos, ese usuario no existe", new Paciente());

        }
    }*/

    //método para guardar los datos del paciente
    public GenericResponse guardarPaciente(Paciente p){
        Optional<Paciente> optP = this.repository.findByDNI(p.getDni());
        String idf = optP.isPresent()? optP.get().getDni() : "";
        if(!idf.equals("")){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: Ya exite un paciente con el mismo número de DNI.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Paciente registrado correctamente", this.repository.save(p));
        }
    }

    /*public GenericResponse asociarPacienteHospital(String dni, Hospital hospital) {
        Optional<Paciente> optP = this.repository.findByDNI(dni);
        Paciente p = optP.get();
        String idf = optP.isPresent()? optP.get().getDni() : "";
        if(!idf.equals("")){
            this.repository.asociarPacienteHospital(dni, hospital);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Paciente asociado correctamente", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el paciente con ese dni", null);

        }
    }*/

    /*public GenericResponse actualizarPassword(String dni, String password){
        Optional<Paciente> optP = this.repository.findByDNI(dni);
        if(optP.isPresent()){
            Paciente pBD = optP.get();
            pBD.setContrasena(password);
            this.repository.save(pBD);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Contraseña actualizada correctamente", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el paciente con ese dni", null);

        }
    }*/
}
