package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Dieta;
import com.tfg.vitalfit.entity.service.dto.DietaConPlatosDTO;
import com.tfg.vitalfit.entity.service.dto.GenerarDietaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DietasApi {
    //RUTA DEL CONTROLADOR DIETAS
    String base = "api/dietas";

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<GenerarDietaDTO>> guardarDieta(@Body GenerarDietaDTO dto);

   /* @GET(base + "/misDietas/{dni}")
    Call<GenericResponse<List<DietaConPlatosDTO>>> obtenerDietasPorPaciente(@Path("dni") String dni);*/

    @GET(base + "/{dni}/{diaSemana}")
    Call<DietaConPlatosDTO> getDietaPorPacienteYDia(@Path("dni") String dniPaciente, @Path("diaSemana") String diaSemana);

    @PUT(base + "/update")
    Call<GenericResponse<GenerarDietaDTO>> updateDieta(@Body GenerarDietaDTO dto);
}
