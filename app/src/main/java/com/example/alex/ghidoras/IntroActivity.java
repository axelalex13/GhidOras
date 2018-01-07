package com.example.alex.ghidoras;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IntroActivity extends AppCompatActivity {


    ImageView bunVenit;
    TextView bunVenitText;
    public static boolean isLogged;
    public static String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("");
        getSupportActionBar().hide();
        setContentView(R.layout.activity_intro);

        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context
                .MODE_PRIVATE);

        isLogged = sharedPreferences.getBoolean("logged", false);

        bunVenit = (ImageView) findViewById(R.id.bunVenit);
        bunVenitText = (TextView)findViewById(R.id.bunVenitText);
        bunVenit.animate().translationY(300f);
        bunVenit.animate().translationY(-300f).setDuration(1000);

        bunVenitText.animate().translationY(300f);
        bunVenitText.animate().translationY(-300f).setDuration(1000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isLogged) {
                    Intent i = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(IntroActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 4000);



    }



}


