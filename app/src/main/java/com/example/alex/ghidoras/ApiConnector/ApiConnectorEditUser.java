package com.example.alex.ghidoras.ApiConnector;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alex on 18.12.2017.
 */

public class ApiConnectorEditUser {

    public static String ip = ApiConnectorEvent.ip;
    public static String apiURL = ip+ "/editUser.php";
    public static String deleteURL = ip+ "/deleteUser.php";
    public static String editUser(String id,String email, String parola, String nume, String prenume,
                                  String data_nasterii,String adresa, String sex) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiURL);



            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            jsonObject.put("email", email);
            jsonObject.put("parola", parola);
            jsonObject.put("nume", nume);
            jsonObject.put("prenume", prenume);
            jsonObject.put("data_nasterii", data_nasterii);
            jsonObject.put("adresa", adresa);
            jsonObject.put("sex", sex);

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

    public static String deleteUser(String id) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(deleteURL);
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("id_utilizator", id);

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

}
