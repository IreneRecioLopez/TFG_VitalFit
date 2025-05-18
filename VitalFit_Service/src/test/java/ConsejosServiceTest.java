import com.tfg.vitalfit.service.entity.*;
import com.tfg.vitalfit.service.repository.ConsejoRepository;
import com.tfg.vitalfit.service.service.ConsejoService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ConsejosServiceTest {

    @Mock
    private ConsejoRepository repository;

    @InjectMocks
    private ConsejoService service;

    @Test
    @DisplayName(("Devuelve error al guardar un consejo sin un paciente y nutricionista asignado"))
    void guardarConsejo_conPacienteYNutricionistaNulo_devuelveWarning() {
        Consejo consejo = new Consejo();
        consejo.setPaciente(null);
        consejo.setNutricionista(null);

        GenericResponse response = service.guardarConsejo(consejo);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: no se ha asignado un paciente o nutricionista.", response.getMessage());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName(("Devuelve ok al guardar un consejo con un paciente y nutricionista asignado"))
    void guardarPeso_conPacienteYNutricionistaValido_devuelveOk() {
        Consejo consejo = new Consejo();
        consejo.setPaciente(new Paciente());
        consejo.setNutricionista(new Usuario());

        Mockito.when(repository.save(consejo)).thenReturn(consejo);

        GenericResponse response = service.guardarConsejo(consejo);

        assertEquals(1, response.getRpta());
        assertEquals("Consejo registrado correctamente", response.getMessage());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName(("Devuelve lista de consejos de un paciente"))
    void getConsejos_byPaciente_devuelveLista() {
        Consejo consejo1 = new Consejo();
        consejo1.setTitulo("Consejo 1");
        Consejo consejo2 = new Consejo();
        consejo2.setTitulo("Consejo 2");
        List<Consejo> mockList = Arrays.asList(consejo1, consejo2);

        Mockito.when(repository.findByPaciente("99999999X")).thenReturn(mockList);

        List<Consejo> result = service.getConsejosByPaciente("99999999X");

        assertEquals(2, result.size());
        assertEquals("Consejo 1", result.get(0).getTitulo());
        assertEquals("Consejo 2", result.get(1).getTitulo());
    }

    @Test
    @DisplayName(("Devuelve lista de consejos de un paciente y nutricionista"))
    void getConsejos_byPacienteYNutricionista_devuelveLista() {
        Consejo consejo1 = new Consejo();
        consejo1.setTitulo("Consejo 1");
        Consejo consejo2 = new Consejo();
        consejo2.setTitulo("Consejo 2");
        List<Consejo> mockList = Arrays.asList(consejo1, consejo2);

        Mockito.when(repository.findByNutricionistaAndPaciente("99999999X", "00000000Y")).thenReturn(mockList);

        List<Consejo> result = service.getConsejosByNutricionistaAndPaciente("99999999X", "00000000Y");

        assertEquals(2, result.size());
        assertEquals("Consejo 1", result.get(0).getTitulo());
        assertEquals("Consejo 2", result.get(1).getTitulo());
    }

    @Test
    @DisplayName(("Devuelve lista de consejos de un paciente"))
    void getConsejos_byPacienteNoLeidos_devuelveLista() {
        Consejo consejo1 = new Consejo();
        consejo1.setTitulo("Consejo 1");
        Consejo consejo2 = new Consejo();
        consejo2.setTitulo("Consejo 2");
        List<Consejo> mockList = Arrays.asList(consejo1, consejo2);

        Mockito.when(repository.findNoLeidos("99999999X")).thenReturn(mockList);

        List<Consejo> result = service.getConsejosNoLeidos("99999999X");

        assertEquals(2, result.size());
        assertEquals("Consejo 1", result.get(0).getTitulo());
        assertEquals("Consejo 2", result.get(1).getTitulo());
    }

    @Test
    @DisplayName("Marca consejo como leído si existe")
    void marcarComoLeido_existente_devuelveOk() {
        Consejo consejo = new Consejo();
        consejo.setIdConsejo(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(consejo));

        GenericResponse response = service.marcarComoLeido(1L);

        assertEquals(1, response.getRpta());
        assertEquals("Consejo actualizado correctamente", response.getMessage());
        Mockito.verify(repository).marcarComoLeido(1L, 1);
    }

    @Test
    @DisplayName("Devuelve warning si el consejo no existe")
    void marcarComoLeido_noExistente_devuelveWarning() {
        Mockito.when(repository.findById(99L)).thenReturn(Optional.empty());

        GenericResponse response = service.marcarComoLeido(99L);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: No se ha encontrado el consejo con ese id", response.getMessage());
        Mockito.verify(repository, Mockito.never()).marcarComoLeido(Mockito.anyLong(), Mockito.anyInt());
    }
}
