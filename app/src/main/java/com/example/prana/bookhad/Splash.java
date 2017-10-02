package com.example.prana.bookhad;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {
    final int SplashScreen_Timeout=2000;
    private FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mauth=FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mauth.getCurrentUser()!=null){

                }
            }
        },SplashScreen_Timeout);
    }
}
