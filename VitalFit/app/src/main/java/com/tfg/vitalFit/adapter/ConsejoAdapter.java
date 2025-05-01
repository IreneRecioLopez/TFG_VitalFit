package com.tfg.vitalfit.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Consejo;

import java.util.List;

public class ConsejoAdapter extends RecyclerView.Adapter<ConsejoAdapter.ConsejoViewHolder>{
    private List<Consejo> listaConsejos;
    private Context context;
    private OnConsejoLeidoListener listener;

    public interface OnConsejoLeidoListener {
        void onConsejoLeido(Consejo consejo);
    }

    public ConsejoAdapter(Context context, List<Consejo> listaConsejos, OnConsejoLeidoListener listener) {
        this.context = context;
        this.listaConsejos = listaConsejos;
        this.listener = listener;
    }

    public ConsejoAdapter(Context context, List<Consejo> listaConsejos) {
        this.context = context;
        this.listaConsejos = listaConsejos;
    }

    @NonNull
    @Override
    public ConsejoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_consejo, parent, false);
        return new ConsejoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsejoViewHolder holder, int position) {
        Consejo consejo = listaConsejos.get(position);
        holder.txtTitulo.setText(consejo.getTitulo());
        holder.txtDescripcion.setVisibility(View.GONE);

        if(consejo.getLeido() == 0){
            holder.txtTitulo.setTypeface(null, Typeface.BOLD);
            holder.itemView.setBackgroundResource(R.drawable.borde_resaltado);
        }

        holder.itemView.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle(consejo.getTitulo())
                    .setMessage(consejo.getMensaje())
                    .setPositiveButton("Cerrar", (dialog, which) -> {
                        if (consejo.getLeido() == 0) {
                            consejo.setLeido(1);
                            notifyItemChanged(position);
                            listener.onConsejoLeido(consejo);
                        }
                    })
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return listaConsejos.size();
    }

    public static class ConsejoViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtDescripcion;

        public ConsejoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloConsejo);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcionConsejo);
        }
    }
}
