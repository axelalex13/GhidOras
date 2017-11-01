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
                String type = "login";
                BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
                backgroundWorker.execute(type, emailString, passwordString);

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
