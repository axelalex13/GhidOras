package com.example.alex.ghidoras;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Ghidul orasului Bucuresti");

          getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.myGreen)));
//        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.blureed));
        getSupportActionBar().setTitle("");
        getSupportActionBar().hide();
        final TextView email = (TextView) findViewById(R.id.email_login);
        final TextView parola = (TextView) findViewById(R.id.password_login);
        Button login = (Button) findViewById(R.id.login2);
        Button goToRegister = (Button) findViewById(R.id.goToRegister);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String emailString = email.getText().toString();
                final String passwordString = parola.getText().toString();
//                String type = "login";
//                BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
//                backgroundWorker.execute(type, emailString, passwordString);
                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

                     UserLogin userLogIn = new UserLogin();
                    @Override
                    protected Void doInBackground(Void... params) {
                        String s = ApiConnectorLogin.logIn(emailString, passwordString,true);

                        Log.v("am primit in login", s);
                        Gson g = new Gson();
                        userLogIn = g.fromJson(s, UserLogin.class);

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);

                        if (userLogIn.getStatus().equals("ok")) {
                            // new getCandidate().execute(candidateId);
                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userInfo", Context
                                    .MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", emailString);
                            editor.putString("password", passwordString);
                            editor.putBoolean("logged", true);
                            editor.putString("nume",userLogIn.getNume());
                            editor.putString("prenume",userLogIn.getPrenume());
                            editor.putString("data_nasterii",userLogIn.getData_nasterii());
                            editor.putString("id",userLogIn.getId_utilizator());
                            editor.putString("sex",userLogIn.getSex());

                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Welocome back "+userLogIn.getPrenume()+"!",
                                    Toast.LENGTH_SHORT).show();

//                            Intent intent = new Intent(getActivity(), HomeActivityCandidate.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            startActivity(intent);
                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            getApplicationContext().startActivity(i);


                        } else {

                            Toast.makeText(getApplicationContext(), "Parola gresita/Cont inexistent",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                task.execute();
            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });




    }
}
