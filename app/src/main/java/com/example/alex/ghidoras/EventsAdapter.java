package com.example.alex.ghidoras;

import android.content.Context;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.ghidoras.utils.Event;

import java.util.List;

/**
 * Created by alex on 08.12.2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {


    private Context mContext;
    private List<Event> eventsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, descriere,date,locatie;


        public MyViewHolder(View view) {

            super(view);
            title = (TextView) view.findViewById(R.id.titlu);
            descriere = (TextView) view.findViewById(R.id.descriere);
            date = (TextView) view.findViewById(R.id.dates);
            locatie = (TextView) view.findViewById(R.id.locatie);
        }
    }


    public EventsAdapter(Context mContext, List<Event> eventsList) {
        this.mContext = mContext;
        this.eventsList = eventsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Event event = eventsList.get(position);
        holder.title.setText(event.getName());
        holder.descriere.setText(event.getDescriere());
        holder.date.setText(event.getData_inceput() + " - "+ event.getData_sfarsit());
        holder.locatie.setText(event.getNume_locatie());



    }



}




