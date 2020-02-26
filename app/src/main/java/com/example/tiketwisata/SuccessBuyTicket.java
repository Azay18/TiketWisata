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

public class SuccessBuyTicket extends AppCompatActivity {

    Button btn_dashboard, btn_view_ticket;
    Animation app_splash, bottom_top, top_bottom;
    TextView app_title, app_subtitle;
    ImageView icon_success_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        btn_dashboard = findViewById(R.id.btn_dashboard);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);
        icon_success_ticket = findViewById(R.id.icon_success_ticket);

        // load animation
        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        bottom_top = AnimationUtils.loadAnimation(this, R.anim.bottom_top);
        top_bottom = AnimationUtils.loadAnimation(this, R.anim.top_bottom);

        // run animation
        icon_success_ticket.startAnimation(app_splash);

        app_title.startAnimation(top_bottom);
        app_subtitle.startAnimation(top_bottom);

        btn_dashboard.startAnimation(bottom_top);
        btn_view_ticket.startAnimation(bottom_top);

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(SuccessBuyTicket.this, MyProfile.class);
                startActivity(gotoprofile);
            }
        });

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccessBuyTicket.this, Home.class);
                startActivity(gotohome);
            }
        });
    }
}
