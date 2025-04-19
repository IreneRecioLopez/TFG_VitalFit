package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Operaciones;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OperacionesApi {
    //RUTA DEL CONTROLADOR PESOS
    String base = "api/operaciones";

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<Operaciones>> guardarOperacion(@Body Operaciones op);
}
