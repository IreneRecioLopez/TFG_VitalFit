import com.tfg.vitalfit.service.entity.Dieta;
import com.tfg.vitalfit.service.entity.Plato;
import com.tfg.vitalfit.service.repository.PlatosRepository;
import com.tfg.vitalfit.service.service.PlatosService;
import com.tfg.vitalfit.service.utils.GenericResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PlatosServiceTest {
    @Mock
    private PlatosRepository repository;

    @InjectMocks
    private PlatosService service;

    @Test
    @DisplayName(("Devuelve error al guardar un plato sin una dieta asignada"))
    void guardarPlatos_conDietaNulo_devuelveWarning() {
        Plato plato = new Plato();
        plato.setDieta(null);

        List<Plato> platos = new ArrayList<>();
        platos.add(plato);

        GenericResponse response = service.guardarPlatos(platos);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: no se ha asignado una dieta a uno o más platos.", response.getMessage());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName(("Devuelve ok al guardar unos platos con una dieta asignado"))
    void guardarPlatos_conDietaValida_devuelveOk() {
        Plato plato = new Plato();
        plato.setDieta(new Dieta());

        List<Plato> platos = new ArrayList<>();
        platos.add(plato);

        Mockito.when(repository.saveAll(platos)).thenReturn(platos);

        GenericResponse response = service.guardarPlatos(platos);

        assertEquals(1, response.getRpta());
        assertEquals("Platos registrados correctamente", response.getMessage());
        assertNotNull(response.getBody());
    }
}
