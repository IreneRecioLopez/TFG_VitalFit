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
import com.tfg.vitalfit.activity.uiMedicoNutricionista.datosPersonales.DatosPersonalesMedicoNutricionistaFragment;
import com.tfg.vitalfit.databinding.ActivityInicioMedicoNutricionistaBinding;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.DateSerializer;
import com.tfg.vitalfit.utils.TimeSerializer;

import java.sql.Date;
import java.sql.Time;

public class InicioMedicoActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioMedicoNutricionistaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInicioMedicoNutricionistaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarInicioMedico.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navViewMedico;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_medico, R.id.nav_datos_personales_medico, R.id.nav_configuracion_medico_nutricionista)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio_medico);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment currentFragment = getSupportFragmentManager()
                    .findFragmentById(R.id.nav_host_fragment_content_inicio_medico)
                    .getChildFragmentManager()
                    .getFragments()
                    .get(0);

            if (currentFragment instanceof DatosPersonalesMedicoNutricionistaFragment) {
                DatosPersonalesMedicoNutricionistaFragment fragment = (DatosPersonalesMedicoNutricionistaFragment) currentFragment;

                if (fragment.estaEnModoEdicion()) {
                    new AlertDialog.Builder(this)
                            .setTitle("¿Descartar cambios?")
                            .setMessage("Tienes cambios sin guardar. ¿Deseas descartarlos?")
                            .setPositiveButton("Sí", (dialog, which) -> {
                                fragment.cancelarEdicion();

                                // ✅ Forzar navegación y actualizar ítem seleccionado
                                navController.navigate(item.getItemId());
                                item.setChecked(true); // <- ACTUALIZA el estado visual del menú
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
        getMenuInflater().inflate(R.menu.inicio_medico_nutricionista, menu);
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
            final View vistaHeader = binding.navViewMedico.getHeaderView(0);
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
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio_medico);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}