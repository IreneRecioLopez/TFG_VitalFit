import com.tfg.vitalfit.service.entity.Dieta;
import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.entity.Peso;
import com.tfg.vitalfit.service.entity.Plato;
import com.tfg.vitalfit.service.entity.dto.DietaConPlatosDTO;
import com.tfg.vitalfit.service.entity.dto.GenerarDietaDTO;
import com.tfg.vitalfit.service.repository.DietasRepository;
import com.tfg.vitalfit.service.repository.PlatosRepository;
import com.tfg.vitalfit.service.service.DietasService;
import com.tfg.vitalfit.service.service.PlatosService;
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
public class DietaServiceTest {
    @Mock
    private DietasRepository repository;

    @Mock
    private PlatosRepository platosRepository;

    @Mock
    private PlatosService platosService;

    @InjectMocks
    private DietasService service;

    @Test
    void guardarDieta_devuelveOk() {
        Dieta dieta = new Dieta();
        dieta.setDiaSemana("Lunes");

        Plato plato1 = new Plato();
        Plato plato2 = new Plato();
        List<Plato> platos = Arrays.asList(plato1, plato2);

        Paciente paciente = new Paciente();
        dieta.setPaciente(paciente);

        GenerarDietaDTO dto = new GenerarDietaDTO();
        dto.setDieta(dieta);
        dto.setPlatos(platos);
        dto.setPaciente(paciente);

        Mockito.when(repository.save(dieta)).thenReturn(dieta);

        GenericResponse response = service.guardarDieta(dto);

        assertEquals(1, response.getRpta());
        assertEquals("Dieta guardada correctamente", response.getMessage());
        Mockito.verify(platosService).guardarPlatos(platos);
    }

    @Test
    void obtenerDieta_existente_devuelveDTO() {
        Dieta dieta = new Dieta();
        dieta.setPlatos(Arrays.asList(new Plato(), new Plato()));
        Mockito.when(repository.findByPacienteAndDiaSemana("99999999X", "Lunes"))
                .thenReturn(Optional.of(dieta));

        DietaConPlatosDTO result = service.obtenerDietaPorPacienteYDia("99999999X", "Lunes");



        assertNotNull(result);
        assertEquals(dieta, result.getDieta());
        assertEquals(2, getSize(result.getPlatos()));
    }

    public int getSize(Iterable<Plato> platos) {
        int count = 0;
        for (Plato p : platos) {
            count++;
        }
        return count;
    }

    @Test
    void obtenerDieta_noExistente_devuelveNull() {
        Mockito.when(repository.findByPacienteAndDiaSemana("99999999X", "Martes"))
                .thenReturn(Optional.empty());

        DietaConPlatosDTO result = service.obtenerDietaPorPacienteYDia("99999999X", "Martes");

        assertNull(result);
    }

    @Test
    @DisplayName("Actualizar dieta existentes y guardar platos correctamente")
    void updateDieta_existente_guardaPlatosYDevuelveOk() {
        Dieta dieta = new Dieta();
        dieta.setIdDieta(1L);

        Plato p1 = new Plato();
        Plato p2 = new Plato();
        List<Plato> nuevosPlatos = Arrays.asList(p1, p2);

        GenerarDietaDTO dto = new GenerarDietaDTO();
        dto.setDieta(dieta);
        dto.setPlatos(nuevosPlatos);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(dieta));

        GenericResponse response = service.updateDieta(dto);

        assertEquals(1, response.getRpta());
        Mockito.verify(platosRepository, Mockito.times(2)).save(Mockito.any(Plato.class));
    }

    @Test
    void updateDieta_noExistente_devuelveWarning() {
        Dieta dieta = new Dieta();
        dieta.setIdDieta(999L);

        GenerarDietaDTO dto = new GenerarDietaDTO();
        dto.setDieta(dieta);

        Mockito.when(repository.findById(999L)).thenReturn(Optional.empty());

        GenericResponse response = service.updateDieta(dto);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: No se ha encontrado la dieta con ese id", response.getMessage());
    }
}

