package com.example.alex.ghidoras;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.ghidoras.ApiConnector.ApiConnectorEvent;
import com.example.alex.ghidoras.ApiConnector.ApiConnectorLocation;
import com.example.alex.ghidoras.utils.Location;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;


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
    private static int RESULT_LOAD_IMAGE = 1;
    static public List<Location> locations = new ArrayList<Location>();
    ArrayList<String> locationName = new ArrayList<>();
    LinkedHashMap<String,Location> hashMap = new LinkedHashMap<>();
    View view;
    String event;

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

       final SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        final TextView textView = (TextView) view.findViewById(R.id.editText9);
        // Initialize the textview with '0'
        int progress = seekBar.getProgress();

        textView.setText(Integer.toString(progress));
//        textView.setEnabled(false);
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int progress = 0;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                        progress = progressValue;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // Display the value in textview
                        textView.setText(Integer.toString(progress));
                    }
                });

        TextView addImage = (TextView) view.findViewById(R.id.textView10);
        addImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View arg0) {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Adauga cover")
                        .setContentText("Vrei sa adaugi o imagine?")
                        .setConfirmText("Da")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                if (!checkWriteExternalPermission()) {
                                    String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                                    int permsRequestCode = 200;
                                    requestPermissions(perms, permsRequestCode);
                                } else {
                                    //pickPhoto();
                                    Intent i = new Intent(
                                            Intent.ACTION_PICK,
                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                                }
                            }
                        })
                        .setCancelText("Nu")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                            }
                        })
                        .show();
            }

        });

        final ImageView cover = (ImageView) view.findViewById(R.id.coperta);
  
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
                final String id_oraganizator = sharedPreferencesUser.getString("id", "");
                final String id_locatie = hashMap.get(locatie).getId();
                final String numar = textView.getText().toString();

                // Log.v("duc in api",numeS + descriereS + " "+dataSfarsit +" "+ dataInceput +" "+ locatie +" "+ id_oraganizator + " "+ id_locatie);
                if (!numeS.equals("") && !descriereS.equals("") && !locatie.equals("") && !descriereS.equals("0") && !dataSfarsit.equals("0") && !dataInceput.equals("0")) {


                    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {


                        @Override
                        protected Void doInBackground(Void... params) {
                             event = ApiConnectorEvent.addEvent(numeS, descriereS,
                                    id_locatie, dataInceput, dataSfarsit, numar, id_oraganizator);
                            Log.v("am primit la add", event);


                            return null;
                        }

                        protected void onPostExecute(Void param) {
                            if (event.equals("add event succes\n")) {
                                final SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);

                                alertDialog.setTitle("Eveniment adaugat cu succes!");

                                alertDialog.setConfirmText("Ok");
                                alertDialog.show();
                                alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        alertDialog.dismiss();
                                    }
                                });

                            } else {
                                final SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                                alertDialog.setTitle("Evenimentul NU a fost adaugat!");
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
                    task.execute();
                }else{
                    Toast.makeText(getActivity(), "Nu ati completat toate campurile!", Toast.LENGTH_SHORT).show();
                }
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
    //Metode pentru a verifica daca proprietatea e garantata deja

    public boolean checkWriteExternalPermission() {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = getActivity().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /*
    Metoda pentru alegerea noii poze si salvarea acesteia
     */
    public void pickPhoto() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //EDITARE IMAGINE DE PROFIL
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) view.findViewById(R.id.coperta);
            Bitmap image = BitmapFactory.decodeFile(picturePath);
            int newDim = (int) (image.getHeight() * (512.0 / image.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(image, 512, newDim, true);
            imageView.setImageBitmap(scaled);


        }
    }
}
