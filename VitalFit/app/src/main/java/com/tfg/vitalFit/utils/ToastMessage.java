package com.tfg.vitalfit.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tfg.vitalfit.R;

public class ToastMessage {

    public static void Correcto(Context context, String msg){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_toast_ok, null);
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastOk);
        txtMensaje.setText(msg);
        android.widget.Toast toast = new android.widget.Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public static void Invalido(Context context, String msg){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_toast_bad, null);
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastBad);
        txtMensaje.setText(msg);
        android.widget.Toast toast = new android.widget.Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(android.widget.Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
}
