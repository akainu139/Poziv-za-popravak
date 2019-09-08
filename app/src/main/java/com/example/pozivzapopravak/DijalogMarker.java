package com.example.pozivzapopravak;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;

public class DijalogMarker extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dijalog,null);

        builder.setView(view).setPositiveButton("Zatvori", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        TextView dijalogRadiona = view.findViewById(R.id.dijalogRadiona);
        TextView dijalogAdresa = view.findViewById(R.id.dijalogAdresa);
        TextView dijalogVlasnik = view.findViewById(R.id.dijalogVlasnik);
        TextView dijalogWebStranica = view.findViewById(R.id.dijalogWebStranica);
        TextView dijalogEmail = view.findViewById(R.id.dijalogEmail);
        TextView dijalogTelefon = view.findViewById(R.id.dijalogTelefon);
        TextView dijalogUdaljenost = view.findViewById(R.id.dijalogUdaljenost);

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 125);

        Bundle args = getArguments();
        if(args != null)
        {
            String keyRadiona = (String)args.get("keyRadiona");
            String keyAdresa = (String)args.get("keyAdresa");
            String keyVlasnik = (String)args.get("keyVlasnik");
            final String keyWebStranica = (String)args.get("keyWebStranica");
            final String keyEmail = (String)args.get("keyEmail");
            final String keyTelefon = (String)args.get("keyTelefon");
            String keyUdaljenost = (String)args.get("keyUdaljenost");

            dijalogRadiona.setText(keyRadiona);
            dijalogAdresa.setText(keyAdresa);
            dijalogVlasnik.setText(keyVlasnik);
            dijalogWebStranica.setText(keyWebStranica);
            dijalogEmail.setText(keyEmail);
            dijalogTelefon.setText(keyTelefon);
            dijalogUdaljenost.setText(keyUdaljenost);

            dijalogTelefon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = "tel:" + keyTelefon;
                    try {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(s));
                        startActivity(intent);
                    }catch(Exception e)
                    {
                        Toast.makeText(getContext(),"Morate dopustiti aplikaciji korištenje telefonskih usluga.",Toast.LENGTH_LONG).show();
                    }
                }
            });

            dijalogEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = "mailto:" + keyEmail;
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
                        // test
                        //intent.putExtra(Intent.EXTRA_SUBJECT, "Predmet test");
                        //intent.putExtra(Intent.EXTRA_TEXT, "Poruka test");
                        startActivity(intent);
                    }catch(Exception e)
                    {
                        Toast.makeText(getContext(),"Došlo je do pogreške prilikom pristupanja mailu",Toast.LENGTH_LONG).show();
                    }
                }
            });

            dijalogWebStranica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(getContext(),WebStranica.class);
                        intent.putExtra("stranica",keyWebStranica);
                        startActivity(intent);
                    }catch(Exception e)
                    {
                        Toast.makeText(getContext(),"Došlo je do pogreške prilikom otvaranja web stranice",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        return builder.create();
    }

}
