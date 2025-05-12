package com.tfg.vitalfit.adapter;

import android.content.Context;
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
import com.tfg.vitalfit.entity.service.Observacion;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.ObservacionesViewModel;

import java.util.List;

public class ObservacionesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_PACIENTE = 1;
    private static final int VIEW_TYPE_NUTRICIONISTA_MEDICO = 0;

    private Context context;
    private ObservacionesViewModel observacionesViewModel;

    private List<Observacion> listaObservaciones;
    private Boolean esPaciente;

    public ObservacionesAdapter(Context context, List<Observacion> listaObservaciones, Boolean esPaciente, ObservacionesViewModel observacionesViewModel){
        this.context = context;
        this.listaObservaciones = listaObservaciones;
        this.esPaciente = esPaciente;
        this.observacionesViewModel = observacionesViewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_PACIENTE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delete_observacion, parent, false);
            return new PacienteObservacionesViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ver_observacion, parent, false);
            return new ObservacionesViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Observacion observacion = listaObservaciones.get(position);
        if (holder instanceof PacienteObservacionesViewHolder) {
            PacienteObservacionesViewHolder viewHolder = (PacienteObservacionesViewHolder) holder;
            viewHolder.txtObservacion.setText(observacion.getObservacion());

            viewHolder.btnEliminar.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Eliminar observación")
                        .setMessage("¿Estás seguro de que deseas eliminar esta observación?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            observacionesViewModel.delete(observacion.getIdObservacion()).observe((LifecycleOwner) context, response -> {
                                if(response.getRpta() == 1){
                                    int pos = holder.getAdapterPosition();
                                    listaObservaciones.remove(pos);
                                    notifyItemRemoved(pos);
                                    notifyItemRangeChanged(pos, listaObservaciones.size());
                                    ToastMessage.Correcto(context, "Observación eliminada");
                                }else{
                                    ToastMessage.Invalido(context, "Error al eliminar la observación");
                                }
                            });
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            });

        } else if (holder instanceof ObservacionesViewHolder) {
            ObservacionesViewHolder viewHolder = (ObservacionesViewHolder) holder;
            viewHolder.txtObservacion.setText(observacion.getObservacion());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return esPaciente? VIEW_TYPE_PACIENTE : VIEW_TYPE_NUTRICIONISTA_MEDICO;
    }

    @Override
    public int getItemCount() {
        return listaObservaciones.size();
    }

    public static class ObservacionesViewHolder extends RecyclerView.ViewHolder {
        TextView txtObservacion;

        public ObservacionesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtObservacion = itemView.findViewById(R.id.txtObservacion);
        }
    }

    public static class PacienteObservacionesViewHolder extends RecyclerView.ViewHolder {
        TextView txtObservacion;
        Button btnEliminar;

        public PacienteObservacionesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtObservacion = itemView.findViewById(R.id.txtObservacion);
            btnEliminar = itemView.findViewById(R.id.btnEliminarObservacion);
        }
    }
}
