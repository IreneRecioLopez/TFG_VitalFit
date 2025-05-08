package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Observaciones;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ObservacionesApi {
    //RUTA DEL CONTROLADOR PESOS
    String base = "api/observaciones";

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<Observaciones>> guardarObservacion(@Body Observaciones o);

    @DELETE(base + "/delete/{idObservacion}")
    Call<GenericResponse<Void>> eliminarObservacion(@Path("idObservacion") Long idObservacion);
}
