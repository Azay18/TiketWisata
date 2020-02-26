package com.example.tiketwisata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessRegister extends AppCompatActivity {

    Button btn_continue;
    Animation app_splash, bottom_top, top_bottom;
    ImageView icon_success;
    TextView app_title, app_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        btn_continue = findViewById(R.id.btn_continue);
        icon_success = findViewById(R.id.icon_success);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);

        // load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottom_top = AnimationUtils.loadAnimation(this, R.anim.bottom_top);
        top_bottom = AnimationUtils.loadAnimation(this, R.anim.top_bottom);


        // run animation
        btn_continue.startAnimation(bottom_top);
        icon_success.startAnimation(app_splash);
        app_title.startAnimation(top_bottom);
        app_subtitle.startAnimation(top_bottom);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccessRegister.this, Home.class);
                startActivity(gotohome);
            }
        });
    }
}
