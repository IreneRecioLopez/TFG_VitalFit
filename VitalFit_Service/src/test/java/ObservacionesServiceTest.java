import com.tfg.vitalfit.service.entity.Observacion;
import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.repository.ObservacionesRepository;
import com.tfg.vitalfit.service.service.ObservacionesService;
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

public class ObservacionesServiceTest {
    @Mock
    private ObservacionesRepository repository;

    @InjectMocks
    private ObservacionesService service;


    @Test
    @DisplayName(("Devuelve error al guardar una observación sin un paciente asignado"))
    void guardarObservacion_conPacienteNulo_devuelveWarning() {
        Observacion observacion = new Observacion();
        observacion.setPaciente(null);

        GenericResponse response = service.guardarObservacion(observacion);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: no se ha asignado un paciente.", response.getMessage());
    }

    @Test
    @DisplayName(("Devuelve ok al guardar una observacion con un paciente asignado"))
    void guardarObservacion_conPacienteValido_devuelveOk() {
        Observacion observacion = new Observacion();
        observacion.setPaciente(new Paciente());

        Mockito.when(repository.save(observacion)).thenReturn(observacion);

        GenericResponse response = service.guardarObservacion(observacion);

        assertEquals(1, response.getRpta());
        assertEquals("Observación registrada correctamente", response.getMessage());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName((" Elimina una observación existente"))
    void eliminarObservacion_existente_devuelveOk() {
        Observacion observacion = new Observacion();
        observacion.setIdObservacion(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(observacion));

        GenericResponse response = service.eliminarObservacion(1L);

        assertEquals(1, response.getRpta());
        assertEquals("Observación eliminada correctamente", response.getMessage());
        Mockito.verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName(("Devuelve error al eliminar una observación no existente"))
    void eliminarAlergia_noExistente_devuelveWarning() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        GenericResponse response = service.eliminarObservacion(1L);

        assertEquals(0, response.getRpta());
        assertEquals("No se ha encontrado la observación a eliminar", response.getMessage());
    }
}
