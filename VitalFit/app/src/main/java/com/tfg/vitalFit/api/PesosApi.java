package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.entity.service.Peso;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface PesosApi {
    //RUTA DEL CONTROLADOR PESOS
    String base = "api/pesos";

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<Peso>> guardarPeso(@Body Peso p);

    @PUT(base + "/update")
    Call<GenericResponse<Void>> actualizarPeso(@Body Peso p);

    @GET(base + "/ultimo")
    Call<Peso> getPesoUltimo(@Query("dni") String dni);
}
