import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.repository.HospitalRepository;
import com.tfg.vitalfit.service.service.HospitalService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class HospitalServiceTest {
    @Mock
    private HospitalRepository repository;

    @InjectMocks
    private HospitalService service;

    @Test
    @DisplayName(("Devuelve error al obtener un hospital que no existe"))
    void getHospital_noExistente_devuelveNull() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        Hospital result = service.getHospitalById(1L);

        assertNull(result);
    }

    @Test
    @DisplayName("Devuelve ok al obtener el un hospital existente")
    void getHospital_Existente_devuelveOk() {
        Hospital hospital = new Hospital();
        hospital.setIdHospital(1L);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(hospital));

        Hospital result = service.getHospitalById(1L);

        assertEquals(hospital, result);
    }

    @Test
    @DisplayName(("Devuelve lista de hospitales de una provincia"))
    void getHospital_byProvincia_devuelveLista() {
        Hospital hospital1 = new Hospital();
        hospital1.setNombre("Hospital 1");
        Hospital hospital2 = new Hospital();
        hospital2.setNombre("Hospital 2");
        List<Hospital> mockList = Arrays.asList(hospital1, hospital2);

        Mockito.when(repository.findByProvincia("Málaga")).thenReturn(mockList);

        List<Hospital> result = service.getHospitalsByProvincia("Málaga");

        assertEquals(2, result.size());
        assertEquals("Hospital 1", result.get(0).getNombre());
        assertEquals("Hospital 2", result.get(1).getNombre());
    }

}
