package com.example.alex.ghidoras;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.ghidoras.ApiConnector.ApiConnectorEvent;
import com.example.alex.ghidoras.ApiConnector.ApiConnectorLocation;
import com.example.alex.ghidoras.ApiConnector.ApiConnectorLogin;
import com.example.alex.ghidoras.ApiConnector.ApiConnectorRegister;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static public List<Location> locations = new ArrayList<Location>();
    ArrayList<String> locationName = new ArrayList<>();
    LinkedHashMap<String,Location> hashMap = new LinkedHashMap<>();
    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_add, container, false);

        final TextView startingDate = (TextView) view.findViewById(R.id.startingDate);
        final TextView endingDate = (TextView) view.findViewById(R.id.endingDate);
        final TextView nume = (TextView) view.findViewById(R.id.nume);
        final TextView descriere = (TextView) view.findViewById(R.id.descriere);
        final Button adauga = (Button) view.findViewById(R.id.adauga);
        final Spinner dropdown2 = (Spinner) view.findViewById(R.id.locatie);



        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            private void updateLabel() {
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                startingDate.setText(sdf.format(myCalendar.getTime()));

            }

        };

        startingDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final Calendar myCalendar2 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            private void updateLabel() {
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                endingDate.setText(sdf.format(myCalendar2.getTime()));

            }

        };

        endingDate.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {


            @Override
            protected Void doInBackground(Void... params) {
                String s = ApiConnectorLocation.getLocations();

                Log.v("am primit la get Loc", s);
                Gson g = new Gson();
                locations = g.fromJson(s,  new TypeToken<List<Location>>(){}.getType());
                Log.v("lista mea",locations.get(0).getDescriere());
                for(int i=0; i<locations.size();i++){
                    hashMap.put(locations.get(i).getNume(),locations.get(i));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                super.onPostExecute(aVoid);

                for(int i=0; i<locations.size();i++){
                    locationName.add(locations.get(i).getNume());
                }
                ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, locationName);
                dropdown2.setAdapter(adapter_location);


            }
        };

        task.execute();

        adauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferencesUser = getActivity().getSharedPreferences("userInfo", Context
                        .MODE_PRIVATE);
                final String numeS = nume.getText().toString();
                final String descriereS = descriere.getText().toString();
                final String dataSfarsit = endingDate.getText().toString();
                final String dataInceput = startingDate.getText().toString();
                final String locatie = dropdown2.getSelectedItem().toString();
                final String id_oraganizator = sharedPreferencesUser.getString("id","");
                final String id_locatie = hashMap.get(locatie).getId();

               // Log.v("duc in api",numeS + descriereS + " "+dataSfarsit +" "+ dataInceput +" "+ locatie +" "+ id_oraganizator + " "+ id_locatie);
                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {


                    @Override
                    protected Void doInBackground(Void... params) {
                        String event = ApiConnectorEvent.addEvent(numeS, descriereS,
                                id_locatie, dataInceput, dataSfarsit,"50", id_oraganizator);
                        Log.v("am primit la add", event);

                        return null;
                    }

                    protected void onPostExecute(Void param) {

                    }
                };
                task.execute();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
