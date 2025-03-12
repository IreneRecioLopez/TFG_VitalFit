package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Nutricionista;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NutricionistaApi {
    //RUTA DEL CONTROLADOR NUTRICIONISTA
    String base = "api/nutricionista";

    //RUTA DEL CONTROLADOR NUTRICIONISTA + RUTA MÉTODO
    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Nutricionista>> login(@Field("dni") String dni, @Field("password") String password);

    @FormUrlEncoded
    @POST(base)
    Call<GenericResponse<Nutricionista>> guardarNutricionista(@Body Nutricionista n);
}
