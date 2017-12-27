package com.example.alex.ghidoras;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.ghidoras.ApiConnector.ApiConnectorEditUser;
import com.example.alex.ghidoras.ApiConnector.ApiConnectorRegister;
import com.example.alex.ghidoras.utils.UserLogin;
import com.google.gson.Gson;

import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EditProfile extends AppCompatActivity {
    TextView nume;
    TextView prenume;
    TextView data_nasterii;
    TextView email;
    TextView parola;
    TextView parola_confirm;
    TextView adresa;
    TextView sex;
    Button save;
    Calendar myCalendar;
    RadioButton feminin;
    RadioButton masculin;
    String sexString;
    String editUser;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setTitle("Editeaza datele tale");



        nume = (EditText) findViewById(R.id.firstNameEdit);
        prenume = (EditText) findViewById(R.id.lastNameEdit);
        data_nasterii = (EditText) findViewById(R.id.varstaEdit);
        email = (EditText) findViewById(R.id.emailEdit);
        parola = (TextView) findViewById(R.id.passwordEdit);
        parola_confirm = (TextView) findViewById(R.id.password_confirmEdit);
        adresa = (TextView) findViewById(R.id.addressEdit);
        masculin = (RadioButton) findViewById(R.id.barbat);
        feminin = (RadioButton) findViewById(R.id.femeie);

        save = (Button) findViewById(R.id.editProfile);

        final SharedPreferences sharedPreferencesUser = getSharedPreferences("userInfo", Context
                .MODE_PRIVATE);

        nume.setText(sharedPreferencesUser.getString("nume",""));
        prenume.setText(sharedPreferencesUser.getString("prenume",""));
        email.setText(sharedPreferencesUser.getString("email",""));
        adresa.setText(sharedPreferencesUser.getString("email",""));
        // sex.setText(sharedPreferencesUser.getString("sex",""));
        data_nasterii.setText(sharedPreferencesUser.getString("data_nasterii",""));


        myCalendar = Calendar.getInstance();
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
                data_nasterii.setText(sdf.format(myCalendar.getTime()));

            }

        };
        data_nasterii.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditProfile.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String numeString = nume.getText().toString();
                final String prenumeString = prenume.getText().toString();
                final String data_nasteriiS = data_nasterii.getText().toString();
                final String emailString = email.getText().toString();
                final String parolaString = sharedPreferencesUser.getString("password","");
                final String parolaConfirmString = parola_confirm.getText().toString();
                final String adresaString = adresa.getText().toString();
                final String id = sharedPreferencesUser.getString("id","");
                if(masculin.isChecked()){
                    sexString = "Masculin";
                }
                else if(feminin.isChecked()) {
                    sexString = "Feminin";
                }


                if(!numeString.equals("") && !prenumeString.equals("") && !data_nasteriiS.equals("") && !emailString.equals("") && !parolaString.equals("") && !adresaString.equals("") ) {

                        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {


                            @Override
                            protected Void doInBackground(Void... params) {
                                editUser = ApiConnectorEditUser.editUser(id, emailString, parolaString,
                                        numeString, prenumeString, "1996-11-15", adresaString, sexString);
                                Log.v("am primit la edit", editUser);

                                return null;
                            }

                            protected void onPostExecute(Void param) {
                                if (editUser.equals("edit user succes\n")) {
                                    final SweetAlertDialog alertDialog = new SweetAlertDialog(EditProfile.this, SweetAlertDialog.SUCCESS_TYPE);

                                    alertDialog.setTitle("Profil editat cu succes!");

                                    alertDialog.setConfirmText("Ok");
                                    alertDialog.show();
                                    alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            alertDialog.dismiss();
                                        }
                                    });

                                } else {
                                    final SweetAlertDialog alertDialog = new SweetAlertDialog(EditProfile.this, SweetAlertDialog.WARNING_TYPE);
                                    alertDialog.setTitle("Profilul nu a fost editat!");
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



                }else {
                    Toast.makeText(EditProfile.this, "Completati toate campurile!", Toast.LENGTH_SHORT).show();
                }


            }
        });




    }
}