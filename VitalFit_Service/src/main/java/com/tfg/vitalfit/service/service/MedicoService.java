package com.tfg.vitalfit.service.service;

import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Medico;
import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.repository.HospitalRepository;
import com.tfg.vitalfit.service.repository.MedicoRepository;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

import static com.tfg.vitalfit.service.utils.Global.*;

@Service
@Transactional
public class MedicoService {
    private final MedicoRepository repository;
    private final HospitalRepository hRepository;

    public MedicoService(MedicoRepository repository, HospitalRepository hRepository) {
        this.repository = repository;
        this.hRepository = hRepository;
    }

    //método para iniciar sesión
    public GenericResponse<Medico> login(String dni, String password){
        Optional<Medico> optM = this.repository.login(dni, password);
        if(optM.isPresent()){
            return new GenericResponse<Medico>(TIPO_AUTH, RPTA_OK, "Has iniciado sesión correctamente", optM.get());
        }else{
            return new GenericResponse<Medico>(TIPO_AUTH, RPTA_WARNING, "Lo sentimos, ese usuario no existe", new Medico());
        }
    }

    //método para guardar los datos del medico
    public GenericResponse guardarMedico(Medico m){
        Optional<Medico> optM = this.repository.findByDNI(m.getDni());
        String idf = optM.isPresent()? optM.get().getDni() : "";
        if(!idf.equals("")){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: Ya exite un médico con el mismo número de DNI.", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Medico registrado correctamente", this.repository.save(m));
        }
    }

    //Método para asociar el médico al hospital
    /*public void asociarMedicoHospital(String dni, Long idHospital) {
        Optional<Medico> m = repository.findByDNI(dni);
        Hospital h = hRepository.findById(idHospital);
        Medico medico = m.get();
        medico.setHospital(h);
        repository.save(medico);
    }*/

    /*public GenericResponse asociarMedicoHospital(String dni, Hospital hospital) {
        Optional<Medico> optM = this.repository.findByDNI(dni);
        String idf = optM.isPresent()? optM.get().getDni() : "";
        if(!idf.equals("")){
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: Ya exite un médico con el mismo número de DNI.", null);
        }else{
            Medico m = optM.get();
            m.setHospital(hospital);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Medico asociado correctamente", this.repository.);
        }
    }*/

    public GenericResponse asociarMedicoHospital(String dni, Hospital hospital) {
        Optional<Medico> optM = this.repository.findByDNI(dni);
        Medico m = optM.get();
        String idf = optM.isPresent()? optM.get().getDni() : "";
        if(!idf.equals("")){
            this.repository.asociarMedicoHospital(dni, hospital);
            return new GenericResponse(TIPO_DATA, RPTA_OK, "Medico asociado correctamente", null);
        }else{
            return new GenericResponse(TIPO_DATA, RPTA_WARNING, "Lo sentimos: No se ha encontrado el médico con ese dni", null);

        }
    }


}
