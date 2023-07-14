package com.example.beatyourbestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    }

    public void onRegisterClick(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void onLoginSuccess(View view) {
        Intent intent = new Intent(this, WorkoutRoutines.class);
        startActivity(intent);
    }
}
