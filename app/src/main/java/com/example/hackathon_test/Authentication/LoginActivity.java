package com.example.hackathon_test.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hackathon_test.Activities.MainActivity;
import com.example.hackathon_test.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    // member variables
    public static final String TAG = "LoginActivity";
    EditText etUsername, etPassword;
    Button btnLogin, btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // if user is already logged in, go to main meny
        if (ParseUser.getCurrentUser() != null)
            goMainActivity();

        // defining vars
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);



        // defining button clicks
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim(),
                       password = etPassword.getText().toString().trim();

                if (username.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please input Both Username and Password", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Empty field");
                }

                // Authenticate User
                loginUser(username, password);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                Log.i(TAG, "Going to Sign Up Activity");

                // Take username and password if there to new activity
                // fill in data in new activity
                // ask for more info
                // create new user
                // comeback to login

            }
        });



    }



    // authenticates user with ParseDatabase
    public void loginUser(String username, String password) {

        Log.i(TAG, "Username: " + username + ", Password: " + password);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    // TODO: better error handling
                    Toast.makeText(LoginActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Issue with Login", e);
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Logged In!");

            }
        });


    }

    // takes to Main Menu
    private void goMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        Log.i(TAG, "Login Successful");
        finish();
    }


}