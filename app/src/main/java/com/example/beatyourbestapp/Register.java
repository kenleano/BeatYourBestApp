package com.example.beatyourbestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordAgain;
    private Button registerButton;
    private TextView loginLink;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordAgain = findViewById(R.id.editTextPasswordAgain);
        registerButton = findViewById(R.id.RegisterAccountBtn);
        loginLink = findViewById(R.id.LoginLink);

        // Set click listener for Register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform registration logic here
                registerUser();
            }
        });

        // Set click listener for Login link
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Login activity
                openLoginActivity();
            }
        });
    }

    private void registerUser() {
        // Get the user input from EditText fields
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextPasswordAgain.getText().toString().trim();

        // Perform registration logic here
        // You can add validation and API calls to register the user

        // Example: Display a toast message
        Toast.makeText(this, "Registration is in progress...", Toast.LENGTH_SHORT).show();
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}

