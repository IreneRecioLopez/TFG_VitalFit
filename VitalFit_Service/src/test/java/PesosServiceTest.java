import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.entity.Peso;
import com.tfg.vitalfit.service.entity.Usuario;
import com.tfg.vitalfit.service.repository.PesosRepository;
import com.tfg.vitalfit.service.service.PesosService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PesosServiceTest {

    @Mock
    private PesosRepository repository;

    @InjectMocks
    private PesosService service;

    @Test
    @DisplayName(("Devuelve error al guardar un peso sin un paciente asignado"))
    void guardarPeso_conPacienteNulo_devuelveWarning() {
        Peso peso = new Peso();
        peso.setPaciente(null);

        GenericResponse response = service.guardarPeso(peso);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: no se ha asignado un paciente.", response.getMessage());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName(("Devuelve ok al guardar un peso con un paciente asignado"))
    void guardarPeso_conPacienteValido_devuelveOk() {
        Peso peso = new Peso();
        peso.setPaciente(new Paciente());

        Mockito.when(repository.save(peso)).thenReturn(peso);

        GenericResponse response = service.guardarPeso(peso);

        assertEquals(1, response.getRpta());
        assertEquals("Peso registrado correctamente", response.getMessage());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Actualiza peso existente correctamente")
    void actualizarPeso_existente_devuelveOk() {
        Peso peso = new Peso();
        peso.setIdPeso(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(peso));

        GenericResponse response = service.actualizarPeso(peso);

        assertEquals(1, response.getRpta());
        assertEquals("Peso actualizado correctamente", response.getMessage());
    }

    @Test
    @DisplayName("Devuelve warning al actualizar peso no existente")
    void actualizarPeso_noExistente_devuelveWarning() {
        Peso peso = new Peso();
        peso.setIdPeso(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        GenericResponse response = service.actualizarPeso(peso);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: No se ha encontrado el peso con ese id", response.getMessage());
    }

    @Test
    @DisplayName(("Devuelve error al obtener un peso que no existe"))
    void getUltimoPeso_sinResultados_devuelveNull() {
        Mockito.when(repository.getUltimoPeso("99999999X")).thenReturn(Collections.emptyList());

        Peso result = service.getUltimoPeso("99999999X");

        assertNull(result);
    }

    @Test
    @DisplayName("Devuelve ok al obtener el último peso")
    void getUltimoPeso_conResultados_devuelvePrimero() {
        Peso peso = new Peso();
        Mockito.when(repository.getUltimoPeso("99999999X")).thenReturn(Arrays.asList(peso));

        Peso result = service.getUltimoPeso("99999999X");

        assertEquals(peso, result);
    }
}
