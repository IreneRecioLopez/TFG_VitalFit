package com.tfg.vitalfit.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Fecha {

    public static String registrarFecha(String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        formatoEntrada.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        formatoSalida.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));

        try {
            Log.e("FormatoFecha", fecha);
            Date date = formatoEntrada.parse(fecha);
            return formatoSalida.format(date);
        } catch (ParseException e) {
            Log.e("ErrorFecha", "Formato incorrecto: " + fecha);
            return null;
        }
    }

    public static String obtenerFecha(String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        formatoEntrada.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        formatoSalida.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));

        try {
            Log.e("FormatoFecha", fecha);
            Date date = formatoEntrada.parse(fecha);

            // Sumar un día
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date nuevaFecha = calendar.getTime();

            return formatoSalida.format(nuevaFecha);
        } catch (ParseException e) {
            Log.e("ErrorFecha", "Formato incorrecto: " + fecha);
            return null;
        }
    }

    public static String obtenerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        return sdf.format(new Date());
    }
}
