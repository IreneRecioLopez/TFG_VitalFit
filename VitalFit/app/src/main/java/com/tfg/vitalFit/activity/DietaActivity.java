package com.tfg.vitalfit.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Dieta;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.entity.service.Platos;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.entity.service.dto.DietaConPlatosDTO;
import com.tfg.vitalfit.entity.service.dto.GenerarDietaDTO;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.DietasViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DietaActivity extends AppCompatActivity {

    private RecyclerView recyclerPlatos;
    private Toolbar toolbar;
    private Button btnEditarDieta, btnGuardarDieta;
    private AutoCompleteTextView dropdownDiasSemana;
    private TextView txtNoHayDieta;
    private TableLayout tableLayoutDieta, tableLayoutEdicion;
    private LinearLayout layoutVerDieta, layoutEditarDieta;
    //private PlatosAdapter platosAdapter;
    private DietasViewModel dietasViewModel;
    private DietaConPlatosDTO dietaConPlatos;
    private final List<String> ordenTramos = Arrays.asList("Desayuno", "Media mañana", "Comida", "Merienda", "Cena");
    private final Map<String, Platos> platosEditables = new HashMap<>();
    private Usuario usuario, paciente;
    private String diaSemana;
    private Boolean esNueva = true;

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
        btnGuardarDieta = findViewById(R.id.btnGuardarDieta);
        dropdownDiasSemana = findViewById(R.id.dropdownDiaSemana);
        txtNoHayDieta = findViewById(R.id.txtNoHayDieta);
        tableLayoutDieta = findViewById(R.id.tableLayoutDieta);
        tableLayoutEdicion = findViewById(R.id.tableLayoutEdicion);
        layoutVerDieta = findViewById(R.id.layoutTablaDieta);
        layoutEditarDieta = findViewById(R.id.layoutEditarDieta);


        setSupportActionBar(toolbar);

        // Habilitar el botón de regreso en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        btnEditarDieta.setOnClickListener(v -> {
            layoutVerDieta.setVisibility(View.GONE);
            layoutEditarDieta.setVisibility(View.VISIBLE);
            tableLayoutEdicion.setVisibility(View.VISIBLE);
            obtenerDietaEdicion();
        });

        btnGuardarDieta.setOnClickListener(v -> {
            guardarDieta(esNueva, paciente.getPaciente(), diaSemana);
            obtenerDieta();
            layoutVerDieta.setVisibility(View.VISIBLE);
            layoutEditarDieta.setVisibility(View.GONE);
        });

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

    private void obtenerDietaEdicion(){
        dietasViewModel.obtenerDietaPorPacienteYDia(paciente.getDni(), diaSemana).observe(this, dieta -> {
            if (dieta != null && !dieta.getPlatos().isEmpty()) {
                dietaConPlatos = dieta;
                esNueva = false;
                mostrarTablaEdicion(dieta.getPlatos());
            } else {
                esNueva = true;
                mostrarTablaEdicion(new ArrayList<>());
            }
        });
    }

    private void mostrarTablaDieta(List<Platos> platos) {
        tableLayoutDieta.removeAllViews();

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

    private void mostrarTablaEdicion(List<Platos> platos) {
        tableLayoutEdicion.removeAllViews();
        platosEditables.clear();

        // Mapear platos por tramo (inicializa vacíos si no hay)
        Map<String, Platos> platosPorTramo = new HashMap<>();
        for (Platos p : platos) {
            platosPorTramo.put(p.getTramoDia(), p);
        }

        for (String tramo : ordenTramos) {
            TableRow row = new TableRow(this);

            TextView tramoView = new TextView(this);
            tramoView.setText(tramo);
            tramoView.setPadding(16, 8, 16, 8);

            // Obtener o crear plato vacío
            Platos plato = platosPorTramo.getOrDefault(tramo, new Platos());
            plato.setTramoDia(tramo); // asegurar tramo asignado
            platosEditables.put(tramo, plato);

            LinearLayout inputsLayout = new LinearLayout(this);
            inputsLayout.setOrientation(LinearLayout.VERTICAL);

            inputsLayout.addView(crearInput(plato, "1º", "primerPlato"));
            inputsLayout.addView(crearInput(plato, "2º", "segundoPlato"));
            inputsLayout.addView(crearInput(plato, "Postre", "postre"));

            row.addView(tramoView);
            row.addView(inputsLayout);
            tableLayoutEdicion.addView(row);

            // Agregar separador (barra)
            View separator = new View(this);
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    2 // altura en píxeles (puedes ajustar)
            );
            params.setMargins(0, 8, 0, 8); // márgenes si deseas espacio
            separator.setLayoutParams(params);
            separator.setBackgroundColor(Color.LTGRAY); // o usa tu color deseado

            tableLayoutEdicion.addView(separator);
        }
    }

    private View crearInput(Platos plato, String hint, String campo) {
        //TextInputLayout inputLayout = new TextInputLayout(this);
        TextInputEditText editText = new TextInputEditText(this);
        editText.setHint(hint);
        editText.setBackground(null);
        editText.setText(obtenerCampo(plato, campo));

        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                setCampo(plato, campo, s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        //inputLayout.addView(editText);
        //return inputLayout;
        return editText;
    }

    private String obtenerCampo(Platos plato, String campo) {
        switch (campo) {
            case "primerPlato": return plato.getPrimerPlato() != null ? plato.getPrimerPlato() : "";
            case "segundoPlato": return plato.getSegundoPlato() != null ? plato.getSegundoPlato() : "";
            case "postre": return plato.getPostre() != null ? plato.getPostre() : "";
            default: return "";
        }
    }

    private void setCampo(Platos plato, String campo, String valor) {
        switch (campo) {
            case "primerPlato": plato.setPrimerPlato(valor); break;
            case "segundoPlato": plato.setSegundoPlato(valor); break;
            case "postre": plato.setPostre(valor); break;
        }
    }

    private void guardarDieta(boolean esNueva, Paciente paciente, String diaSemana) {
        if (esNueva) {
            Dieta dieta = new Dieta();
            dieta.setDiaSemana(diaSemana);
            dieta.setPaciente(paciente);

            List<Platos> listaPlatos = new ArrayList<>(platosEditables.values());
            for (Platos p : listaPlatos) {
                p.setDieta(dieta); // relación inversa
            }

            GenerarDietaDTO dto = new GenerarDietaDTO();
            dto.setDieta(dieta);
            dto.setPaciente(paciente);
            dto.setPlatos(new ArrayList<>(listaPlatos));
            dietasViewModel.save(dto).observe(this, response ->{
                if(response.getRpta() == 1){
                    ToastMessage.Correcto(this, "Dieta Guardada");
                }
            });
        } else {
            Dieta dieta = new Dieta();
            dieta.setIdDieta(dietaConPlatos.getDieta().getIdDieta());
            dieta.setDiaSemana(diaSemana);
            dieta.setPaciente(paciente);

            List<Platos> listaPlatos = new ArrayList<>(platosEditables.values());
            for (Platos p : listaPlatos) {
                p.setDieta(dieta); // relación inversa
            }

            GenerarDietaDTO dto = new GenerarDietaDTO();
            dto.setDieta(dieta);
            dto.setPaciente(paciente);
            dto.setPlatos(new ArrayList<>(listaPlatos));
            dietasViewModel.updateDieta(dto).observe(this, response ->{
                if(response.getRpta() == 1){
                    ToastMessage.Correcto(this, "Dieta Actualizada");
                }
            });
        }
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