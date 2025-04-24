package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.entity.Usuario;
import com.tfg.vitalfit.service.repository.PacienteRepository;
import com.tfg.vitalfit.service.repository.UsuarioRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class PacienteService {
    private final PacienteRepository repository;
    private final UsuarioRepository usuarioRepository;

    public PacienteService(PacienteRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
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
        Optional<Paciente> opt = this.repository.findByDNI(p.getDni());
        String dni = opt.isPresent() ? opt.get().getDni() : "";
        if(!dni.equals("")){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: El paciente con este DNI ya existe.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Paciente registrado correctamente", this.repository.save(p));
        }
    }

    public GenericResponse actualizarPaciente(Paciente p){
        Optional<Paciente> optP = this.repository.findByDNI(p.getDni());
        if(optP.isPresent()){
            this.repository.save(p);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Paciente actualizado correctamente", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el paciente con ese dni", null);
        }
    }

}
