package com.tfg.vitalfit.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tfg.vitalfit.R;
import com.tfg.vitalfit.entity.service.Consejo;
import com.tfg.vitalfit.entity.service.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ConsejoAdapter extends RecyclerView.Adapter<ConsejoAdapter.ConsejoViewHolder>{
    private List<Consejo> listaConsejos;
    private Context context;
    private OnConsejoLeidoListener listener;
    private Usuario usuario;


    public interface OnConsejoLeidoListener {
        void onConsejoLeido(Consejo consejo);
    }

    public ConsejoAdapter(Context context, List<Consejo> listaConsejos, Usuario usuario, OnConsejoLeidoListener listener) {
        this.context = context;
        this.listaConsejos = listaConsejos;
        this.listener = listener;
        this.usuario = usuario;
        this.obtenerDatosUsuario();

    }

    public ConsejoAdapter(Context context, List<Consejo> listaConsejos) {
        this.context = context;
        this.listaConsejos = listaConsejos;
        this.obtenerDatosUsuario();
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

        if (consejo.getLeido() == 0) {
            holder.txtTitulo.setTypeface(null, Typeface.BOLD);
            holder.itemView.setBackgroundResource(R.drawable.borde_resaltado);
        } else {
            holder.txtTitulo.setTypeface(null, Typeface.NORMAL);
            holder.itemView.setBackgroundResource(android.R.color.transparent);
        }

        holder.itemView.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle(consejo.getTitulo())
                    .setMessage(consejo.getMensaje())
                    .setPositiveButton("Cerrar", (dialog, which) -> {
                        if (usuario.getRol().equals("Paciente") && consejo.getLeido() == 0) {
                            consejo.setLeido(1); // Marca local
                            notifyItemChanged(holder.getAdapterPosition());
                            notifyDataSetChanged();
                            listener.onConsejoLeido(consejo); // Notifica al activity
                        }
                    })
                    .show();
        });
    }

    private void obtenerDatosUsuario() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonUsuario = prefs.getString("UsuarioJson", null);

        if (jsonUsuario != null) {
            usuario = new Gson().fromJson(jsonUsuario, Usuario.class);
            Log.d("UsuarioRecibido", new Gson().toJson(usuario));
        }
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
