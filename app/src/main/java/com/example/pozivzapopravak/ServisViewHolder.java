package com.example.pozivzapopravak;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;



public class ServisViewHolder extends RecyclerView.ViewHolder {

    TextView cardViewRadiona, cardViewAdresa;

    public ServisViewHolder(@NonNull final View itemView, final CardServisItemClickListener listener) {
        super(itemView);
        cardViewRadiona = itemView.findViewById(R.id.cardViewRadiona);
        cardViewAdresa = itemView.findViewById(R.id.cardViewAdresa);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION)
                    {
                        listener.onItemClick(position);
                    }
                }
            }
        });

    }


}
