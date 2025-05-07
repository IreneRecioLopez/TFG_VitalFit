package com.tfg.vitalfit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Observaciones;

import java.util.List;

public class ObservacionesAdapter extends RecyclerView.Adapter<ObservacionesAdapter.ObservacionesViewHolder>{
    private List<Observaciones> listaObservaciones;
    private Context context;

    public ObservacionesAdapter(Context contex, List<Observaciones> listaObservaciones){
        this.context = context;
        this.listaObservaciones = listaObservaciones;
    }

    @NonNull
    @Override
    public ObservacionesAdapter.ObservacionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ver_observacion, parent, false);
        return new ObservacionesAdapter.ObservacionesViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservacionesAdapter.ObservacionesViewHolder holder, int position) {
        Observaciones observacion = listaObservaciones.get(position);
        holder.txtObservacion.setText(observacion.getObservacion());
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
}
