package com.example.alex.ghidoras;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alex on 14.11.2017.
 */

public class ApiConnectorRegister {
    public static String apiURL = "http://192.168.0.105/register.php";


    public static String register(String email, String parola, String nume, String prenume, String data_nasterii, String adresa, String sex) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(apiURL);
            JSONObject postDataParams = new JSONObject();


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("parola", parola);
            jsonObject.put("nume", nume);
            jsonObject.put("prenume", prenume);
            jsonObject.put("data_nasterii", data_nasterii);
            jsonObject.put("adresa", adresa);
            jsonObject.put("sex", sex);
            postDataParams.put("candidate", jsonObject);

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
            System.out.println("aici: " + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        } finally {
            connection.disconnect();
        }
    }
}
