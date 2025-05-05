package com.tfg.vitalfit.activity.uiMedico.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.activity.MedicoDatosPacienteActivity;
import com.tfg.vitalfit.activity.NutricionistaDatosPacienteActivity;
import com.tfg.vitalfit.databinding.FragmentHomeMedicoNutricionistaBinding;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private AutoCompleteTextView dropdownPaciente;
    private Button btnContinuar;

    private UsuarioViewModel usuarioViewModel;
    private Usuario usuario;
    private String paciente;
    private List<String> nombreCompletosPacientes, dniPacientes;

    private FragmentHomeMedicoNutricionistaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        initViewModel();

        binding = FragmentHomeMedicoNutricionistaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        obtenerDatosUsuario(root);

        dropdownPaciente = binding.dropdownPaciente;

        if(usuario.getRol().equals("Médico")){
            obtenerPacientesPorMedico(usuario.getDni());
        }else if(usuario.getRol().equals("Nutricionista")){
            obtenerPacientesPorNutricionista(usuario.getDni());
        }

        dropdownPaciente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                paciente = s.toString();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnContinuar = root.findViewById(R.id.btnContinuarMedico);

        btnContinuar.setOnClickListener(v -> {
            int index = nombreCompletosPacientes.indexOf(paciente);
            if(index != -1){
                String dniSeleccionado = dniPacientes.get(index);
                Usuario pacienteSeleccionado = null;

                if(usuario.getRol().equals("Médico")){
                    for(Usuario p: usuario.getPacientesMedico()){
                        if(p.getDni().equals(dniSeleccionado)){
                            pacienteSeleccionado = p;
                            break;
                        }
                    }
                    if(pacienteSeleccionado != null){
                        Intent intent = new Intent(getContext(), MedicoDatosPacienteActivity.class);
                        intent.putExtra("paciente", pacienteSeleccionado);
                        startActivity(intent);
                        dropdownPaciente.setText("");
                    }
                }else if(usuario.getRol().equals("Nutricionista")){
                    for(Usuario p: usuario.getPacientesNutricionista()){
                        if(p.getDni().equals(dniSeleccionado)){
                            pacienteSeleccionado = p;
                            break;
                        }
                    }
                    if(pacienteSeleccionado != null){
                        Intent intent = new Intent(getContext(), NutricionistaDatosPacienteActivity.class);
                        intent.putExtra("paciente", pacienteSeleccionado);
                        startActivity(intent);
                        dropdownPaciente.setText("");
                    }
                }


            }else{
                ToastMessage.Invalido(getContext(), "Selecciona un paciente");
            }
        });

        return root;
    }

    private void initViewModel(){
        final ViewModelProvider vmp = new ViewModelProvider(this);
        usuarioViewModel = vmp.get(UsuarioViewModel.class);
    }

    private void obtenerDatosUsuario(View view){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        Log.d("UsuarioRecibidoHomeFragment", new Gson().toJson(usuario));

        if(jsonUsuario != null) {
            usuario = new Gson().fromJson(jsonUsuario, Usuario.class);
        }
    }

    private void obtenerPacientesPorMedico(String dniMedico){
        nombreCompletosPacientes = new ArrayList<>();
        dniPacientes = new ArrayList<>();
        /*usuarioViewModel.getPacientesByMedico(dniMedico).observe(getViewLifecycleOwner(), new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                if(usuarios != null){
                    for(Usuario paciente: usuarios){
                        String nombreCompleto = paciente.getNombreCompleto();
                        nombreCompletosPacientes.add(nombreCompleto);
                        dniPacientes.add(paciente.getDni());
                    }
                }
            }
        });*/
        for(Usuario paciente: usuario.getPacientesMedico()){
            String nombreCompleto = paciente.getNombreCompleto();
            nombreCompletosPacientes.add(nombreCompleto);
            dniPacientes.add(paciente.getDni());
        }
        ArrayAdapter<String> arrayPacientes = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombreCompletosPacientes);
        dropdownPaciente.setAdapter(arrayPacientes);
    }

    private void obtenerPacientesPorNutricionista(String dniMedico){
        nombreCompletosPacientes = new ArrayList<>();
        dniPacientes = new ArrayList<>();

        for(Usuario paciente: usuario.getPacientesNutricionista()){
            String nombreCompleto = paciente.getNombreCompleto();
            nombreCompletosPacientes.add(nombreCompleto);
            dniPacientes.add(paciente.getDni());
        }
        ArrayAdapter<String> arrayPacientes = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombreCompletosPacientes);
        dropdownPaciente.setAdapter(arrayPacientes);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}