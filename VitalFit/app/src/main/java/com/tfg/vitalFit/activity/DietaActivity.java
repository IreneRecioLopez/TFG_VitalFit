package com.tfg.vitalfit.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Dieta;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.entity.service.Plato;
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

public class DietaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnEditarDieta, btnGuardarDieta;
    private AutoCompleteTextView dropdownDiasSemana;
    private TextView txtNoHayDieta;
    private TableLayout tableLayoutDieta, tableLayoutEdicion;
    private LinearLayout layoutVerDieta, layoutEditarDieta;
    private DietasViewModel dietasViewModel;
    private DietaConPlatosDTO dietaConPlatos;
    private final List<String> ordenTramos = Arrays.asList("Desayuno", "Media mañana", "Comida", "Merienda", "Cena");
    private final Map<String, Plato> platosEditables = new HashMap<>();
    private Usuario usuario, paciente;
    private String diaSemana;
    private Boolean esNueva = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta);
        this.obtenerDatosUsuario();
        this.obtenerDatosPaciente();
        this.initViewModel();
        this.init();
    }

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

        // Adapter para los días de la semana
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

    private void mostrarTablaDieta(List<Plato> platos) {
        tableLayoutDieta.removeAllViews();

        // Agrupar platos por tramo
        Map<String, List<Plato>> platosPorTramo = new LinkedHashMap<>();
        for (String tramo : ordenTramos) {
            platosPorTramo.put(tramo, new ArrayList<>());
        }

        for (Plato p : platos) {
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
            for (Plato p : platosPorTramo.get(tramo)) {
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
            platosText.setMaxWidth(800);
            platosText.setEllipsize(null);
            platosText.setSingleLine(false);
            platosText.setHorizontallyScrolling(false);

            row.addView(tramoText);
            row.addView(platosText);

            tableLayoutDieta.addView(row);
        }
    }

    private void mostrarTablaEdicion(List<Plato> platos) {
        tableLayoutEdicion.removeAllViews();
        platosEditables.clear();

        // Mapear platos por tramo (inicializa vacíos si no hay)
        Map<String, Plato> platosPorTramo = new HashMap<>();
        for (Plato p : platos) {
            platosPorTramo.put(p.getTramoDia(), p);
        }

        for (String tramo : ordenTramos) {
            TableRow row = new TableRow(this);

            TextView tramoView = new TextView(this);
            tramoView.setText(tramo);
            tramoView.setPadding(16, 8, 16, 8);

            // Obtener o crear plato vacío
            Plato plato = platosPorTramo.getOrDefault(tramo, new Plato());
            plato.setTramoDia(tramo);
            platosEditables.put(tramo, plato);

            LinearLayout inputsLayout = new LinearLayout(this);
            inputsLayout.setOrientation(LinearLayout.VERTICAL);

            inputsLayout.addView(crearInput(plato, "1º", "primerPlato"));
            inputsLayout.addView(crearInput(plato, "2º", "segundoPlato"));
            inputsLayout.addView(crearInput(plato, "Postre", "postre"));

            row.addView(tramoView);
            row.addView(inputsLayout);
            tableLayoutEdicion.addView(row);


            View separator = new View(this);
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    2
            );
            params.setMargins(0, 8, 0, 8);
            separator.setLayoutParams(params);
            separator.setBackgroundColor(Color.LTGRAY);

            tableLayoutEdicion.addView(separator);
        }
    }

    private View crearInput(Plato plato, String hint, String campo) {
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

        return editText;
    }

    private String obtenerCampo(Plato plato, String campo) {
        switch (campo) {
            case "primerPlato": return plato.getPrimerPlato() != null ? plato.getPrimerPlato() : "";
            case "segundoPlato": return plato.getSegundoPlato() != null ? plato.getSegundoPlato() : "";
            case "postre": return plato.getPostre() != null ? plato.getPostre() : "";
            default: return "";
        }
    }

    private void setCampo(Plato plato, String campo, String valor) {
        switch (campo) {
            case "primerPlato": plato.setPrimerPlato(valor); break;
            case "segundoPlato": plato.setSegundoPlato(valor); break;
            case "postre": plato.setPostre(valor); break;
        }
    }

    private void guardarDieta(boolean esNueva, Paciente paciente, String diaSemana) {
        Dieta dieta = new Dieta();
        if (!esNueva && dietaConPlatos != null && dietaConPlatos.getDieta() != null) {
            dieta.setIdDieta(dietaConPlatos.getDieta().getIdDieta());
        }
        dieta.setDiaSemana(diaSemana);
        dieta.setPaciente(paciente);

        List<Plato> listaPlatos = new ArrayList<>(platosEditables.values());
        for (Plato p : listaPlatos) {
            p.setDieta(dieta); // relación inversa
        }

        GenerarDietaDTO dto = new GenerarDietaDTO();
        dto.setDieta(dieta);
        dto.setPaciente(paciente);
        dto.setPlatos(new ArrayList<>(listaPlatos));

        if (esNueva) {
            dietasViewModel.save(dto).observe(this, response -> {
                if (response != null && response.getRpta() == 1) {
                    ToastMessage.Correcto(this, "Dieta Guardada");
                    layoutVerDieta.setVisibility(View.VISIBLE);
                    layoutEditarDieta.setVisibility(View.GONE);
                    obtenerDieta();
                } else {
                    ToastMessage.Invalido(this, "Error al guardar la dieta");
                }
            });
        } else {
            dietasViewModel.updateDieta(dto).observe(this, response -> {
                if (response != null && response.getRpta() == 1) {
                    ToastMessage.Correcto(this, "Dieta Actualizada");
                    layoutVerDieta.setVisibility(View.VISIBLE);
                    layoutEditarDieta.setVisibility(View.GONE);
                    obtenerDieta();
                } else {
                    ToastMessage.Invalido(this, "Error al actualizar la dieta");
                }
            });
        }
    }

    private void obtenerDatosUsuario(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        if(jsonUsuario != null) {
            usuario = new Gson().fromJson(jsonUsuario, Usuario.class);
        }
    }

    private void obtenerDatosPaciente(){
        paciente = (Usuario) getIntent().getSerializableExtra("paciente");
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