package com.tfg.vitalfit.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.activity.uiPaciente.datosPersonales.DatosPersonalesPacienteFragment;
import com.tfg.vitalfit.databinding.ActivityInicioBinding;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.DateSerializer;
import com.tfg.vitalfit.utils.TimeSerializer;

import java.sql.Date;
import java.sql.Time;

public class InicioActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarInicio.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_pagina_principal, R.id.nav_datos_personales, R.id.nav_configuracion)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment_content_inicio)
                    .getChildFragmentManager()
                    .getFragments()
                    .get(0);

            if (currentFragment instanceof DatosPersonalesPacienteFragment) {
                DatosPersonalesPacienteFragment fragment = (DatosPersonalesPacienteFragment) currentFragment;

                if (fragment.estaEnModoEdicion()) {
                    new AlertDialog.Builder(this)
                            .setTitle("¿Descartar cambios?")
                            .setMessage("Tienes cambios sin guardar. ¿Deseas descartarlos?")
                            .setPositiveButton("Sí", (dialog, which) -> {
                                fragment.cancelarEdicion();
                                
                                navController.navigate(item.getItemId());
                                item.setChecked(true);
                                drawer.closeDrawers();
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                    return false;
                }
            }

            // Sin edición activa, navegar normalmente
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            if (handled) {
                drawer.closeDrawers();
            }
            return handled;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cerrarSesion){
            this.logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }
    private void loadData(){
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(this);
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        String usuarioJson = preference.getString("UsuarioJson", "");
        if(usuarioJson != null){
            final Usuario u = g.fromJson(usuarioJson, Usuario.class);
            final View vistaHeader = binding.navView.getHeaderView(0);
            final TextView nombreUsuario = vistaHeader.findViewById(R.id.nombreUsuario);
            final TextView tlfUsuario = vistaHeader.findViewById(R.id.tlfUsuario);

            nombreUsuario.setText(u.getNombreCompleto());
            tlfUsuario.setText(u.getTelefono());
        }
    }

    //Método para cerrar sesión
    private void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("UsuarioJson");
        editor.apply();
        this.finish();
        this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}