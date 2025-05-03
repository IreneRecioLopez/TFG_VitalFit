package com.tfg.vitalfit.service.controller;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Usuario;
import com.tfg.vitalfit.service.service.UsuarioService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/usuario")
public class UsuarioREST {
    private final UsuarioService service;

    public UsuarioREST(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public GenericResponse<Usuario> login(HttpServletRequest request){
        String dni = request.getParameter("dni");
        String password = request.getParameter("password");
        return this.service.login(dni, password);
    }

    @PostMapping("/save")
    public GenericResponse save(@RequestBody Usuario m){
        return this.service.guardarUsuario(m);
    }


    @GetMapping("/{dni}")
    public Usuario getByDNI(@PathVariable("dni") String dni){
        return this.service.getUsuarioByDNI(dni);
    }

    @GetMapping("/medico/hospital/{idHospital}")
    public List<Usuario> medicosHospital(@PathVariable("idHospital") Long id){
        return this.service.obtenerMedicoHospital(id);
    }

    @GetMapping("/medico/hospital")
    public Usuario medicoNombreCompletoPorHospital(@RequestParam String nombreCompleto, @RequestParam Long idHospital){
        return this.service.obtenerMedicoPorNombreCompleto(nombreCompleto, idHospital);
    }

    @GetMapping("/paciente/nutricionista/{dni}")
    public List<Usuario> pacientesPorNutricionista(@PathVariable("dni") String dni){
        return this.service.obtenerPacienteNutricionista(dni);
    }

    @GetMapping("/paciente/nutricionista")
    public Usuario pacienteNombreCompletoPorNutricionista(@RequestParam String nombreCompleto, @RequestParam String dni){
        return this.service.obtenerPacientePorNombreCompleto(nombreCompleto, dni);
    }

    @PutMapping("/{dni}/hospital")
    public GenericResponse asociarUsuarioHospital(@PathVariable String dni, @RequestBody Hospital hospital){
        return this.service.asociarUsuarioHospital(dni, hospital);
    }

    @PutMapping("/paciente/{dni}/medico")
    public GenericResponse asociarPacienteMedico(@PathVariable String dni, @RequestBody Usuario medico){
        return this.service.asociarPacienteMedico(dni, medico);
    }

    @PutMapping("/paciente/{dni}/nutricionista")
    public GenericResponse asociarPacienteNutricionista(@PathVariable String dni, @RequestBody Usuario nutricionista){
        return this.service.asociarPacienteNutricionista(dni, nutricionista);
    }

    @PutMapping("/{dni}/password")
    public GenericResponse actualizarPassword(@PathVariable String dni, @RequestBody String password){
        return this.service.actualizarPassword(dni, password);
    }

    @PutMapping("/update")
    public GenericResponse actualizarUsuario(@RequestBody Usuario u){
        return this.service.actualizarUsuario(u);
    }

}
