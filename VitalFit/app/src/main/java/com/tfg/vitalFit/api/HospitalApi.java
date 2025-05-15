package com.tfg.vitalfit.api;

import com.tfg.vitalfit.entity.service.Hospital;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HospitalApi {
    //RUTA DEL CONTROLADOR HOSPITAL
    String base = "api/hospital";

    @GET(base + "/{idHospital}")
    Call<Hospital> obtenerHospitalPorId(@Path("idHospital") Long idHospital);

    @GET(base + "/provincia/{provincia}")
    Call<List<Hospital>> obtenerHospitalesPorProvincia(@Path("provincia") String provincia);
    
}
