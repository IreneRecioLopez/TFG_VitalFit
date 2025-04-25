package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.service.Consejo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConsejosApi {
    //RUTA DEL CONTROLADOR CONSEJOS
    String base = "api/consejo";

    @GET(base + "/paciente/{dni}")
    Call<List<Consejo>> obtenerConsejosPorPaciente(@Path("dni") String dni);
}
