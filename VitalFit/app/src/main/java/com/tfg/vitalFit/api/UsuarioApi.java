package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.GenericResponse;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Usuario;


import java.util.List;

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
import retrofit2.http.Query;


public interface UsuarioApi {
    //RUTA DEL CONTROLADOR USUARIO
    String base = "api/usuario";

    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Usuario>> login(@Field("dni") String dni, @Field("password") String password);

    @POST(base + "/save")
    Call<GenericResponse<Usuario>> guardarUsuario(@Body Usuario u);

    @GET(base + "/{dni}")
    Call<Usuario> getUsuarioByDni(@Path("dni") String dni);

    @GET(base + "/medico/hospital/{idHospital}")
    Call<List<Usuario>> getMedicosByHospital(@Path("idHospital") Long idHospital);

    @GET(base + "/nutricionista/hospital/{idHospital}")
    Call<List<Usuario>> getNutricionistasByHospital(@Path("idHospital") Long idHospital);

    @PUT(base + "/{dni}/hospital")
    Call<GenericResponse<Void>> asociarUsuarioHospital(@Path("dni") String dniUsuario, @Body Hospital hospital);

    @PUT(base + "/paciente/{dni}/medico")
    Call<GenericResponse<Void>> asociarPacienteMedico(@Path("dni") String dniPaciente, @Body Usuario medico);

    @PUT(base + "/paciente/{dni}/nutricionista")
    Call<GenericResponse<Void>> asociarPacienteNutricionista(@Path("dni") String dniPaciente, @Body Usuario nutricionista);

    @Headers("Content-Type: text/plain")
    @PUT(base + "/{dni}/password")
    Call<GenericResponse<Void>> actualizarPassword(@Path("dni")String dni, @Body RequestBody password);

    @PUT(base + "/update")
    Call<GenericResponse<Void>> actualizarUsuario(@Body Usuario u);
}
