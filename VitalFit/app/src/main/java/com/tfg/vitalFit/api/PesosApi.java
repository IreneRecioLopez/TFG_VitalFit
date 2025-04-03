package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Pesos;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PesosApi {
    //RUTA DEL CONTROLADOR PESOS
    String base = "api/pesos";

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<Pesos>> guardarPeso(@Body Pesos p);
}
