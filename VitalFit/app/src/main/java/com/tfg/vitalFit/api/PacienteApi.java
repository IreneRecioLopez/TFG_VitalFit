package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Paciente;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PacienteApi {
    //RUTA DEL CONTROLADOR PACIENTE
    String base = "api/paciente";

    //RUTA DEL CONTROLADOR PACIENTE + RUTA MÉTODO
    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Paciente>> login(@Field("dni") String dni, @Field("password") String password);

    @FormUrlEncoded
    @POST(base)
    Call<GenericResponse<Paciente>> guardarPaciente(@Body Paciente p);
}
