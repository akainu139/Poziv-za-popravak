package com.example.pozivzapopravak;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PodaciJSON extends AsyncTask<String, String, String> {

    public static ArrayList<Servis> listaServisa = new ArrayList<Servis>();
    private static boolean napunjenaLista = false;

    @Override
    protected String doInBackground(String... params) {
        if(!napunjenaLista)
        {
            napunjenaLista = true;
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer result = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line+"\n");
                }

                JSONObject jsonObject = new JSONObject(result.toString());
                JSONArray jsonArrayData = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArrayData.length(); i++) {
                    JSONObject jsonObjectData = jsonArrayData.getJSONObject(i);

                    String radiona = jsonObjectData.getString("naziv");
                    String adresa = jsonObjectData.getString("adresa");
                    String vlasnik = jsonObjectData.getString("vlasnik");
                    String webStranica = jsonObjectData.getString("web");
                    String email = jsonObjectData.getString("email");
                    String telefon = jsonObjectData.getString("tel");
                    double latitude = jsonObjectData.getDouble("geo_latitude");
                    double longitude = jsonObjectData.getDouble("geo_longitude");

                    listaServisa.add(new Servis(radiona,adresa,vlasnik,webStranica, email,telefon,latitude,longitude));

                }
                return result.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }
    return null;
    }
}



