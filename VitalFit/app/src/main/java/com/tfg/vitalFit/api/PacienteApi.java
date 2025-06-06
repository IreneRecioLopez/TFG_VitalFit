package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Paciente;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PacienteApi {
    //RUTA DEL CONTROLADOR PACIENTE
    String base = "api/paciente";

    @POST(base + "/save")
    Call<GenericResponse<Paciente>> guardarPaciente(@Body Paciente p);

    @GET(base + "/{dni}")
    Call<Paciente> pacienteByDNI(@Path("dni") String dni);

    @PUT(base + "/update")
    Call<GenericResponse<Void>> actualizarPaciente(@Body Paciente p);
}
