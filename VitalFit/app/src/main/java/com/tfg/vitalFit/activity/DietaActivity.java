package com.tfg.vitalfit.activity;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Platos;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.entity.service.dto.DietaConPlatosDTO;
import com.tfg.vitalfit.viewModel.DietasViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DietaActivity extends AppCompatActivity {

    private RecyclerView recyclerPlatos;
    private Toolbar toolbar;
    private Button btnEditarDieta;
    private AutoCompleteTextView dropdownDiasSemana;
    private TextView txtNoHayDieta;
    private TableLayout tableLayoutDieta;
    private LinearLayout layoutVerDieta;
    //private PlatosAdapter platosAdapter;
    private DietasViewModel dietasViewModel;
    private DietaConPlatosDTO dietaConPlatos;
    private Usuario usuario, paciente;
    private String diaSemana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta);
        //this.initRecycler();
        this.obtenerDatosUsuario();
        this.obtenerDatosPaciente();
        this.initViewModel();
        this.init();
    }

    /*private void initRecycler(){
        recyclerPlatos = findViewById(R.id.recyclerPlatos);
        recyclerPlatos.setLayoutManager(new LinearLayoutManager(this));
    }*/

    private void initViewModel(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.dietasViewModel = vmp.get(DietasViewModel.class);
    }

    private void init(){
        toolbar = findViewById(R.id.toolbarDietaPaciente);
        btnEditarDieta = findViewById(R.id.btnEditarDieta);
        dropdownDiasSemana = findViewById(R.id.dropdownDiaSemana);
        txtNoHayDieta = findViewById(R.id.txtNoHayDieta);
        tableLayoutDieta = findViewById(R.id.tableLayoutDieta);


        setSupportActionBar(toolbar);

        // Habilitar el botón de regreso en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(usuario.getRol().equals("Nutricionista")){
            btnEditarDieta.setVisibility(View.VISIBLE);
        }

        // Adapter para otros tipoos de datos
        String[] diasDeLaSemana = getResources().getStringArray(R.array.diasDeLaSemana);
        ArrayAdapter<String> adapterDiasSemana = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, diasDeLaSemana
        );
        dropdownDiasSemana.setAdapter(adapterDiasSemana);
        dropdownDiasSemana.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                diaSemana = s.toString();
                obtenerDieta();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        /*btnEditarDieta.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditarDietaActivity.class);
            intent.putExtra("pacienteId", usuario.getDni());
            intent.putExtra("diaSemana", diaSemana);
            startActivity(intent);
        });*/
    }

    private void obtenerDieta(){
        dietasViewModel.obtenerDietaPorPacienteYDia(paciente.getDni(), diaSemana).observe(this, dieta -> {
            if (dieta != null && !dieta.getPlatos().isEmpty()) {
                txtNoHayDieta.setVisibility(View.GONE);
                tableLayoutDieta.setVisibility(View.VISIBLE);
                dietaConPlatos = dieta;
                mostrarTablaDieta(dieta.getPlatos());
                if (usuario.getRol().equals("Nutricionista")) {
                    btnEditarDieta.setVisibility(View.VISIBLE);
                    btnEditarDieta.setText("Editar");
                }
            } else {
                tableLayoutDieta.setVisibility(View.GONE);
                txtNoHayDieta.setVisibility(View.VISIBLE);
                if (usuario.getRol().equals("Nutricionista")) {
                    btnEditarDieta.setVisibility(View.VISIBLE);
                    btnEditarDieta.setText("Añadir");
                }
            }
        });
    }

    private void mostrarTablaDieta(List<Platos> platos) {
        tableLayoutDieta.removeAllViews();

        // Orden personalizado de tramos
        List<String> ordenTramos = Arrays.asList("Desayuno", "Media mañana", "Almuerzo", "Merienda", "Cena");

        // Agrupar platos por tramo
        Map<String, List<Platos>> platosPorTramo = new LinkedHashMap<>();
        for (String tramo : ordenTramos) {
            platosPorTramo.put(tramo, new ArrayList<>());
        }

        for (Platos p : platos) {
            if (platosPorTramo.containsKey(p.getTramoDia())) {
                platosPorTramo.get(p.getTramoDia()).add(p);
            } else {
                // En caso de tramo no reconocido
                platosPorTramo.putIfAbsent(p.getTramoDia(), new ArrayList<>());
                platosPorTramo.get(p.getTramoDia()).add(p);
            }
        }

        // Crear filas
        for (String tramo : platosPorTramo.keySet()) {
            TableRow row = new TableRow(this);

            TextView tramoText = new TextView(this);
            tramoText.setText(tramo);
            tramoText.setPadding(16, 8, 16, 8);
            tramoText.setTextSize(16);
            tramoText.setTypeface(null, Typeface.BOLD);
            tramoText.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

            TextView platosText = new TextView(this);
            StringBuilder sb = new StringBuilder();
            for (Platos p : platosPorTramo.get(tramo)) {
                String primerPlato = p.getPrimerPlato() != null ? p.getPrimerPlato() : "";
                String segundoPlato = p.getSegundoPlato() != null ? p.getSegundoPlato() : "";
                String postre = p.getPostre() != null ? p.getPostre() : "";

                sb.append("1º: ").append(primerPlato).append("\n");
                sb.append("2º: ").append(segundoPlato).append("\n");
                sb.append("Postre: ").append(postre).append("\n");
            }

            platosText.setText(sb.toString().trim());
            platosText.setPadding(16, 8, 16, 8);
            platosText.setTextSize(15);
            platosText.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));
            platosText.setMaxWidth(800); // Ajusta este valor según tus necesidades
            platosText.setEllipsize(null);
            platosText.setSingleLine(false);
            platosText.setHorizontallyScrolling(false);

            row.addView(tramoText);
            row.addView(platosText);

            tableLayoutDieta.addView(row);
        }
    }

    // Método para crear celdas con estilo básico
    private TextView crearCelda(String texto, boolean esCabecera) {
        TextView tv = new TextView(this);
        tv.setText(texto != null ? texto : "");
        tv.setPadding(16, 8, 16, 8);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(14);
        tv.setTypeface(null, esCabecera ? Typeface.BOLD : Typeface.NORMAL);
        return tv;
    }


    private void obtenerDatosUsuario(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        Log.d("UsuarioRecibidoHomeFragment", new Gson().toJson(usuario));

        if(jsonUsuario != null) {
            usuario = new Gson().fromJson(jsonUsuario, Usuario.class);
        }
    }

    private void obtenerDatosPaciente(){
        paciente = (Usuario) getIntent().getSerializableExtra("paciente");
        //Log.d("Paciente recibido otros datos", paciente.toString());
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