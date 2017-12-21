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
    String total;
    String total2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        getSupportActionBar().setTitle("Statisticile tale");


        totalOrganizator = (TextView) findViewById(R.id.totalOrganizator);
        locatii = (TextView) findViewById(R.id.locatii);
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






    }
}
