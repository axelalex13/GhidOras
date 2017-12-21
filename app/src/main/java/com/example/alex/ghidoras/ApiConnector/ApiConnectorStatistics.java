package com.example.alex.ghidoras.ApiConnector;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alex on 19.12.2017.
 */

public class ApiConnectorStatistics {
    public static String ip = ApiConnectorEvent.ip;
    public static String apiURL = ip + "/statisticiTotalEvenimenteOrganizator.php";
    public static String apiURLlocatii = ip + "/statisticiLocatiiOrganizator.php";
    public static String totalOrganizator(String id) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiURL);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
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

    public static String locatiiOrganizator(String id) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiURLlocatii);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
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
