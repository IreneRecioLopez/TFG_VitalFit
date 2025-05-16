package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Peso;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PesosApi {
    //RUTA DEL CONTROLADOR PESOS
    String base = "api/pesos";

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<Peso>> guardarPeso(@Body Peso p);

    @GET(base + "/hoy")
    Call<Peso> getPesoHoy(@Query("dni") String dni);
}
