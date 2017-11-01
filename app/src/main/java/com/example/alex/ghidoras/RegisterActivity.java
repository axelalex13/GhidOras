package com.example.alex.ghidoras;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    TextView nume;
    TextView prenume;
    TextView data_nasterii;
    TextView email;
    TextView parola;
    TextView parola_confirm;
    TextView adresa;
    TextView sex;
    Button register;
    Calendar myCalendar;
    RadioButton feminin;
    RadioButton masculin;
    String sexString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        getSupportActionBar().setTitle("Inregistreza-te");
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.myGreen)));
        getSupportActionBar().setTitle("");
        getSupportActionBar().hide();
        nume = (TextView) findViewById(R.id.firstName);
        prenume = (TextView) findViewById(R.id.lastName);
        data_nasterii = (TextView) findViewById(R.id.varsta);
        email = (TextView) findViewById(R.id.email);
        parola = (TextView) findViewById(R.id.password);
        parola_confirm = (TextView) findViewById(R.id.password_confirm);
        adresa = (TextView) findViewById(R.id.address);
        masculin = (RadioButton) findViewById(R.id.barbat);
        feminin = (RadioButton) findViewById(R.id.femeie);

        register = (Button) findViewById(R.id.register2);


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
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String numeString = nume.getText().toString();
                final String prenumeString = prenume.getText().toString();
                final String data_nasteriiS = data_nasterii.getText().toString();
                final String emailString = email.getText().toString();
                final String parolaString = parola.getText().toString();
                final String parolaConfirmString = parola_confirm.getText().toString();
                final String adresaString = adresa.getText().toString();

                if(masculin.isChecked()){
                    sexString = "Masculin";
                }
                else if(feminin.isChecked()) {
                    sexString = "Feminin";
                }



                if(parolaString.equals(parolaConfirmString)) {
                    String type = "register";
                    BackgroundWorker backgroundWorker = new BackgroundWorker(RegisterActivity.this);
                    backgroundWorker.execute(type, emailString, parolaString, numeString, prenumeString, data_nasteriiS, adresaString, sexString);
                }else {
                    Toast.makeText(RegisterActivity.this, "Parolele nu coincid", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

}
