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
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    // member variables
    public static final String TAG = "SignUpActivity";
    EditText etUsername, etPassword, etEmail, etUniversity;
    Button btnSignUp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // defining vars
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etUniversity = findViewById(R.id.etUniversity);
        btnSignUp = findViewById(R.id.btnSignUp);


        // button actions
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String university = etUniversity.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty() || university.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Missing Inputs", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Empty field");
                }

                // Sign up user
                signUpUser(username, password, email, university);
            }
        });


    }

    private void signUpUser(String username, String password, String email, String university) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.put("university", university);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    // Error with Callback
                    Toast.makeText(SignUpActivity.this, "Sign Up Error", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Issue with Sign Up", e);
                    return;
                }

                // Callback Successful
                goMainActivity();
                Toast.makeText(SignUpActivity.this, "Signed Up Successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void goMainActivity() {
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
        Log.i(TAG, "Sign-Up Successful. Going to Main Activity");
        finish();
    }
}