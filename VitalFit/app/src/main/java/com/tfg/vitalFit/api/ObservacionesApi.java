package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Observaciones;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ObservacionesApi {
    //RUTA DEL CONTROLADOR PESOS
    String base = "api/observaciones";

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<Observaciones>> guardarObservacion(@Body Observaciones o);
}
