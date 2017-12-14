package com.example.alex.ghidoras;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alex.ghidoras.ApiConnector.ApiConnectionGetEvents;
import com.example.alex.ghidoras.ApiConnector.ApiConnectorOrganizator;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Details extends AppCompatActivity {

    TextView nume;
    TextView descriere;
    TextView locatie;
    TextView date;
    TextView descriereLocatie;
    TextView adresa;
    TextView numar;
    RelativeLayout layoutActiuni;
    RelativeLayout details;
    Button delete;
    Button edit;
    String result;
    int  position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
       // getSupportActionBar().setTitle("Detalii eveniment");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        position = getIntent().getIntExtra("position",100);
        nume = (TextView) findViewById(R.id.numeEveniment);
        descriere = (TextView) findViewById(R.id.descriere);
        locatie = (TextView) findViewById(R.id.numeLocatie);
        date = (TextView) findViewById(R.id.date);
        descriereLocatie = (TextView) findViewById(R.id.descriereLocatie);
        adresa = (TextView) findViewById(R.id.adresa);
        numar = (TextView) findViewById(R.id.numar);
        layoutActiuni = (RelativeLayout) findViewById(R.id.layoutActiuni);
        final SharedPreferences sharedPreferencesUser = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String id_organizator = sharedPreferencesUser.getString("id_organizator", "");
        final int id_org_event = EventFragment.events.get(position).getId_organizator();
        if(id_organizator.equals(String.valueOf(id_org_event))){
            layoutActiuni.setAlpha(1);
        }else {
            layoutActiuni.setAlpha(0);
        }

        delete = (Button) findViewById(R.id.delete);
        edit = (Button) findViewById(R.id.edit);
        nume.setText(EventFragment.events.get(position).getName());
        descriere.setText("Descriere eveniment: " + EventFragment.events.get(position).getDescriere());
        locatie.setText("Locatie: "+ EventFragment.events.get(position).getNume_locatie());
        date.setText( EventFragment.events.get(position).getData_inceput() + " - "+ EventFragment.events.get(position).getData_sfarsit());
        descriereLocatie.setText("Descriere locatie: " +EventFragment.events.get(position).getDescriere_locatie());
        adresa.setText("Adresa: " +EventFragment.events.get(position).getAdresa());
        numar.setText("Numar maxim de persoane: " +EventFragment.events.get(position).getNumar_persoane());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                final String id_eveniment = String.valueOf(EventFragment.events.get(position).getId());
                AsyncTask<Void, Void, Void> task3 = new AsyncTask<Void, Void, Void>() {


                    @Override
                    protected Void doInBackground(Void... params) {
                        result = ApiConnectionGetEvents.deleteEvent(id_eveniment);
                        Log.v("am primit la del ev", result);

                        return null;
                    }

                    protected void onPostExecute(Void param) {
                        if (result.equals("delete event succes\n")) {
                            final SweetAlertDialog alertDialog = new SweetAlertDialog(view.getContext(), SweetAlertDialog.SUCCESS_TYPE);

                            alertDialog.setTitle("Eveniment sters cu succes!");
                            alertDialog.setConfirmText("Ok");
                            alertDialog.show();
                            alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    alertDialog.dismiss();
                                }
                            });

                        } else {
                            final SweetAlertDialog alertDialog = new SweetAlertDialog(view.getContext(), SweetAlertDialog.WARNING_TYPE);

                            alertDialog.setContentText("Something went wrong :( ");
                            alertDialog.setConfirmText("Ok");
                            alertDialog.show();
                            alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    alertDialog.dismiss();
                                }
                            });
                        }

                    }
                };
                task3.execute();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditActivity.class);
                i.putExtra("position",position);
                getApplicationContext().startActivity(i);

            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
