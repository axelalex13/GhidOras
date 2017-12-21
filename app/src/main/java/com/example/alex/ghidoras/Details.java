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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.ghidoras.ApiConnector.ApiConnectionGetEvents;
import com.example.alex.ghidoras.ApiConnector.ApiConnectorLogin;
import com.example.alex.ghidoras.ApiConnector.ApiConnectorOrganizator;
import com.example.alex.ghidoras.ApiConnector.ApiConnectorParticipant;
import com.example.alex.ghidoras.utils.Organizator;
import com.example.alex.ghidoras.utils.UserLogin;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Details extends AppCompatActivity {

    TextView nume;
    TextView descriere;
    TextView locatie;
    TextView date;
    TextView descriereLocatie;
    TextView adresa;
    TextView numar;
    TextView dateOrg;
    RelativeLayout layoutActiuni;
    RelativeLayout details;
    Button delete;
    Button edit;
    Button participa;
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
        participa = (Button) findViewById(R.id.particip);
        layoutActiuni = (RelativeLayout) findViewById(R.id.layoutActiuni);
        final SharedPreferences sharedPreferencesUser = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String id_organizator = sharedPreferencesUser.getString("id_organizator", "");
        final int id_org_event = EventFragment.events.get(position).getId_organizator();
        if(id_organizator.equals(String.valueOf(id_org_event))){
            layoutActiuni.setAlpha(1);
            participa.setAlpha(0);
        }else {
            layoutActiuni.setAlpha(0);
            participa.setAlpha(1);
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
        dateOrg = (TextView) findViewById(R.id.dateOrg);
        participa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id_eveniment = String.valueOf(EventFragment.events.get(position).getId());
                final SharedPreferences sharedPreferencesUser = getSharedPreferences("userInfo", Context
                        .MODE_PRIVATE);
                final String id = sharedPreferencesUser.getString("id", "");
                AsyncTask<Void, Void, Void> task3 = new AsyncTask<Void, Void, Void>() {


                    @Override
                    protected Void doInBackground(Void... params) {
                        result = ApiConnectorParticipant.addParticipant(id, id_eveniment);
                        Log.v("am primit la par", result);


                        return null;
                    }

                    protected void onPostExecute(Void param) {
                        if (result.equals("add participant succes\n")) {
                            final SweetAlertDialog alertDialog = new SweetAlertDialog(Details.this, SweetAlertDialog.SUCCESS_TYPE);

                            alertDialog.setTitle("Te-ai inscris la "+ EventFragment.events.get(position).getName() +"!");
                            alertDialog.setConfirmText("Ok");
                            alertDialog.show();
                            alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    alertDialog.dismiss();
                                }
                            });

                        } else {
                            final SweetAlertDialog alertDialog = new SweetAlertDialog(Details.this, SweetAlertDialog.NORMAL_TYPE);

                            alertDialog.setContentText("Esti deja inscris la  "+ EventFragment.events.get(position).getName());
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


        dateOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

                    Organizator organizator = new Organizator();
                    @Override
                    protected Void doInBackground(Void... params) {
                        String org = ApiConnectorOrganizator.getOrganizator(id_org_event);

                        Log.v("am primit in getO", org);
                        Gson g = new Gson();
                        organizator = g.fromJson(org, Organizator.class);
                        Log.v("am primit in getO", organizator.getDescriere_organizator() + organizator.getEmail());
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        final SweetAlertDialog customDialog = new SweetAlertDialog(Details.this, SweetAlertDialog.NORMAL_TYPE);
                        customDialog.setTitle("Date organizator");
//                        customDialog.setContentText("Nume: "+organizator.getNume());
//                        customDialog.show();
                        LayoutInflater inflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View view2 = inflater2.inflate(R.layout.custom_dialog_organizator, null);

                        final TextView descriere = (TextView) view2.findViewById(R.id.descriereOrg);
                        final TextView ocupatie = (TextView) view2.findViewById(R.id.ocupatieOrg);
                        final TextView nume = (TextView) view2.findViewById(R.id.numeOrg);
                        final TextView prenume = (TextView) view2.findViewById(R.id.prenumeOrg);
                        final TextView email = (TextView) view2.findViewById(R.id.emailOrg);
                        descriere.setText("Despre "+organizator.getPrenume()+": "+organizator.getDescriere_organizator());
                        ocupatie.setText("Ocupatie: "+ organizator.getOcupatie_organizator());
                        nume.setText("Nume: "+organizator.getNume());
                        prenume.setText("Prenume: "+organizator.getPrenume());
                        email.setText("Email: "+organizator.getEmail());
                        customDialog.show();
                        customDialog.setCustomView(view2);
                        customDialog.setTitleText("Date organizator");
                    }
                };

                task.execute();
           }


        });




    }

}
