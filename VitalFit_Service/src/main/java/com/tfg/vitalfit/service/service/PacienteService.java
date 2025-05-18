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

    //método para guardar los datos del paciente
    public GenericResponse guardarPaciente(Paciente p){
        Optional<Paciente> opt = this.repository.findById(p.getDni());
        String dni = opt.isPresent() ? opt.get().getDni() : "";
        if(!dni.equals("")){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: El paciente con este DNI ya existe.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Paciente registrado correctamente", this.repository.save(p));
        }
    }

    public GenericResponse actualizarPaciente(Paciente p){
        Optional<Paciente> optP = this.repository.findById(p.getDni());
        if(optP.isPresent()){
            this.repository.save(p);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Paciente actualizado correctamente", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el paciente con ese dni", null);
        }
    }

    public Paciente pacienteByDNI(String dni) {
        Optional<Paciente> optP = this.repository.findById(dni);
        if(optP.isPresent()){
            return optP.get();
        }else{
            return null;
        }
    }
}
