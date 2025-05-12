package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Operacion;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OperacionesApi {
    //RUTA DEL CONTROLADOR PESOS
    String base = "api/operaciones";

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<Operacion>> guardarOperacion(@Body Operacion op);

    @DELETE(base + "/delete/{idOperacion}")
    Call<GenericResponse<Void>> eliminarOperacion(@Path("idOperacion") Long idOperacion);
}
