package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Medico;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MedicoApi {
    //RUTA DEL CONTROLADOR MEDICO
    String base = "api/medico";

    //RUTA DEL CONTROLADOR MEDICO + RUTA MÉTODO
    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Medico>> login(@Field("dni") String dni, @Field("password") String password);
}
