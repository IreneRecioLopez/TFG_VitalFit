import com.tfg.vitalfit.service.entity.Alergia;
import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.repository.AlergiasRepository;
import com.tfg.vitalfit.service.service.AlergiasService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AlergiasServiceTest {

    @Mock
    private AlergiasRepository repository;

    @InjectMocks
    private AlergiasService service;


    @Test
    @DisplayName(("Devuelve error al guardar una alergia sin un paciente asignado"))
    void guardarAlergia_conPacienteNulo_devuelveWarning() {
        Alergia alergia = new Alergia();
        alergia.setPaciente(null);

        GenericResponse response = service.guardarAlergia(alergia);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: no se ha asignado un paciente.", response.getMessage());
    }

    @Test
    @DisplayName(("Devuelve ok al guardar una alergia con un paciente asignado"))
    void guardarAlergia_conPacienteValido_devuelveOk() {
        Alergia alergia = new Alergia();
        alergia.setPaciente(new Paciente());

        Mockito.when(repository.save(alergia)).thenReturn(alergia);

        GenericResponse response = service.guardarAlergia(alergia);

        assertEquals(1, response.getRpta());
        assertEquals("Alergia registrada correctamente", response.getMessage());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName((" Elimina una alergia existente"))
    void eliminarAlergia_existente_devuelveOk() {
        Alergia alergia = new Alergia();
        alergia.setIdAlergia(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(alergia));

        GenericResponse response = service.eliminarAlergia(1L);

        assertEquals(1, response.getRpta());
        assertEquals("Alergia eliminada correctamente", response.getMessage());
        Mockito.verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName(("Devuelve error al eliminar una alergia no existente"))
    void eliminarAlergia_noExistente_devuelveWarning() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        GenericResponse response = service.eliminarAlergia(1L);

        assertEquals(0, response.getRpta());
        assertEquals("No se ha encontrado la alergia a eliminar", response.getMessage());
    }
}
