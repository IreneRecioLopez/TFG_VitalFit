package com.tfg.vitalfit.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Operacion;
import com.tfg.vitalfit.utils.Fecha;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.OperacionesViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OperacionesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_PACIENTE = 1;
    private static final int VIEW_TYPE_NUTRICIONISTA_MEDICO = 0;

    private Context context;
    private OperacionesViewModel operacionesViewModel;

    private List<Operacion> listaOperaciones;
    private Boolean esPaciente;


    public OperacionesAdapter(Context context, List<Operacion> listaOperaciones, Boolean esPaciente, OperacionesViewModel operacionesViewModel){
        this.context = context;
        this.listaOperaciones = listaOperaciones;
        this.esPaciente = esPaciente;
        this.operacionesViewModel = operacionesViewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_PACIENTE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delete_operacion, parent, false);
            return new PacienteOperacionesViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ver_operacion, parent, false);
            return new OperacionesViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Operacion operacion = listaOperaciones.get(position);
        if(holder instanceof PacienteOperacionesViewHolder){
            PacienteOperacionesViewHolder viewHolder = (PacienteOperacionesViewHolder) holder;
            viewHolder.txtOperacion.setText(operacion.getNombre());
            viewHolder.txtFecha.setText(Fecha.obtenerFecha(operacion.getFecha()));

            viewHolder.btnEliminar.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Eliminar operación")
                        .setMessage("¿Estás seguro de que deseas eliminar esta operación?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            operacionesViewModel.delete(operacion.getIdOperacion()).observe((LifecycleOwner) context, response -> {
                                if(response.getRpta() == 1){
                                    int pos = holder.getAdapterPosition();
                                    listaOperaciones.remove(pos);
                                    notifyItemRemoved(pos);
                                    notifyItemRangeChanged(pos, listaOperaciones.size());
                                    ToastMessage.Correcto(context, "Operación eliminada");
                                }else{
                                    ToastMessage.Invalido(context, "Error al eliminar la operación");
                                }
                            });
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            });
        }else if(holder instanceof OperacionesViewHolder){
            OperacionesViewHolder viewHolder = (OperacionesViewHolder) holder;
            viewHolder.txtOperacion.setText(operacion.getNombre());
            viewHolder.txtFecha.setText(Fecha.obtenerFecha(operacion.getFecha()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return esPaciente? VIEW_TYPE_PACIENTE : VIEW_TYPE_NUTRICIONISTA_MEDICO;
    }

    @Override
    public int getItemCount() {
        return listaOperaciones.size();
    }


    public static class OperacionesViewHolder extends RecyclerView.ViewHolder {
        TextView txtOperacion, txtFecha;

        public OperacionesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOperacion = itemView.findViewById(R.id.txtNombreOperacion);
            txtFecha = itemView.findViewById(R.id.txtFechaOperacion);
        }
    }

    public static class PacienteOperacionesViewHolder extends RecyclerView.ViewHolder {
        TextView txtOperacion, txtFecha;
        Button btnEliminar;

        public PacienteOperacionesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOperacion = itemView.findViewById(R.id.txtNombreOperacion);
            txtFecha = itemView.findViewById(R.id.txtFechaOperacion);
            btnEliminar = itemView.findViewById(R.id.btnEliminarOperacion);
        }
    }
}
