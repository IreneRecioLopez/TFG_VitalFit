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
import com.tfg.vitalfit.entity.service.Alergias;
import com.tfg.vitalfit.utils.ToastMessage;
import com.tfg.vitalfit.viewModel.AlergiasViewModel;

import java.util.List;

public class AlergiasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_PACIENTE = 1;
    private static final int VIEW_TYPE_NUTRICIONISTA_MEDICO = 0;

    private Context context;
    private AlergiasViewModel alergiasViewModel;

    private List<Alergias> listaAlergias;
    private Boolean esPaciente;

    public AlergiasAdapter(Context context, List<Alergias> listaAlergias, Boolean esPaciente, AlergiasViewModel alergiasViewModel){
        this.context = context;
        this.listaAlergias = listaAlergias;
        this.esPaciente = esPaciente;
        this.alergiasViewModel = alergiasViewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_PACIENTE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delete_alergia, parent, false);
            return new PacienteAlergiasViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ver_alergia, parent, false);
            return new AlergiasViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Alergias alergia = listaAlergias.get(position);
        if (holder instanceof PacienteAlergiasViewHolder) {
            PacienteAlergiasViewHolder viewHolder = (PacienteAlergiasViewHolder) holder;
            viewHolder.txtAlergia.setText(alergia.getAlergia());
            viewHolder.txtTipo.setText(alergia.getTipo());

            viewHolder.btnEliminar.setOnClickListener(v -> {
                new AlertDialog.Builder(context)
                        .setTitle("Eliminar alergia")
                        .setMessage("¿Estás seguro de que deseas eliminar esta alergia?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            alergiasViewModel.delete(alergia.getIdAlergia()).observe((LifecycleOwner) context, response -> {
                                if(response.getRpta() == 1){
                                    int pos = holder.getAdapterPosition();
                                    listaAlergias.remove(pos);
                                    notifyItemRemoved(pos);
                                    notifyItemRangeChanged(pos, listaAlergias.size());
                                    ToastMessage.Correcto(context, "Alergia eliminada");
                                }else{
                                    ToastMessage.Invalido(context, "Error al eliminar la alergia");
                                }
                            });
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            });

        } else if (holder instanceof AlergiasViewHolder) {
            AlergiasViewHolder viewHolder = (AlergiasViewHolder) holder;
            viewHolder.txtAlergia.setText(alergia.getAlergia());
            viewHolder.txtTipo.setText(alergia.getTipo());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return esPaciente? VIEW_TYPE_PACIENTE : VIEW_TYPE_NUTRICIONISTA_MEDICO;
    }

    @Override
    public int getItemCount() {
        return listaAlergias.size();
    }

    public static class AlergiasViewHolder extends RecyclerView.ViewHolder {
        TextView txtAlergia, txtTipo;

        public AlergiasViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAlergia = itemView.findViewById(R.id.txtNombreAlergia);
            txtTipo = itemView.findViewById(R.id.txtTipoAlergia);
        }
    }

    public static class PacienteAlergiasViewHolder extends RecyclerView.ViewHolder {
        TextView txtAlergia, txtTipo;
        Button btnEliminar;

        public PacienteAlergiasViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAlergia = itemView.findViewById(R.id.txtNombreAlergia);
            txtTipo = itemView.findViewById(R.id.txtTipoAlergia);
            btnEliminar = itemView.findViewById(R.id.btnEliminarAlergia);
        }
    }
}
