package com.example.tiketwisata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation app_splash, bottom_top;
    ImageView app_logo;
    TextView app_tagline;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUsernameLocal();

        // load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottom_top = AnimationUtils.loadAnimation(this, R.anim.bottom_top);

        // load element
        app_logo = findViewById(R.id.app_logo);
        app_tagline = findViewById(R.id.app_tagline);

        // run animation
        app_logo.startAnimation(app_splash);
        app_tagline.startAnimation(bottom_top);
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        if(username_key_new.isEmpty()){

            // SplashScreen
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent getstarted = new Intent(MainActivity.this, GetStarted.class);
                    startActivity(getstarted);
                    finish();
                }
            }, 2000);
        } else {
            // SplashScreen
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent gohome = new Intent(MainActivity.this, Home.class);
                    startActivity(gohome);
                    finish();
                }
            }, 2000);
        }
    }
}
