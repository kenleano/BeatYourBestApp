package com.example.beatyourbestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText emailLogin;
    EditText passwordLogin;
    Button loginButton;
    TextView registerButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        //loginButton = findViewById(R.id.LoginBtn);
        registerButton = findViewById(R.id.RegisterBtn);


    }

    private void loginUser() {
        // Get the user input from EditText fields
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        // Perform login logic here
        // You can add validation and API calls to authenticate the user

        // Example: Display a toast message
        Toast.makeText(this, "Login is in progress...", Toast.LENGTH_SHORT).show();
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
