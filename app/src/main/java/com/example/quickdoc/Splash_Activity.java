package com.example.quickdoc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_Activity extends AppCompatActivity {
    ImageView ivDoctorLogo;
    Animation fadein;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivDoctorLogo=findViewById(R.id.ivDoctorLogo);


        fadein = AnimationUtils.loadAnimation(Splash_Activity.this,R.anim.fadein);
        ivDoctorLogo.setAnimation(fadein);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash_Activity.this, Main_Activity.class);
                startActivity(i);
            }
        } , 4000);




    }
}