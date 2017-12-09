package com.example.alex.ghidoras.ApiConnector;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alex on 06.12.2017.
 */

public class ApiConnectorEvent {
    public static String ip = "http://192.168.0.100";
    public static String apiURL = ip+ "/addEvent.php";
    public static String addEvent(String nume, String descriere, String id_locatie, String data_inceput, String data_sfarsit, String numar_persoane, String id_organizator) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiURL);



            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nume", nume);
            jsonObject.put("descriere", descriere);
            jsonObject.put("id_locatie", id_locatie);
            jsonObject.put("id_organizator", id_organizator);
            jsonObject.put("data_inceput", data_inceput);
            jsonObject.put("data_sfarsit", data_sfarsit);
            jsonObject.put("numar_persoane", numar_persoane);

            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(jsonObject.toString());
            out.close();
            StringBuilder sb = new StringBuilder();
            sb.append("");
            int HttpResult = connection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
            } else {
                System.out.println(connection.getResponseMessage());
            }
            System.out.println("aici: " + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        } finally {
            connection.disconnect();
        }
    }

}
