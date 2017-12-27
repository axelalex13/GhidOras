package com.example.alex.ghidoras;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alex.ghidoras.ApiConnector.ApiConnectorEditUser;
import com.example.alex.ghidoras.ApiConnector.ApiConnectorStatistics;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Statistics extends AppCompatActivity {

    TextView totalOrganizator;
    TextView locatii;
    TextView utilizatoriAdulti;
    TextView utilizatori;
    TextView evenimente;
    TextView donatie;
    int totalEvenimente;
    int totalEvenimenteFemei;
    String total;
    String total2;
    String total3;

    TextView femei;
    String total4 , total5, total6,total7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        getSupportActionBar().setTitle("Statistici");


        totalOrganizator = (TextView) findViewById(R.id.totalOrganizator);
        locatii = (TextView) findViewById(R.id.locatii);
        utilizatoriAdulti = (TextView) findViewById(R.id.utilizatoriAdulti);
        utilizatori = (TextView) findViewById(R.id.utilizatori);
        evenimente = (TextView) findViewById(R.id.evenimente);
        donatie = (TextView) findViewById(R.id.donatie);

        femei = (TextView) findViewById(R.id.femei);
        final SharedPreferences sharedPreferencesUser = getSharedPreferences("userInfo", Context
                .MODE_PRIVATE);
        final String id_organizator = sharedPreferencesUser.getString("id_organizator", "");
        AsyncTask<Void, Void, Void> task1 = new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
                total = ApiConnectorStatistics.totalOrganizator(id_organizator);
                Log.v("am primit la sta", total);

                return null;
            }

            protected void onPostExecute(Void param) {

                totalOrganizator.setText("Evenimente la care ai fost organizator: " + total);

            }
        };
        task1.execute();

        AsyncTask<Void, Void, Void> task2 = new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
                total2 = ApiConnectorStatistics.locatiiOrganizator(id_organizator);
                Log.v("am primit la sta", total2);

                return null;
            }

            protected void onPostExecute(Void param) {
                total2 =  total2.replace("[","");
                total2 =  total2.replace("]","");
                total2 =  total2.replace("\"","");

                locatii.setText("Locatiile unde ati organizat evenimente: " + total2);

            }
        };
        task2.execute();

        AsyncTask<Void, Void, Void> task3 = new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
                total3 = ApiConnectorStatistics.useriAdulti(id_organizator);
                Log.v("am primit la sta", total3);

                return null;
            }

            protected void onPostExecute(Void param) {
                total3 =  total3.replace("[","");
                total3 =  total3.replace("]","");
                total3 =  total3.replace("\"","");

                utilizatoriAdulti.setText("Evenimente la care au participat si copii: " + total3);

            }
        };
        task3.execute();



        AsyncTask<Void, Void, Void> task5 = new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
                total5 =  ApiConnectorStatistics.evenimente(id_organizator);
                Log.v("am primit la sta", total5);

                return null;
            }

            protected void onPostExecute(Void param) {

                totalEvenimente = Integer.parseInt(total5);
                evenimente.setText("Total evenimente: " + total5);

                AsyncTask<Void, Void, Void> task4 = new AsyncTask<Void, Void, Void>() {


                    @Override
                    protected Void doInBackground(Void... params) {
                        total4 =  ApiConnectorStatistics.evenimenteFemei(id_organizator);
                        Log.v("am primit la sta", total4);

                        return null;
                    }

                    protected void onPostExecute(Void param) {
                        total4 =  total4.replace("[","");
                        total4 =  total4.replace("]","");
                        total4 =  total4.replace("\"","");
                        totalEvenimenteFemei = Integer.valueOf(total4);
                        int procent = 0;
                        procent = totalEvenimenteFemei*100/totalEvenimente;
                        femei.setText("Total evenimente organizate de femei: " + procent + "%");

                    }
                };
                task4.execute();

            }
        };
        task5.execute();

        AsyncTask<Void, Void, Void> task6 = new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
                total6 =  ApiConnectorStatistics.utilizatori(id_organizator);
                Log.v("am primit la sta", total6);

                return null;
            }

            protected void onPostExecute(Void param) {
                total6 =  total6.replace("[","");
                total6 =  total6.replace("]","");
                total6 =  total6.replace("\"","");

                utilizatori.setText("Total utilizatori: " + total6);

            }
        };
        task6.execute();
        AsyncTask<Void, Void, Void> task7 = new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
                total7 =  ApiConnectorStatistics.donatie(id_organizator);
                Log.v("am primit la sta", total7);

                return null;
            }

            protected void onPostExecute(Void param) {


                donatie.setText("Evenimentul cu cea mai mare donatie: " + total7);

            }
        };
        task7.execute();





    }
}
