package com.example.pozivzapopravak;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class ZoviPomocActivity extends AppCompatActivity {


    SupportMapFragment mapa;

    Location trenutnaLokacija;
    FusedLocationProviderClient fusedLocationProviderClient;

    private boolean zoomPrvi = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zovi_pomoc);

        String[] dopustenjaLokacije = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(this, dopustenjaLokacije, 124);

        mapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mapa.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                if (ActivityCompat.checkSelfPermission(ZoviPomocActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(ZoviPomocActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    googleMap.setMyLocationEnabled(true);
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                    Task<Location> task = fusedLocationProviderClient.getLastLocation();
                    task.addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location != null)
                            {
                                trenutnaLokacija = location;
                                LatLng latLngTrenutnaLokacija = new LatLng(trenutnaLokacija.getLatitude(),trenutnaLokacija.getLongitude());
                                if(zoomPrvi)
                                {
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLngTrenutnaLokacija));
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngTrenutnaLokacija,10f));
                                    zoomPrvi=false;
                                }
                            }
                        }
                    });

                    for (int i = 0; i < PodaciJSON.listaServisa.size(); i++) {
                        final Servis servis = PodaciJSON.listaServisa.get(i);

                        final LatLng lokacijaMarkera = new LatLng(servis.getLatitude(),servis.getLongitude());
                        MarkerOptions markerOptionsoptions = new MarkerOptions();
                        markerOptionsoptions.position(lokacijaMarkera);

                        Marker marker = googleMap.addMarker(markerOptionsoptions);
                        marker.setTag(i);

                    }

                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            int pozicija = (int) marker.getTag();
                            Servis servis = PodaciJSON.listaServisa.get(pozicija);

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

                            return true;
                        }
                    });

                } else {
                    Toast.makeText(ZoviPomocActivity.this, "Morate dopustiti aplikaciji korištenje lokacijskih usluga.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }

}


