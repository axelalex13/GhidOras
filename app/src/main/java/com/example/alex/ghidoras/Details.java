package com.example.alex.ghidoras;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    TextView nume;
    TextView descriere;
    TextView locatie;
    TextView date;
    TextView descriereLocatie;
    TextView adresa;
    TextView numar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
       // getSupportActionBar().setTitle("Detalii eveniment");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int position = getIntent().getIntExtra("position",100);
        nume = (TextView) findViewById(R.id.numeEveniment);
        descriere = (TextView) findViewById(R.id.descriere);
        locatie = (TextView) findViewById(R.id.numeLocatie);
        date = (TextView) findViewById(R.id.date);
        descriereLocatie = (TextView) findViewById(R.id.descriereLocatie);
        adresa = (TextView) findViewById(R.id.adresa);
        numar = (TextView) findViewById(R.id.numar);

        nume.setText(EventFragment.events.get(position).getName());
        descriere.setText("Descriere eveniment: " + EventFragment.events.get(position).getDescriere());
        locatie.setText("Locatie: "+ EventFragment.events.get(position).getNume_locatie());
        date.setText( EventFragment.events.get(position).getData_inceput() + " - "+ EventFragment.events.get(position).getData_sfarsit());
        descriereLocatie.setText("Descriere locatie: " +EventFragment.events.get(position).getDescriere_locatie());
        adresa.setText("Adresa: " +EventFragment.events.get(position).getAdresa());
        numar.setText("Numar maxim de persoane: " +EventFragment.events.get(position).getNumar_persoane());




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
