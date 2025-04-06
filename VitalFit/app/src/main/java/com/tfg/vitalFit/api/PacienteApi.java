package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Paciente;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PacienteApi {
    //RUTA DEL CONTROLADOR PACIENTE
    String base = "api/paciente";

    //RUTA DEL CONTROLADOR PACIENTE + RUTA MÉTODO
    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Paciente>> login(@Field("dni") String dni, @Field("password") String password);

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<Paciente>> guardarPaciente(@Body Paciente p);

    @PUT(base + "/{dni}/hospital")
    Call<GenericResponse<Void>> asociarPacienteHospital(@Path("dni") String dniPaciente, @Body Hospital hospital);

    @Headers("Content-Type: text/plain")
    @PUT(base + "/{dni}/password")
    Call<GenericResponse<Void>> actualizarPassword(@Path("dni")String dni, @Body RequestBody password);
}
