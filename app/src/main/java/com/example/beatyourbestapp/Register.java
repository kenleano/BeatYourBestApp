package com.example.beatyourbestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
