package com.example.beatyourbestapp.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beatyourbestapp.LoginAndRegister.Login;
import com.example.beatyourbestapp.MainMenu;
import com.example.beatyourbestapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordAgain;
    private Button registerButton;
    private TextView loginLink;

    FirebaseAuth mAuth;
    ProgressBar progressBar;
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intent);
            finish();
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordAgain = findViewById(R.id.editTextPasswordAgain);
        registerButton = findViewById(R.id.RegisterAccountBtn);
        loginLink = findViewById(R.id.LoginLink);
        progressBar = findViewById(R.id.progressBar);

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
        progressBar.setVisibility(View.VISIBLE);
        // Get the user input from EditText fields
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextPasswordAgain.getText().toString().trim();

        // Perform registration logic here
        // You can add validation and API calls to register the user
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(this, "Please confirm password", Toast.LENGTH_LONG).show();
            return;
        }

        // Example: Display a toast message
        Toast.makeText(this, "Registration is in progress...", Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility( View.GONE );
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Register.this, "Account created.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}

