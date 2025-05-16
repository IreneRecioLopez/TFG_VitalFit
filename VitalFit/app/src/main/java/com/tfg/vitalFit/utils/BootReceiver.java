package com.tfg.vitalfit.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
            boolean notificacionActivada = prefs.getBoolean("notificacion_activada", false);

            if (notificacionActivada) {
                // Reprogramar la notificación
                Intent notifIntent = new Intent(context, NotificacionDiariaReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notifIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 9);
                calendar.set(Calendar.MINUTE, 15);
                calendar.set(Calendar.SECOND, 0);
                if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                }

                long horaInicio = calendar.getTimeInMillis();

                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, horaInicio, pendingIntent);
            }
        }
    }
}
