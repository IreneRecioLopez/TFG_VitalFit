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

    //RUTA DEL CONTROLADOR MEDICO + RUTA MÉTODO
    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Usuario>> login(@Field("dni") String dni, @Field("password") String password);

    //@FormUrlEncoded
    @POST(base + "/save")
    Call<GenericResponse<Usuario>> guardarUsuario(@Body Usuario u);

    @GET(base + "/{dni}")
    Call<Usuario> getUsuarioByDni(@Path("dni") String dni);

    @GET(base + "/medico/hospital/{idHospital}")
    Call<List<Usuario>> getMedicosByHospital(@Path("idHospital") Long idHospital);

    @GET(base + "/nutricionista/hospital/{idHospital}")
    Call<List<Usuario>> getNutricionistasByHospital(@Path("idHospital") Long idHospital);

    @GET(base + "/medico/hospital")
    Call<Usuario> getMedicoByNombreCompletoByHospital(@Query("nombreCompleto") String nombreCompleto, @Query("idHospital") Long idHospital);

    @GET(base + "/paciente/nutricionista/{dni}")
    Call<List<Usuario>> getPacientesByNutricionista(@Path("dni") String dni);

    @GET(base + "/paciente/medico/{dni}")
    Call<List<Usuario>> getPacientesByMedico(@Path("dni") String dni);

    @GET(base + "/paciente/nutricionista")
    Call<Usuario> getPacienteByNombreCompletoByNutricionista(@Query("nombreCompleto") String nombreCompleto, @Query("dni") String dni);

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
