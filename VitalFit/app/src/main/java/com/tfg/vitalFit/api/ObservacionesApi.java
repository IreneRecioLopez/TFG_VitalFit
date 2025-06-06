package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Observacion;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ObservacionesApi {
    //RUTA DEL CONTROLADOR PESOS
    String base = "api/observaciones";

    @POST(base + "/save")
    Call<GenericResponse<Observacion>> guardarObservacion(@Body Observacion o);

    @DELETE(base + "/delete/{idObservacion}")
    Call<GenericResponse<Void>> eliminarObservacion(@Path("idObservacion") Long idObservacion);
}
