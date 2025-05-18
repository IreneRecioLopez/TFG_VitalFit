import com.tfg.vitalfit.service.entity.Consejo;
import com.tfg.vitalfit.service.entity.Hospital;
import com.tfg.vitalfit.service.entity.Paciente;
import com.tfg.vitalfit.service.entity.Usuario;
import com.tfg.vitalfit.service.repository.UsuarioRepository;
import com.tfg.vitalfit.service.service.UsuarioService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    @Test
    @DisplayName("Devuelve OK al hacer login con credenciales válidas")
    void login_credencialesValidas_devuelveOk() {
        Usuario usuario = new Usuario();
        usuario.setDni("12345678X");
        usuario.setContrasena("password123");

        when(repository.login("12345678X", "password123"))
                .thenReturn(Optional.of(usuario));

        GenericResponse<Usuario> response = service.login("12345678X", "password123");

        assertEquals(1, response.getRpta());
        assertEquals("Has iniciado sesión correctamente", response.getMessage());
        assertEquals(usuario, response.getBody());
    }

    @Test
    @DisplayName("Devuelve warning al hacer login con credenciales inválidas")
    void login_credencialesInvalidas_devuelveWarning() {
        when(repository.login("00000000X", "wrongpass"))
                .thenReturn(Optional.empty());

        GenericResponse<Usuario> response = service.login("00000000X", "wrongpass");

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos, ese usuario no existe", response.getMessage());
        assertNotNull(response.getBody());
    }


    @Test
    @DisplayName(("Devuelve error al guardar un usuario con dni existente"))
    void guardarUsuario_conDniExistente_devuelveWarning() {
        Usuario usuario = new Usuario();
        usuario.setDni("12345678X");

        when(repository.findById("12345678X"))
                .thenReturn(Optional.of(usuario));

        GenericResponse response = service.guardarUsuario(usuario);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: Ya exite un usuario con el mismo número de DNI.", response.getMessage());
    }

    @Test
    @DisplayName(("Devuelve ok al guardar un usuario con dni no existente"))
    void guardarUsuario_conDniNoExistente_devuelveOk() {
        Usuario usuario = new Usuario();
        usuario.setDni("12345678X");

        when(repository.findById("12345678X")).thenReturn(Optional.empty());
        when(repository.save(usuario)).thenReturn(usuario);

        GenericResponse response = service.guardarUsuario(usuario);

        assertEquals(1, response.getRpta());
        assertEquals("Usuario registrado correctamente", response.getMessage());

        verify(repository).save(usuario);
    }

    @Test
    @DisplayName("Actualiza usuario existente correctamente")
    void actualizarUsuario_existente_devuelveOk() {
        Usuario usuario = new Usuario();
        usuario.setDni("12345678X");

        when(repository.findById("12345678X"))
                .thenReturn(Optional.of(usuario));

        GenericResponse response = service.actualizarUsuario(usuario);

        assertEquals(1, response.getRpta());
        assertEquals("Usuario actualizado correctamente", response.getMessage());
    }

    @Test
    @DisplayName("Devuelve warning al actualizar usuario no existente")
    void actualizarUsuario_noExistente_devuelveWarning() {
        Usuario usuario = new Usuario();
        usuario.setDni("00000000X");

        when(repository.findById("00000000X"))
                .thenReturn(Optional.empty());

        GenericResponse response = service.actualizarUsuario(usuario);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: No se ha encontrado el usuario con ese dni", response.getMessage());
    }

    @Test
    @DisplayName("Devuelve OK al actualizar la contraseña de un usuario existente")
    void actualizarPassword_usuarioExistente_devuelveOk() {
        String dni = "12345678X";
        String nuevaPassword = "nueva123";
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setDni(dni);

        when(repository.findById(dni)).thenReturn(Optional.of(usuarioExistente));

        GenericResponse response = service.actualizarPassword(dni, nuevaPassword);

        assertEquals(1, response.getRpta());
        assertEquals("Contraseña actualizada correctamente", response.getMessage());
        verify(repository).save(usuarioExistente);
        assertEquals(nuevaPassword, usuarioExistente.getContrasena());
    }

    @Test
    @DisplayName("Devuelve warning al intentar actualizar contraseña de un usuario no existente")
    void actualizarPassword_usuarioNoExistente_devuelveWarning() {
        String dni = "00000000X";
        String nuevaPassword = "invalida";

        when(repository.findById(dni)).thenReturn(Optional.empty());

        GenericResponse response = service.actualizarPassword(dni, nuevaPassword);

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: No se ha encontrado el usuario con ese dni", response.getMessage());
        verify(repository, never()).save(any());
    }


    @Test
    @DisplayName(("Devuelve error al obtener un usuario que no existe"))
    void getUsuario_noExistente_devuelveNull() {
        when(repository.findById("99999999X")).thenReturn(Optional.empty());

        Usuario result = service.getUsuarioByDNI("99999999X");

        assertNull(result);
    }

    @Test
    @DisplayName("Devuelve ok al obtener un usuario existente")
    void getUsuario_Existente_devuelveOk() {
        Usuario usuario = new Usuario();
        usuario.setDni("99999999X");
        when(repository.findById("99999999X")).thenReturn(Optional.of(usuario));

        Usuario result = service.getUsuarioByDNI("99999999X");

        assertEquals(usuario, result);
    }

    @Test
    @DisplayName(("Devuelve lista de medicos de un hospital"))
    void getMedicos_byHospital_devuelveLista() {
        Usuario usuario1 = new Usuario();
        usuario1.setDni("11111111X");
        Usuario usuario2 = new Usuario();
        usuario2.setDni("22222222Y");
        List<Usuario> mockList = Arrays.asList(usuario1, usuario2);

        Mockito.when(repository.obtenerMedicosHospital(1L)).thenReturn(mockList);

        List<Usuario> result = service.obtenerMedicosHospital(1L);

        assertEquals(2, result.size());
        assertEquals("11111111X", result.get(0).getDni());
        assertEquals("22222222Y", result.get(1).getDni());
    }

    @Test
    @DisplayName(("Devuelve lista de nutricionistas de un hospital"))
    void getNutricionistas_byHospital_devuelveLista() {
        Usuario usuario1 = new Usuario();
        usuario1.setDni("11111111X");
        Usuario usuario2 = new Usuario();
        usuario2.setDni("22222222Y");
        List<Usuario> mockList = Arrays.asList(usuario1, usuario2);

        Mockito.when(repository.obtenerNutricionistasHospital(1L)).thenReturn(mockList);

        List<Usuario> result = service.obtenerNutricionistasHospital(1L);

        assertEquals(2, result.size());
        assertEquals("11111111X", result.get(0).getDni());
        assertEquals("22222222Y", result.get(1).getDni());
    }

    @Test
    @DisplayName("Devuelve ok al asociar hospital a un usuario existente")
    void asociarUsuarioHospital_existente_devuelveOk() {
        Usuario usuario = new Usuario();
        usuario.setDni("12345678X");
        Hospital hospital = new Hospital();

        when(repository.findById("12345678X")).thenReturn(Optional.of(usuario));

        GenericResponse response = service.asociarUsuarioHospital("12345678X", hospital);

        assertEquals(1, response.getRpta());
        assertEquals("Usuario asociado correctamente al hospital", response.getMessage());
        verify(repository).asociarUsuarioHospital("12345678X", hospital);
    }

    @Test
    @DisplayName("Devuelve warning al intentar asociar un hospital a un usuario inexistente")
    void asociarUsuarioHospital_noExistente_devuelveWarning() {
        when(repository.findById("12345678X")).thenReturn(Optional.empty());

        GenericResponse response = service.asociarUsuarioHospital("12345678X", new Hospital());

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: No se ha encontrado el usuario con ese dni", response.getMessage());
        verify(repository, never()).asociarUsuarioHospital(anyString(), any());
    }

    @Test
    @DisplayName("Devuelve ok al asociar médico a un paciente existente")
    void asociarPacienteMedico_existente_devuelveOk() {
        Usuario paciente = new Usuario();
        paciente.setDni("11111111A");
        Usuario medico = new Usuario();

        when(repository.findById("11111111A")).thenReturn(Optional.of(paciente));

        GenericResponse response = service.asociarPacienteMedico("11111111A", medico);

        assertEquals(1, response.getRpta());
        assertEquals("Paciente asociado correctamente al medico", response.getMessage());
        verify(repository).asociarPacienteMedico("11111111A", medico);
    }

    @Test
    @DisplayName("Devuelve warning al intentar asociar a un paciente inexistente un medico")
    void asociarPacienteMedico_noExistente_devuelveWarning() {
        when(repository.findById("12345678X")).thenReturn(Optional.empty());

        GenericResponse response = service.asociarPacienteMedico("12345678X", new Usuario());

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: No se ha encontrado el paciente con ese dni", response.getMessage());
        verify(repository, never()).asociarPacienteMedico(anyString(), any());
    }

    @Test
    @DisplayName("Devuelve ok al asociar paciente a nutricionista existente")
    void asociarPacienteNutricionista_existente_devuelveOk() {
        Usuario paciente = new Usuario();
        paciente.setDni("11111111A");
        Usuario nutricionista = new Usuario();

        when(repository.findById("11111111A")).thenReturn(Optional.of(paciente));

        GenericResponse response = service.asociarPacienteNutricionista("11111111A", nutricionista);

        assertEquals(1, response.getRpta());
        assertEquals("Paciente asociado correctamente al nutricionista", response.getMessage());
        verify(repository).asociarPacienteNutricionista("11111111A", nutricionista);
    }

    @Test
    @DisplayName("Devuelve warning al intentar asociar a un paciente inexistente un nutricionista")
    void asociarPacienteNutricionista_noExistente_devuelveWarning() {
        when(repository.findById("12345678X")).thenReturn(Optional.empty());

        GenericResponse response = service.asociarPacienteNutricionista("12345678X", new Usuario());

        assertEquals(0, response.getRpta());
        assertEquals("Lo sentimos: No se ha encontrado el paciente con ese dni", response.getMessage());
        verify(repository, never()).asociarPacienteNutricionista(anyString(), any());
    }

}
