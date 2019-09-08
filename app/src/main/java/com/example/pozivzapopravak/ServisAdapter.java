package com.example.pozivzapopravak;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ServisAdapter extends RecyclerView.Adapter<ServisViewHolder> {

    private ArrayList<Servis> lista;

    private CardServisItemClickListener cardListener;

    public ServisAdapter(ArrayList<Servis> lista) {
        this.lista = lista;
    }

    public void setOnItemClickListener(CardServisItemClickListener listener)
    {
        cardListener = listener;
    }

    @NonNull
    @Override
    public ServisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_popis_servisa,viewGroup, false);
        ServisViewHolder servisViewHolder = new ServisViewHolder(view, cardListener);
        return servisViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServisViewHolder servisViewHolder, int i) {
        Servis trenutniServis = lista.get(i);

        servisViewHolder.cardViewRadiona.setText(trenutniServis.getRadiona());
        servisViewHolder.cardViewAdresa.setText(trenutniServis.getAdresa());
        if(i%2==0)
        {
            servisViewHolder.cardViewRadiona.setBackgroundColor(Color.parseColor("#E6E6FA"));
            servisViewHolder.cardViewAdresa.setBackgroundColor(Color.parseColor("#E6E6FA"));
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

}
