package com.example.alex.ghidoras;

/**
 * Created by alex on 09.12.2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.ghidoras.utils.Event;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Event> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewDescriere;
        TextView textViewDates;
        TextView textViewLocatie;
        TextView textViewAdresa;
        ImageView coperta;
        public Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.titlu);
            this.textViewDescriere = (TextView) itemView.findViewById(R.id.descriere);
            this.textViewDates = (TextView) itemView.findViewById(R.id.dates);
            this.textViewLocatie = (TextView) itemView.findViewById(R.id.locatie);

            this.coperta = (ImageView) itemView.findViewById(R.id.coperta);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(v.getContext(), Details.class);
//
//                    v.getContext().startActivity(intent);
//
//                }
//            });
        }
    }

    public CustomAdapter(ArrayList<Event> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);



        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewDescriere = holder.textViewDescriere;
        TextView textViewDates = holder.textViewDates;
        TextView textViewLocatie = holder.textViewLocatie;

        ImageView coperta = holder.coperta;


        textViewName.setText(dataSet.get(listPosition).getName());
        textViewDescriere.setText(dataSet.get(listPosition).getDescriere());
        textViewDates.setText(dataSet.get(listPosition).getData_inceput() + " - "+ dataSet.get(listPosition).getData_sfarsit());
        textViewLocatie.setText(dataSet.get(listPosition).getNume_locatie());
       // textViewAdresa.setText(dataSet.get(listPosition).getAdresa());
        if(dataSet.get(listPosition).getId_locatie() == 1)
        {
            coperta.setImageResource(R.drawable.herastrau);
        }
        if(dataSet.get(listPosition).getId_locatie() == 3)
        {
            coperta.setImageResource(R.drawable.teatrul_national);
        }
        if(dataSet.get(listPosition).getId_locatie() == 4)
        {
            coperta.setImageResource(R.drawable.biblioteca);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent=new Intent(v.getContext(), Details.class);
                    intent.putExtra("position",listPosition);
                    v.getContext().startActivity(intent);

            }
        } );




    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
