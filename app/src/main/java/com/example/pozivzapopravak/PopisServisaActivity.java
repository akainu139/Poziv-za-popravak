package com.example.pozivzapopravak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class PopisServisaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapterRecyclerView;
    private ServisAdapter adapterRecyclerView;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popis_servisa);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapterRecyclerView = new ServisAdapter(PodaciJSON.listaServisa);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterRecyclerView);

        adapterRecyclerView.setOnItemClickListener(new CardServisItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Servis servis = PodaciJSON.listaServisa.get(position);

                String udaljenost = Servis.distance(servis.getLatitude(),MainActivity.trenutnaLokacija.getLatitude(),servis.getLongitude(),MainActivity.trenutnaLokacija.getLongitude(),0,0);
                servis.setUdaljenost(udaljenost);

                Bundle args = new Bundle();
                args.putString("keyRadiona", servis.getRadiona());
                args.putString("keyAdresa", servis.getAdresa());
                args.putString("keyVlasnik", servis.getVlasnik());
                args.putString("keyWebStranica", servis.getWebStranica());
                args.putString("keyEmail", servis.getEmail());
                args.putString("keyTelefon", servis.getTelefon());
                args.putString("keyUdaljenost",servis.getUdaljenost());

                DijalogMarker dijalogMarker = new DijalogMarker();
                dijalogMarker.setArguments(args);
                dijalogMarker.show(getSupportFragmentManager(),"");

            }
        });
    }

}
