package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Consejo;
import com.tfg.vitalfit.entity.service.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ConsejosApi {
    //RUTA DEL CONTROLADOR CONSEJOS
    String base = "api/consejo";

    @GET(base + "/paciente/{dni}")
    Call<List<Consejo>> obtenerConsejosPorPaciente(@Path("dni") String dni);

    @PUT(base + "/leido/{idConsejo}")
    Call<GenericResponse<Void>> marcarComoLeido(@Path("idConsejo")Long idConsejo);
}
