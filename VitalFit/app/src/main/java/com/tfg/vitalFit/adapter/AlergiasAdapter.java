package com.tfg.vitalfit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Alergias;

import java.util.List;

public class AlergiasAdapter extends RecyclerView.Adapter<AlergiasAdapter.AlergiasViewHolder>{
    private List<Alergias> listaAlergias;
    private Context context;

    public AlergiasAdapter(Context contex, List<Alergias> listaAlergias){
        this.context = context;
        this.listaAlergias = listaAlergias;
    }

    @NonNull
    @Override
    public AlergiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ver_alergia, parent, false);
        return new AlergiasViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AlergiasViewHolder holder, int position) {
        Alergias alergia = listaAlergias.get(position);
        holder.txtAlergia.setText(alergia.getAlergia());
        holder.txtTipo.setText(alergia.getTipo());
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
}
