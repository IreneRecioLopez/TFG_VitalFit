package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.entity.Usuario;
import com.tfg.vitalfit.service.repository.UsuarioRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    //método para iniciar sesión
    public GenericResponse<Usuario> login(String dni, String password) {
        Optional<Usuario> optM = this.repository.login(dni, password);
        if (optM.isPresent()) {
            return new GenericResponse<Usuario>(TIPO_AUTH, RPTA_OK, "Has iniciado sesión correctamente", optM.get());
        } else {
            return new GenericResponse<Usuario>(TIPO_AUTH, RPTA_WARNING, "Lo sentimos, ese usuario no existe", new Usuario());
        }
    }

    //método para guardar los datos del usuario
    public GenericResponse guardarUsuario(Usuario m) {
        Optional<Usuario> optM = this.repository.findById(m.getDni());
        String idf = optM.isPresent() ? optM.get().getDni() : "";
        if (!idf.equals("")) {
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: Ya exite un usuario con el mismo número de DNI.", null);
        } else {
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Usuario registrado correctamente", this.repository.save(m));
        }
    }

    //método para asocial al médico un hospital
    public GenericResponse asociarUsuarioHospital(String dni, Hospital hospital) {
        Optional<Usuario> opt = this.repository.findById(dni);
        String idf = opt.isPresent() ? opt.get().getDni() : "";
        if (!idf.equals("")) {
            this.repository.asociarUsuarioHospital(dni, hospital);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Usuario asociado correctamente al hospital", null);
        } else {
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el usuario con ese dni", null);

        }
    }

    public GenericResponse asociarPacienteMedico(String dni, Usuario medico) {
        Optional<Usuario> opt = this.repository.findById(dni);
        String idf = opt.isPresent() ? opt.get().getDni() : "";
        if (!idf.equals("")) {
            this.repository.asociarPacienteMedico(dni, medico);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Paciente asociado correctamente al medico", null);
        } else {
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el paciente con ese dni", null);

        }
    }

    public GenericResponse asociarPacienteNutricionista(String dni, Usuario nutricionista) {
        Optional<Usuario> opt = this.repository.findById(dni);
        String idf = opt.isPresent() ? opt.get().getDni() : "";
        if (!idf.equals("")) {
            this.repository.asociarPacienteNutricionista(dni, nutricionista);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Paciente asociado correctamente al nutricionista", null);
        } else {
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el paciente con ese dni", null);

        }
    }

    public GenericResponse actualizarPassword(String dni, String password) {
        Optional<Usuario> optU = this.repository.findById(dni);
        if (optU.isPresent()) {
            Usuario uBD = optU.get();
            uBD.setContrasena(password);
            this.repository.save(uBD);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Contraseña actualizada correctamente", null);
        } else {
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el usuario con ese dni", null);
        }
    }

    public GenericResponse actualizarUsuario(Usuario u) {
        Optional<Usuario> optU = this.repository.findById(u.getDni());
        if (optU.isPresent()) {
            this.repository.save(u);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Usuario actualizado correctamente", null);
        } else {
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el usuario con ese dni", null);
        }
    }

    public Usuario getUsuarioByDNI(String dni) {
        Optional<Usuario> optU = this.repository.findById(dni);
        if(optU.isPresent()){
            return optU.get();
        }else{
            return null;
        }
    }

    public List<Usuario> obtenerMedicosHospital(Long id) {
        return repository.obtenerMedicosHospital(id);
    }

    public List<Usuario> obtenerNutricionistasHospital(Long id) {
        return repository.obtenerNutricionistasHospital(id);
    }


}