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

public class GetStarted extends AppCompatActivity {

    Button btn_sign_in, btn_new_account_create;
    Animation top_bottom, bottom_top;
    ImageView emblem;
    TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        top_bottom = AnimationUtils.loadAnimation(this, R.anim.top_bottom);
        bottom_top = AnimationUtils.loadAnimation(this, R.anim.bottom_top);

        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_new_account_create = findViewById(R.id.btn_new_account_create);

        emblem = findViewById(R.id.emblem);
        desc = findViewById(R.id.desc);

        // run animation
        emblem.startAnimation(top_bottom);
        desc.startAnimation(top_bottom);
        btn_sign_in.startAnimation(bottom_top);
        btn_new_account_create.startAnimation(bottom_top);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosign = new Intent(GetStarted.this, SignIn.class);
                startActivity(gotosign);
            }
        });

        btn_new_account_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterone = new Intent(GetStarted.this, RegisterOne.class);
                startActivity(gotoregisterone);
            }
        });
    }
}
