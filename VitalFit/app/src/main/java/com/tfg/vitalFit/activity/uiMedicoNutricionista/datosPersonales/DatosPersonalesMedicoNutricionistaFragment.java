package com.tfg.vitalfit.activity.uiMedicoNutricionista.datosPersonales;

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
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.databinding.FragmentDatosPersonalesMedicoNutricionistaBinding;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.HospitalViewModel;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatosPersonalesMedicoNutricionistaFragment extends Fragment {

    private List<View> camposEditables;
    private List<View> camposNoEditables;
    private Button btnEditarDatos;
    private Boolean modoEdicion = false;

    private UsuarioViewModel usuarioViewModel;
    private HospitalViewModel hospitalViewModel;
    private Usuario usuario;

    AutoCompleteTextView dropdownHospital, dropdownProvincia;

    String provincia, hospital;
    List<String> nombresHospitales;
    List<Long> idHospitales;

    private FragmentDatosPersonalesMedicoNutricionistaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        hospitalViewModel = new ViewModelProvider(this).get(HospitalViewModel.class);

        binding = FragmentDatosPersonalesMedicoNutricionistaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        obtenerDatosUsuario(root);

        camposEditables = Arrays.asList(
                root.findViewById(R.id.edtName),
                root.findViewById(R.id.edtPrimerApellido),
                root.findViewById(R.id.edtSegundoApellido),
                root.findViewById(R.id.edtTelefono),
                root.findViewById(R.id.dropdownProvincia),
                root.findViewById(R.id.dropdownHospital)
        );

        camposNoEditables = Arrays.asList(
                root.findViewById(R.id.edtDNI)
        );
        //Desactivar campos
        for(View campo : camposNoEditables){
            campo.setEnabled(false);
        }
        for(View campo : camposEditables){
            campo.setEnabled(false);
        }

        //Botón para editar
        btnEditarDatos = root.findViewById(R.id.btnEditarDatos);
        btnEditarDatos.setOnClickListener(v -> {
            if(!modoEdicion){
                nombresHospitales = new ArrayList<>();
                idHospitales = new ArrayList<>();

                for(View campo : camposEditables){
                    campo.setEnabled(true);
                }
                modoEdicion = true;
                btnEditarDatos.setText("Guardar");

                dropdownProvincia = binding.dropdownProvincia;
                dropdownHospital = binding.dropdownHospital;
                // Adapter para provincias
                String[] provincias = getResources().getStringArray(R.array.provincias);
                ArrayAdapter<String> adapterProvincia = new ArrayAdapter<>(
                        requireContext(), android.R.layout.simple_dropdown_item_1line, provincias
                );
                dropdownProvincia.setAdapter(adapterProvincia);

                provincia = dropdownProvincia.getText().toString();
                hospital = dropdownHospital.getText().toString();
                obtenerHospitalesPorProvincia(provincia);

                dropdownProvincia.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        provincia = s.toString();
                        dropdownHospital.setText("");

                        idHospitales.clear();
                        nombresHospitales.clear();

                        obtenerHospitalesPorProvincia(provincia);
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                dropdownHospital.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        hospital = s.toString();

                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

            }else{
                for(View campo : camposEditables){
                    campo.setEnabled(false);
                }
                modoEdicion = false;
                btnEditarDatos.setText("Editar");

                guardarDatosUsuario();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void obtenerDatosUsuario(View view){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        Log.d("UsuarioRecibido", new Gson().toJson(usuario));

        if(jsonUsuario != null){
            usuario = new Gson().fromJson(jsonUsuario, Usuario.class);

            ((EditText) view.findViewById(R.id.edtName)).setText(usuario.getNombre());
            ((EditText) view.findViewById(R.id.edtPrimerApellido)).setText(usuario.getApellido1());
            ((EditText) view.findViewById(R.id.edtSegundoApellido)).setText(usuario.getApellido2());
            ((EditText) view.findViewById(R.id.edtDNI)).setText(usuario.getDni());
            ((EditText) view.findViewById(R.id.edtTelefono)).setText(usuario.getTelefono());
            ((EditText) view.findViewById(R.id.dropdownProvincia)).setText(usuario.getProvincia());
            ((EditText) view.findViewById(R.id.dropdownHospital)).setText(usuario.getHospital().getNombre());
        }
    }

    private void guardarDatosUsuario(){
        if(usuario == null) return;

        Long idHospital = Long.parseLong("0");

        if(nombresHospitales.isEmpty()){
            usuario.getHospital().getIdHospital();
        }else{
            int indexHospital = nombresHospitales.indexOf(hospital);
            if(indexHospital != -1) {
                idHospital = idHospitales.get(indexHospital);
            }
        }

        hospitalViewModel.hospitalPorId(idHospital).observe(getViewLifecycleOwner(), hospital -> {
            if(hospital != null){
                guardarUsuarioConHospital(hospital);
            }else{
                ToastMessage.Invalido(getContext(), "No se ha encontrado el hospital");
            }
        });

    }

    private void guardarUsuarioConHospital(Hospital hospital){
        Usuario updateUsuario = usuario;
        //Obtener datos actualizados de los campos editados
        updateUsuario.setDni(usuario.getDni());
        updateUsuario.setNombre(binding.edtName.getText().toString());
        updateUsuario.setApellido1(binding.edtPrimerApellido.getText().toString());
        updateUsuario.setApellido2(binding.edtSegundoApellido.getText().toString());
        updateUsuario.setTelefono(binding.edtTelefono.getText().toString());
        updateUsuario.setProvincia(provincia);

        usuarioViewModel.actualizarUsuario(updateUsuario).observe(getViewLifecycleOwner(), response ->{
            if(response.getRpta() == 1){
                this.usuarioViewModel.asociarUsuarioHospital(updateUsuario.getDni(), hospital).observe(getViewLifecycleOwner(), hResponse -> {
                    if(hResponse.getRpta() == 1){
                        ToastMessage.Correcto(getContext(), "Datos guardados correctamente.");
                    }else{
                        ToastMessage.Invalido(getContext(), "Error al actualizar los datos.");
                    }
                });
            }else{
                ToastMessage.Invalido(requireContext(), "Error al guardar los datos");
            }
        });
    }

    private void obtenerHospitalesPorProvincia(String provincia) {
        hospitalViewModel.hospitalPorProvincia(provincia).observe(getViewLifecycleOwner(), new Observer<List<Hospital>>(){
            @Override
            public void onChanged(List<Hospital> hospitales){
                if(hospitales != null){
                    listaNombresHospitales(hospitales);
                }
            }
        });
    }

    private void listaNombresHospitales(List<Hospital> hospitales){
        for(Hospital hospital: hospitales){
            nombresHospitales.add(hospital.getNombre());
            idHospitales.add(hospital.getIdHospital());
        }
        nombresHospitales.add(0, "Otro");
        idHospitales.add(0, Long.parseLong("1"));
        ArrayAdapter<String> arrayHospitales = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombresHospitales);
        dropdownHospital.setAdapter(arrayHospitales);
    }


    public boolean estaEnModoEdicion() {
        return modoEdicion;
    }

    public void cancelarEdicion() {
        modoEdicion = false;
        btnEditarDatos.setText("Editar");
        for (View campo : camposEditables) campo.setEnabled(false);
        obtenerDatosUsuario(getView()); // Restaurar datos originales
    }
}