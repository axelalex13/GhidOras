package com.example.alex.ghidoras;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
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
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.ghidoras.ApiConnector.ApiConnectorEvent;
import com.example.alex.ghidoras.ApiConnector.ApiConnectorLocation;
import com.example.alex.ghidoras.utils.Event;
import com.example.alex.ghidoras.utils.Location;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EditActivity extends AppCompatActivity {
    static public List<Location> locations = new ArrayList<Location>();
    ArrayList<String> locationName = new ArrayList<>();
    LinkedHashMap<String,Location> hashMap = new LinkedHashMap<>();
    String edit;
    private static int RESULT_LOAD_IMAGE = 1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setTitle("Editeaza evenimentul");
        final TextView startingDate = (TextView) findViewById(R.id.startingDate);
        final TextView endingDate = (TextView) findViewById(R.id.endingDate);
        final TextView nume = (TextView) findViewById(R.id.nume);
        final TextView descriere = (TextView) findViewById(R.id.descriere);
        final Button save = (Button) findViewById(R.id.save);
        final Spinner dropdown2 = (Spinner) findViewById(R.id.locatie);
        final int position = getIntent().getIntExtra("position",-1000);

        final ImageView cover = (ImageView) findViewById(R.id.coperta);


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
                new DatePickerDialog(getApplicationContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final Calendar myCalendar2 = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
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
                new DatePickerDialog(getApplicationContext(), date2, myCalendar2
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
                locations = g.fromJson(s, new TypeToken<List<Location>>() {
                }.getType());
                Log.v("lista mea", locations.get(0).getDescriere());
                for (int i = 0; i < locations.size(); i++) {
                    hashMap.put(locations.get(i).getNume(), locations.get(i));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                super.onPostExecute(aVoid);

                for (int i = 0; i < locations.size(); i++) {
                    locationName.add(locations.get(i).getNume());
                }
                ArrayAdapter<String> adapter_location = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, locationName);
                dropdown2.setAdapter(adapter_location);


            }
        };

        task.execute();

        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        final TextView textView = (TextView) findViewById(R.id.editText9);
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

        TextView addImage = (TextView) findViewById(R.id.textView10);
        addImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View arg0) {

                new SweetAlertDialog(EditActivity.this, SweetAlertDialog.NORMAL_TYPE)
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

        final Button addLocation = (Button) findViewById(R.id.addLocation);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SweetAlertDialog customAddCourseDialog = new SweetAlertDialog(EditActivity.this, SweetAlertDialog.NORMAL_TYPE);
                LayoutInflater inflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view2 = inflater2.inflate(R.layout.custom_dialog_add_location, null);

                final EditText numeLocatie = (EditText) view2.findViewById(R.id.numeLocatie);
                numeLocatie.setHint("Nume locatie:");
                final EditText descriereLocatie = (EditText) view2.findViewById(R.id.descriereLocatie);
                descriereLocatie.setHint("Descriere locatie:");
                final EditText adresa = (EditText) view2.findViewById(R.id.adresa);
                adresa.setHint("Adresa");

                customAddCourseDialog.show();
                customAddCourseDialog.setCustomView(view2);
                customAddCourseDialog.setTitleText("Adauga o noua locatie");
                customAddCourseDialog.setConfirmText("Adauga");
                customAddCourseDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        final String numeLocatieS = numeLocatie.getText().toString();
                        final String descriereLocatieS = descriereLocatie.getText().toString();
                        final String adresaS = adresa.getText().toString();
                        AsyncTask<Void, Void, Void> task3 = new AsyncTask<Void, Void, Void>() {


                            @Override
                            protected Void doInBackground(Void... params) {
                                edit = ApiConnectorLocation.addLocation(numeLocatieS, descriereLocatieS, adresaS);
                                Log.v("am primit la location", edit);


                                return null;
                            }

                            protected void onPostExecute(Void param) {
                                if (edit.equals("add location succes\n")) {
                                    final SweetAlertDialog alertDialog = new SweetAlertDialog(EditActivity.this, SweetAlertDialog.SUCCESS_TYPE);

                                    alertDialog.setTitle("Locatie adaugata cu succes!");

                                    alertDialog.setConfirmText("Ok");
                                    alertDialog.show();
                                    alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            alertDialog.dismiss();
                                        }
                                    });

                                } else {
                                    final SweetAlertDialog alertDialog = new SweetAlertDialog(EditActivity.this, SweetAlertDialog.WARNING_TYPE);
                                    alertDialog.setTitle("Locatia NU a fost adaugat!");
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


            }
        });

        if(position!=-1000) {
            nume.setText(EventFragment.events.get(position).getName());
            descriere.setText(EventFragment.events.get(position).getDescriere());
            startingDate.setText(EventFragment.events.get(position).getData_inceput());
            endingDate.setText(EventFragment.events.get(position).getData_sfarsit());

       }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferencesUser = getApplicationContext().getSharedPreferences("userInfo", Context
                        .MODE_PRIVATE);
                final  String id_eveniment = String.valueOf(EventFragment.events.get(position).getId());

                final String numeS = nume.getText().toString();
                final String descriereS = descriere.getText().toString();
                final String dataSfarsit = endingDate.getText().toString();
                final String dataInceput = startingDate.getText().toString();
                final String locatie = dropdown2.getSelectedItem().toString();
                final String id_oraganizator = sharedPreferencesUser.getString("id_organizator", "");
                final String id_locatie = hashMap.get(locatie).getId();
                final String numar = textView.getText().toString();

                // Log.v("duc in api",numeS + descriereS + " "+dataSfarsit +" "+ dataInceput +" "+ locatie +" "+ id_oraganizator + " "+ id_locatie);
                if (!numeS.equals("") && !descriereS.equals("") && !locatie.equals("") && !descriereS.equals("") && !dataSfarsit.equals("") && !dataInceput.equals("")) {


                    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {


                        @Override
                        protected Void doInBackground(Void... params) {
                            edit = ApiConnectorEvent.editEvent(id_eveniment,numeS, descriereS,
                                    id_locatie, dataInceput, dataSfarsit, numar, id_oraganizator);
                            Log.v("am primit la edit", edit);


                            return null;
                        }

                        protected void onPostExecute(Void param) {
                           if (edit.equals("edit event succes\n")) {
                                final SweetAlertDialog alertDialog = new SweetAlertDialog(EditActivity.this, SweetAlertDialog.SUCCESS_TYPE);

                                alertDialog.setTitle("Eveniment editat cu succes!");

                                alertDialog.setConfirmText("Ok");
                                alertDialog.show();
                                alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        alertDialog.dismiss();
                                    }
                                });

                            } else {
                                final SweetAlertDialog alertDialog = new SweetAlertDialog(EditActivity.this, SweetAlertDialog.WARNING_TYPE);
                                alertDialog.setTitle("Evenimentul NU a fost editat!");
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
                    Toast.makeText(getApplicationContext(), "Nu ati completat toate campurile!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkWriteExternalPermission() {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //EDITARE IMAGINE DE PROFIL
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getApplicationContext().getApplicationContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.coperta);
            Bitmap image = BitmapFactory.decodeFile(picturePath);
            int newDim = (int) (image.getHeight() * (512.0 / image.getWidth()));
            Bitmap scaled = Bitmap.createScaledBitmap(image, 512, newDim, true);
            imageView.setImageBitmap(scaled);


        }

    }
}
