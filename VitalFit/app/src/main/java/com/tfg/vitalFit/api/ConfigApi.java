package com.tfg.vitalfit.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tfg.vitalfit.entity.service.Alergias;
import com.tfg.vitalfit.utils.DateSerializer;
import com.tfg.vitalfit.utils.TimeSerializer;

import java.sql.Time;
import java.sql.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Para hacer consultas al servicio
public class ConfigApi {
    //conexión al servicio
    public static final String baseUrlP = "http://10.0.2.2:9090";
    private static Retrofit retrofit;
    private static String token = "";

    private static PacienteApi pacienteApi;
    private static UsuarioApi usuarioApi;
    private static NutricionistaApi nutricionistaApi;
    private static HospitalApi hospitalApi;
    private static PesosApi pesosApi;
    private static AlergiasApi alergiasApi;

    static {
        initClient();
    }

    private static void initClient() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrlP)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient())
                .build();
    }

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        loggin.level(HttpLoggingInterceptor.Level.BODY);

        StethoInterceptor stetho = new StethoInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(loggin)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(stetho);

        return builder.build();
    }

    public static void setToken(String value) {
        token = value;
        initClient();
    }

    public static PacienteApi getPacienteApi(){
        if(pacienteApi == null){
            pacienteApi = retrofit.create(PacienteApi.class);
        }
        return pacienteApi;
    }

    public static UsuarioApi getUsuarioApi(){
        if(usuarioApi == null){
            usuarioApi = retrofit.create(UsuarioApi.class);
        }
        return usuarioApi;
    }

    public static NutricionistaApi getNutricionistaApi(){
        if(nutricionistaApi == null){
            nutricionistaApi = retrofit.create(NutricionistaApi.class);
        }
        return nutricionistaApi;
    }

    public static HospitalApi getHospitalApi(){
        if(hospitalApi == null){
            hospitalApi = retrofit.create(HospitalApi.class);
        }
        return hospitalApi;
    }

    public static PesosApi getPesosApi(){
        if(pesosApi == null){
            pesosApi = retrofit.create(PesosApi.class);
        }
        return pesosApi;
    }

    public static AlergiasApi getAlergiasApi(){
        if(alergiasApi == null){
            alergiasApi = retrofit.create(AlergiasApi.class);
        }
        return alergiasApi;
    }

}

