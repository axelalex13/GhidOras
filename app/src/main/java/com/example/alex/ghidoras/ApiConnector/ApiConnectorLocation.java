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

public class ApiConnectorLocation {
    public static String ip = ApiConnectorEvent.ip;
    public static String apiURL = ip + "/getLocation.php";
    public static String apiURLAdd = ip + "/addLocation.php";

    public static String getLocations() {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiURL);
            JSONObject postDataParams = new JSONObject();
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(postDataParams.toString());
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
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        } finally {
            connection.disconnect();
        }
    }
    public static String addLocation(String nume_locatie, String descriere_locatie, String adresa) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiURLAdd);



            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nume_locatie", nume_locatie);
            jsonObject.put("descriere_locatie", descriere_locatie);
            jsonObject.put("adresa", adresa);
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
