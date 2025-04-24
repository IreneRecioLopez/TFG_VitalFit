package com.tfg.vitalfit.activity.uiPaciente.datosPersonales;

import android.content.DialogInterface;
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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.databinding.FragmentDatospersonalesBinding;
import com.tfg.vitalfit.entity.service.Hospital;
import com.tfg.vitalfit.entity.service.Paciente;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.HospitalViewModel;
import com.tfg.vitalfit.viewModel.PacienteViewModel;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatosPersonalesFragment extends Fragment {

    private List<View> camposEditables;
    private List<View> camposNoEditables;
    private Button btnEditarDatos;
    private Boolean modoEdicion = false;

    private UsuarioViewModel usuarioViewModel;
    private PacienteViewModel pacienteViewModel;
    private HospitalViewModel hospitalViewModel;
    private Usuario usuario;

    AutoCompleteTextView dropdownHospital, dropdownProvincia;

    String provincia, hospital;

    private FragmentDatospersonalesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        pacienteViewModel = new ViewModelProvider(this).get(PacienteViewModel.class);
        hospitalViewModel = new ViewModelProvider(this).get(HospitalViewModel.class);

        binding = FragmentDatospersonalesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        obtenerDatosUsuario(root);

        camposEditables = Arrays.asList(
                root.findViewById(R.id.edtNameP),
                root.findViewById(R.id.edtPrimerApellidoP),
                root.findViewById(R.id.edtSegundoApellidoP),
                root.findViewById(R.id.edtTelefonoP),
                root.findViewById(R.id.edtDireccionP),
                root.findViewById(R.id.edtCPP),
                root.findViewById(R.id.dropdownProvinciaP),
                root.findViewById(R.id.dropdownHospitalP)
        );

        camposNoEditables = Arrays.asList(
                root.findViewById(R.id.edtFechaNacimientoP),
                root.findViewById(R.id.edtDNIP),
                root.findViewById(R.id.edtNSSP)
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
                for(View campo : camposEditables){
                    campo.setEnabled(true);
                }
                modoEdicion = true;
                btnEditarDatos.setText("Guardar");

                dropdownProvincia = binding.dropdownProvinciaP;
                dropdownHospital = binding.dropdownHospitalP;
                // Adapter para provincias
                String[] provincias = getResources().getStringArray(R.array.provincias);
                ArrayAdapter<String> adapterProvincia = new ArrayAdapter<>(
                        requireContext(), android.R.layout.simple_dropdown_item_1line, provincias
                );
                dropdownProvincia.setAdapter(adapterProvincia);
                provincia = dropdownProvincia.getText().toString();
                obtenerHospitalesPorProvincia(provincia);
                dropdownProvincia.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        provincia = s.toString();
                        dropdownHospital.setText("");
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

                guardarDatosPaciente();
            }
        });
        // Agregar el listener para manejar el "back press"
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Si hay cambios no guardados, mostrar el diálogo
                if (modoEdicion) {
                    showDiscardChangesDialog();
                } else {
                    // Si no hay cambios, proceder con la navegación normalmente
                    requireActivity().onBackPressed(); // Llama al "back press" de la actividad
                }
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
        //Obtener los datos del usuario
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        Log.d("UsuarioRecibido", new Gson().toJson(usuario));

        if(jsonUsuario != null){
            usuario = new Gson().fromJson(jsonUsuario, Usuario.class);

            ((EditText) view.findViewById(R.id.edtNameP)).setText(usuario.getNombre());
            ((EditText) view.findViewById(R.id.edtPrimerApellidoP)).setText(usuario.getApellido1());
            ((EditText) view.findViewById(R.id.edtSegundoApellidoP)).setText(usuario.getApellido2());
            ((EditText) view.findViewById(R.id.edtDNIP)).setText(usuario.getDni());
            ((EditText) view.findViewById(R.id.edtNSSP)).setText(usuario.getPaciente().getNumSeguridadSocial());
            ((EditText) view.findViewById(R.id.edtTelefonoP)).setText(usuario.getTelefono());
            ((EditText) view.findViewById(R.id.edtFechaNacimientoP)).setText(LeerFecha(usuario.getPaciente().getFechaNacimiento()));
            ((EditText) view.findViewById(R.id.edtCPP)).setText(usuario.getPaciente().getCP());
            ((EditText) view.findViewById(R.id.edtDireccionP)).setText(usuario.getPaciente().getDireccion());
            ((EditText) view.findViewById(R.id.dropdownProvinciaP)).setText(usuario.getPaciente().getProvincia());
            ((EditText) view.findViewById(R.id.dropdownHospitalP)).setText(usuario.getHospital().getNombre());

        }
    }

    private void guardarDatosPaciente(){
        if(usuario == null) return;

        if (hospital.equals("Otro")) {
            hospitalViewModel.hospitalPorNombre(hospital).observe(getViewLifecycleOwner(), hospital -> {
                if(hospital != null){
                    guardarUsuarioConHospital(hospital);
                }else{
                    ToastMessage.Invalido(getContext(), "No se ha encontrado el hospital");
                }
            });
        }else{
            hospitalViewModel.hospitalPorNombreYProvincia(hospital, provincia).observe(getViewLifecycleOwner(), hospital -> {
                if (hospital != null) {
                    guardarUsuarioConHospital(hospital);
                } else {
                    ToastMessage.Invalido(getContext(),"No se ha encontrado el hospital.");
                }
            });
        }
    }

    private void guardarUsuarioConHospital(Hospital hospital){
        Usuario updateUsuario = usuario;
        Paciente updatePaciente = usuario.getPaciente();
        //Obtener datos actualizados de los campos editados
        updateUsuario.setDni(usuario.getDni());
        updateUsuario.setNombre(binding.edtNameP.getText().toString());
        updateUsuario.setApellido1(binding.edtPrimerApellidoP.getText().toString());
        updateUsuario.setApellido2(binding.edtSegundoApellidoP.getText().toString());
        updateUsuario.setTelefono(binding.edtTelefonoP.getText().toString());

        updatePaciente.setDireccion(binding.edtDireccionP.getText().toString());
        updatePaciente.setCP(binding.edtCPP.getText().toString());
        updatePaciente.setProvincia(provincia);

        //updatePaciente.setProvincia(binding.dropdownProvinciaP.getText().toString());
        //updateUsuario.getHospital().setNombre(binding.dropdownHospitalP.getText().toString());

        usuarioViewModel.actualizarUsuario(updateUsuario).observe(getViewLifecycleOwner(), response ->{
            if(response.getRpta() == 1){
                pacienteViewModel.actualizarPaciente(updatePaciente).observe(getViewLifecycleOwner(), pResponse -> {
                    if(pResponse.getRpta() == 1){
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
            }else{
                ToastMessage.Invalido(requireContext(), "Error al guardar los datos");
            }
        });
    }

    private String LeerFecha(String fecha) {
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Log.e("FormatoFecha", fecha);
            Date date = formatoEntrada.parse(fecha);
            return formatoSalida.format(date);
        } catch (ParseException e) {
            Log.e("ErrorFecha", "Formato incorrecto: " + fecha);
            return null;
        }
    }

    private void obtenerHospitalesPorProvincia(String provincia) {
        hospitalViewModel.hospitalPorProvincia(provincia).observe(this, new Observer<List<Hospital>>(){
            @Override
            public void onChanged(List<Hospital> hospitales){
                if(hospitales != null){
                    Log.d("Hospitales", "Numero de hospitales: " + hospitales.size());
                    listaNombresHospitales(hospitales);
                }
            }
        });
    }

    private void listaNombresHospitales(List<Hospital> hospitales){
        List<String> nombresHospitales = new ArrayList<>();
        for(Hospital hospital: hospitales){
            nombresHospitales.add(hospital.getNombre());
        }
        nombresHospitales.add(0, "Otro");
        ArrayAdapter<String> arrayHospitales = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombresHospitales);
        dropdownHospital.setAdapter(arrayHospitales);
    }


    private void showDiscardChangesDialog() {
        new AlertDialog.Builder(getContext())
                .setMessage("¿Estás seguro de que deseas descartar los cambios?")
                .setCancelable(false) // No permitir cerrar el diálogo sin una acción
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Navegar a otro fragmento o actividad
                        Navigation.findNavController(getView()).navigate(R.id.nav_host_fragment_content_inicio);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}