package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Alergia;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AlergiasApi {
    //RUTA DEL CONTROLADOR ALERGIAS
    String base = "api/alergias";

    @POST(base + "/save")
    Call<GenericResponse<Alergia>> guardarAlergia(@Body Alergia a);

    @DELETE(base + "/delete/{idAlergia}")
    Call<GenericResponse<Void>> eliminarAlergia(@Path("idAlergia") Long idAlergia);
}
