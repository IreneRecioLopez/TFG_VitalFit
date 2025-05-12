package com.tfg.vitalfit.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.entity.service.Peso;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.Fecha;
import com.tfg.vitalfit.viewModel.PacienteViewModel;
import com.tfg.vitalfit.viewModel.PesosViewModel;

import java.util.ArrayList;
import java.util.List;

public class EstadisticasPacienteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LineChart chart;
    private LineDataSet lineDateSet;
    private PacienteViewModel pacienteViewModel;
    private Usuario paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas_paciente);
        this.initViewModel();
        this.initToolbar();
        obtenerDatosPaciente();
        this.init();

    }

    private void initViewModel(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        pacienteViewModel = vmp.get(PacienteViewModel.class);
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.toolbarEstadisticasPaciente);

        // Habilitar el botón de regreso en el Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void init(){
        chart = findViewById(R.id.lineChart);

        pacienteViewModel.pacienteByDNI(paciente.getDni()).observe(this, response -> {
            if(response != null){
                mostrarGraficaPesos(response);
            }
        });

    }

    private void mostrarGraficaPesos(Paciente p){
        List<Entry> entries = new ArrayList<>();
        List<String> fechas = new ArrayList<>();

        List<Peso> listaPesos = p.getPesos();

        int index = 0;
        for (Peso peso : listaPesos) {
            entries.add(new Entry(index, peso.getPeso().floatValue()));
            fechas.add(Fecha.obtenerFecha(peso.getFecha())); 
            index++;
        }

        LineDataSet dataSet = new LineDataSet(entries, "Peso (kg)");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        chart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int i = (int) value;
                return (i >= 0 && i < fechas.size()) ? fechas.get(i) : "";
            }
        });

        chart.getXAxis().setGranularity(1f); // evita valores intermedios
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getDescription().setText("Progreso de peso");
        chart.animateX(1000);
        chart.invalidate(); // refresca
    }

    private void obtenerDatosPaciente(){
        paciente = (Usuario) getIntent().getSerializableExtra("paciente");
        Log.d("Paciente recibido", paciente.toString());
    }

    // Capturar el clic en el botón de regreso
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) { // Este es el ID del botón de navegación
            onBackPressed(); // Regresa a la pantalla anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}