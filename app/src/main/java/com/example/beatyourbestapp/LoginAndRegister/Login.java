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

import com.example.beatyourbestapp.MainMenu;
import com.example.beatyourbestapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText emailLogin;
    EditText passwordLogin;
    Button loginButton;
    TextView registerButton;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(Login.this, MainMenu.class);
//            startActivity(intent);
//            finish();
//        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        registerButton = findViewById(R.id.RegisterBtn);


    }

    private void loginUser() {
        // Get the user input from EditText fields
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        // Perform login logic here
        progressBar.setVisibility(View.VISIBLE);
        // Get the user input from EditText fields

        // Perform registration logic here
        // You can add validation and API calls to register the user
        if(TextUtils.isEmpty(email)){
            Toast.makeText(Login.this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(Login.this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        // You can add validation and API calls to authenticate the user

        // Example: Display a toast message
        Toast.makeText(this, "Login is in progress...", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                         Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show(
                            );
                         Intent intent = new Intent(Login.this, MainMenu.class);
                         startActivity(intent);
                        finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void onRegisterClick(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void onLoginSuccess(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
}
