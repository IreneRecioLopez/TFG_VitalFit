import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.entity.Peso;
import com.tfg.vitalfit.service.repository.PacienteRepository;
import com.tfg.vitalfit.service.service.PacienteService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteService service;

    @Test
    @DisplayName(("Devuelve error al guardar un paciente con dni existente"))
    void guardarPaciente_conDniExistente_devuelveWarning() {
        Paciente paciente = new Paciente();
        paciente.setDni("12345678X");

        Mockito.when(repository.findById("12345678X"))
                .thenReturn(Optional.of(paciente));

        GenericResponse response = service.guardarPaciente(paciente);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: El paciente con este DNI ya existe.", response.getMessage());
    }

    @Test
    @DisplayName(("Devuelve ok al guardar un paciente con dni no existente"))
    void guardarPaciente_conDniNoExistente_devuelveOk() {
        Paciente paciente = new Paciente();
        paciente.setDni("87654321X");

        Mockito.when(repository.findById("87654321X"))
                .thenReturn(Optional.empty());

        Mockito.when(repository.save(paciente)).thenReturn(paciente);

        GenericResponse response = service.guardarPaciente(paciente);

        assertEquals(1, response.getRpta());
        assertEquals("Paciente registrado correctamente", response.getMessage());
        assertEquals(paciente, response.getBody());
    }

    @Test
    @DisplayName("Actualiza paciente existente correctamente")
    void actualizarPaciente_existente_devuelveOk() {
        Paciente paciente = new Paciente();
        paciente.setDni("12345678X");

        Mockito.when(repository.findById("12345678X"))
                .thenReturn(Optional.of(paciente));

        GenericResponse response = service.actualizarPaciente(paciente);

        assertEquals(1, response.getRpta());
        assertEquals("Paciente actualizado correctamente", response.getMessage());
    }

    @Test
    @DisplayName("Devuelve warning al actualizar paciente no existente")
    void actualizarPaciente_noExistente_devuelveWarning() {
        Paciente paciente = new Paciente();
        paciente.setDni("00000000X");

        Mockito.when(repository.findById("00000000X"))
                .thenReturn(Optional.empty());

        GenericResponse response = service.actualizarPaciente(paciente);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: No se ha encontrado el paciente con ese dni", response.getMessage());
    }

    @Test
    @DisplayName(("Devuelve error al obtener un paciente que no existe"))
    void getPaciente_noExistente_devuelveNull() {
        Mockito.when(repository.findById("99999999X")).thenReturn(Optional.empty());

        Paciente result = service.pacienteByDNI("99999999X");

        assertNull(result);
    }

    @Test
    @DisplayName("Devuelve ok al obtener un paciente existente")
    void getPaciente_Existente_devuelveOk() {
        Paciente paciente = new Paciente();
        paciente.setDni("99999999X");
        Mockito.when(repository.findById("99999999X")).thenReturn(Optional.of(paciente));

        Paciente result = service.pacienteByDNI("99999999X");

        assertEquals(paciente, result);
    }
}
