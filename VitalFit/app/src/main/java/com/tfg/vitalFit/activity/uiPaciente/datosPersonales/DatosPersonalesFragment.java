package com.tfg.vitalfit.activity.uiPaciente.datosPersonales;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.databinding.FragmentDatospersonalesBinding;
import com.tfg.vitalfit.entity.service.Usuario;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.UsuarioViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private Usuario usuario;

    private FragmentDatospersonalesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        usuarioViewModel =
                new ViewModelProvider(this).get(UsuarioViewModel.class);

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
            }else{
                for(View campo : camposEditables){
                    campo.setEnabled(false);
                }
                modoEdicion = false;
                btnEditarDatos.setText("Editar");

                guardarDatosPaciente();
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

        //Obtener datos actualizados de los campos editados
        usuario.setNombre(binding.edtNameP.getText().toString());
        usuario.setApellido1(binding.edtPrimerApellidoP.getText().toString());
        usuario.setApellido2(binding.edtSegundoApellidoP.getText().toString());
        usuario.setTelefono(binding.edtTelefonoP.getText().toString());

        usuario.getPaciente().setDireccion(binding.edtDireccionP.getText().toString());
        usuario.getPaciente().setCP(binding.edtCPP.getText().toString());
        usuario.getPaciente().setProvincia(binding.dropdownProvinciaP.getText().toString());

        usuario.getHospital().setNombre(binding.dropdownHospitalP.getText().toString());

        usuarioViewModel.save(usuario).observe(getViewLifecycleOwner(), response ->{
            if(response.getRpta() == 1){
                ToastMessage.Correcto(requireContext(), "Datos guardados correctamente");
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
}