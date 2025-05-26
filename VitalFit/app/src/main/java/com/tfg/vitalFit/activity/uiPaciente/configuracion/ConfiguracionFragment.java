package com.tfg.vitalfit.activity.uiPaciente.configuracion;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.databinding.FragmentConfiguracionBinding;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.NotificacionDiariaReceiver;
import com.tfg.vitalfit.viewModel.ConsejosViewModel;

import java.util.Calendar;
import java.util.Date;

public class ConfiguracionFragment extends Fragment {

    private FragmentConfiguracionBinding binding;

    private Switch switchNotificacion;

    private Usuario usuario;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfiguracionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        crearCanalNotificacion();
        pedirPermisoNotificaciones();

        switchNotificacion = root.findViewById(R.id.switchNotificacion);
        switchNotificacion.setChecked(estaNotificacionActivada());

        if (switchNotificacion.isChecked()) {
            activarNotificacionDiaria();
        }

        switchNotificacion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            guardarEstadoNotificacion(isChecked);
            if (isChecked) {
                activarNotificacionDiaria();
            }
            else {
                desactivarNotificacionDiaria();
            }
        });

        return root;
    }

    private void activarNotificacionDiaria() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                // Abre los ajustes del sistema para conceder permiso manualmente
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return;
            }
        }

        Intent intent = new Intent(requireContext(), NotificacionDiariaReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

        //Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.SECOND, 60);
        //long horaInicio = calendar.getTimeInMillis();
        long horaInicio = obtenerHoraProximaNotificacion();

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, horaInicio, pendingIntent);
        Log.d("Alarma", "Alarma programada para: " + new Date(horaInicio));

    }

    private void desactivarNotificacionDiaria() {
        Intent intent = new Intent(requireContext(), NotificacionDiariaReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    private long obtenerHoraProximaNotificacion() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return calendar.getTimeInMillis();
    }

    private void crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("canal_salud", "Notificaciones Salud", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = requireContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void guardarEstadoNotificacion(boolean activado) {
        SharedPreferences prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("notificacion_activada", activado).apply();
    }

    private boolean estaNotificacionActivada() {
        SharedPreferences prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("notificacion_activada", false);
    }

    private void pedirPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1001);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permisos", "Permiso de notificaciones concedido");
            } else {
                Toast.makeText(getContext(), "Permiso de notificaciones denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void obtenerDatosUsuario(View view){
        //Obtener los datos del usuario
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        Log.d("UsuarioRecibido", new Gson().toJson(usuario));

        if(jsonUsuario != null){
            usuario = new Gson().fromJson(jsonUsuario, Usuario.class);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}