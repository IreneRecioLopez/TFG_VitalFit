package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Alergias;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AlergiasApi {
    //RUTA DEL CONTROLADOR ALERGIAS
    String base = "api/alergias";

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<Alergias>> guardarAlergia(@Body Alergias a);

    @DELETE(base + "/delete/{idAlergia}")
    Call<GenericResponse<Void>> eliminarAlergia(@Path("idAlergia") Long idAlergia);
}
