package com.example.alex.ghidoras;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;



import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    SweetAlertDialog alertDialog;




    BackgroundWorker (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        String ip = "http://192.168.0.105";
        String login_url = ip + "/login.php";
        String register_url = ip+ "/register.php";
        String getUser_url = ip +"/getUser.php";

        if(type.equals("login")){
            HttpURLConnection connection = null;
            try {
                String prenume= "";
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("nume", user_name);
                postDataParams.put("prenume", prenume);
                Log.v("jsonul",postDataParams.toString());

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
//        if(type.equals("login")) {
//            try {
//                String user_name = params[1];
//                String password = params[2];
//                URL url = new URL(login_url);
//                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                OutputStream outputStream = httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
//                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
//                bufferedWriter.write(post_data);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                outputStream.close();
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
//                String result="";
//                String line="";
//                while((line = bufferedReader.readLine())!= null) {
//                    result += line;
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return result;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        if(type.equals("register")) {

            try {
                String email = params[1];
                String parola = params[2];
                String nume = params[3];
                String prenume = params[4];
                String data_nasterii = params[5];
                String adresa = params[6];
                String sex = params[7];


                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("nume","UTF-8")+"="+URLEncoder.encode(nume,"UTF-8")+"&"
                        +URLEncoder.encode("prenume","UTF-8")+"="+URLEncoder.encode(prenume,"UTF-8") +"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8") +"&"
                        +URLEncoder.encode("parola","UTF-8")+"="+URLEncoder.encode(parola,"UTF-8") +"&"
                        +URLEncoder.encode("sex","UTF-8")+"="+URLEncoder.encode(sex,"UTF-8") +"&"
                        +URLEncoder.encode("data_nasterii","UTF-8")+"="+URLEncoder.encode(data_nasterii,"UTF-8") +"&"
                        +URLEncoder.encode("adresa","UTF-8")+"="+URLEncoder.encode(adresa,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        alertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alertDialog.setTitleText("Loading");
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    @Override
    protected void onPostExecute(String result) {
        if(!result.equals("0 results" )) {

            SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Status");
            pDialog.setContentText(result);
            pDialog.setCancelable(false);
            alertDialog.dismiss();
            IntroActivity.isLogged = true;
            pDialog.show();
            Intent i = new Intent(context, HomeActivity.class);
            context.startActivity(i);
        }else{
            SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Status");
            pDialog.setContentText(result);
            pDialog.setCancelable(false);
            alertDialog.dismiss();
            pDialog.show();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}