package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Consejo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConsejosApi {
    //RUTA DEL CONTROLADOR CONSEJOS
    String base = "api/consejo";

    @POST(base + "/save")
    Call<GenericResponse<Consejo>> guardarConsejo(@Body Consejo c);

    @GET(base + "/paciente/{dni}")
    Call<List<Consejo>> obtenerConsejosPorPaciente(@Path("dni") String dni);

    @GET(base + "/nutricionista/{dni}/paciente")
    Call<List<Consejo>> obtenerConsejosPorNutricionistaYPaciente(@Path("dni") String dni, @Query("dniPaciente") String dniPaciente);

    @PUT(base + "/leido/{idConsejo}")
    Call<GenericResponse<Void>> marcarComoLeido(@Path("idConsejo")Long idConsejo);

    @GET(base + "/noLeidos")
    Call<List<Consejo>> getConsejosNoLeidos(@Query("dniPaciente") String dni);
}
