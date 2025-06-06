package com.tfg.vitalfit.utils;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.api.ConfigApi;
import com.tfg.vitalfit.api.ConsejosApi;
import com.tfg.vitalfit.api.PesosApi;
import com.tfg.vitalfit.entity.service.Consejo;
import com.tfg.vitalfit.entity.service.Peso;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionDiariaReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean activada = prefs.getBoolean("notificacion_activada", false);
        String dni = prefs.getString("dni", null);

        if (!activada || dni == null) return;

        PesosApi pesosApi = ConfigApi.getPesosApi();
        ConsejosApi consejosApi = ConfigApi.getConsejosApi();

        pesosApi.getPesoUltimo(dni).enqueue(new Callback<Peso>() {
            @Override
            public void onResponse(Call<Peso> call, Response<Peso> response) {
                if (response.body() == null || !Fecha.registrarFecha(Fecha.obtenerFecha(response.body().getFecha().toString())).equals(Fecha.obtenerFechaActual())) {
                    mostrarNotificacion(context, "Peso", "No olvides registrar tu peso hoy.");
                }
            }

            @Override
            public void onFailure(Call<Peso> call, Throwable t) {
            }
        });

        consejosApi.getConsejosNoLeidos(dni).enqueue(new Callback<List<Consejo>>() {
            @Override
            public void onResponse(Call<List<Consejo>> call, Response<List<Consejo>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    mostrarNotificacion(context, "Consejo nuevo", "Tienes consejos sin leer.");
                }
            }

            @Override
            public void onFailure(Call<List<Consejo>> call, Throwable t) {
            }
        });
    }

    private void mostrarNotificacion(Context context, String titulo, String mensaje) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "canal_salud")
                .setSmallIcon(R.drawable.ic_mensaje)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                manager.notify((int) System.currentTimeMillis(), builder.build());
            }
        } else {
            manager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }
}

