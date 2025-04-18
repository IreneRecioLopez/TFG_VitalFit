package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Alergias;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AlergiasApi {
    //RUTA DEL CONTROLADOR PESOS
    String base = "api/alergias";

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<Alergias>> guardarAlergia(@Body Alergias a);
}
