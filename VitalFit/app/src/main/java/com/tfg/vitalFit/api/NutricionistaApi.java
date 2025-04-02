package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Nutricionista;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NutricionistaApi {
    //RUTA DEL CONTROLADOR NUTRICIONISTA
    String base = "api/nutricionista";

    //RUTA DEL CONTROLADOR NUTRICIONISTA + RUTA MÉTODO
    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Nutricionista>> login(@Field("dni") String dni, @Field("password") String password);

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<Nutricionista>> guardarNutricionista(@Body Nutricionista n);

    @PUT(base + "/{dni}/hospital")
    Call<GenericResponse<Void>> asociarNutricionistaHospital(@Path("dni") String dniNutricionista, @Body Hospital hospital);
}
