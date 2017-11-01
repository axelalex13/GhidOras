package com.example.alex.ghidoras;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

    ImageView bunVenit;
    TextView bunVenitText;
    public static boolean isLogged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // remove title
        //Remove title bar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("");
        getSupportActionBar().hide();
        setContentView(R.layout.activity_intro);
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
