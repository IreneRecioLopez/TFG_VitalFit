import com.tfg.vitalfit.service.entity.Operacion;
import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.repository.OperacionesRepository;
import com.tfg.vitalfit.service.service.OperacionesService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class OperacionesServiceTest {
    @Mock
    private OperacionesRepository repository;

    @InjectMocks
    private OperacionesService service;


    @Test
    @DisplayName(("Devuelve error al guardar una operación sin un paciente asignado"))
    void guardarOperación_conPacienteNulo_devuelveWarning() {
        Operacion operacion = new Operacion();
        operacion.setPaciente(null);

        GenericResponse response = service.guardarOperacion(operacion);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: no se ha asignado un paciente.", response.getMessage());
    }

    @Test
    @DisplayName(("Devuelve ok al guardar una operación con un paciente asignado"))
    void guardarOperacion_conPacienteValido_devuelveOk() {
        Operacion operacion = new Operacion();
        operacion.setPaciente(new Paciente());

        Mockito.when(repository.save(operacion)).thenReturn(operacion);

        GenericResponse response = service.guardarOperacion(operacion);

        assertEquals(1, response.getRpta());
        assertEquals("Operación registrada correctamente", response.getMessage());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName((" Elimina una operación existente"))
    void eliminarOperacion_existente_devuelveOk() {
        Operacion operacion = new Operacion();
        operacion.setIdOperacion(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(operacion));

        GenericResponse response = service.eliminarOperacion(1L);

        assertEquals(1, response.getRpta());
        assertEquals("Operación eliminada correctamente", response.getMessage());
        Mockito.verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName(("Devuelve error al eliminar una operación no existente"))
    void eliminarOperación_noExistente_devuelveWarning() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        GenericResponse response = service.eliminarOperacion(1L);

        assertEquals(0, response.getRpta());
        assertEquals("No se ha encontrado la operación a eliminar", response.getMessage());
    }
}
