package com.tfg.vitalfit.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Operaciones;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OperacionesAdapter extends RecyclerView.Adapter<OperacionesAdapter.OperacionesViewHolder>{
    private List<Operaciones> listaOperaciones;
    private Context context;

    public OperacionesAdapter(Context contex, List<Operaciones> listaOperaciones){
        this.context = context;
        this.listaOperaciones = listaOperaciones;
    }

    @NonNull
    @Override
    public OperacionesAdapter.OperacionesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ver_operacion, parent, false);
        return new OperacionesAdapter.OperacionesViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull OperacionesAdapter.OperacionesViewHolder holder, int position) {
        Operaciones operacion = listaOperaciones.get(position);
        holder.txtOperacion.setText(operacion.getNombre());
        holder.txtFecha.setText(convertirFecha(operacion.getFecha()));
    }

    @Override
    public int getItemCount() {
        return listaOperaciones.size();
    }

    private String convertirFecha(String fecha) {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Log.e("FormatoFecha", fecha);
            Date date = formatoEntrada.parse(fecha);
            return formatoSalida.format(date);
        } catch (ParseException e) {
            Log.e("ErrorFecha", "Formato incorrecto: " + fecha);
            return null;
        }
    }

    public static class OperacionesViewHolder extends RecyclerView.ViewHolder {
        TextView txtOperacion, txtFecha;

        public OperacionesViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOperacion = itemView.findViewById(R.id.txtNombreOperacion);
            txtFecha = itemView.findViewById(R.id.txtFechaOperacion);
        }
    }
}
