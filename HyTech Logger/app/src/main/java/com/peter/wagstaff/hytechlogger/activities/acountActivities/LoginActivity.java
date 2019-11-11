package com.peter.wagstaff.hytechlogger.activities.acountActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.R;
import com.peter.wagstaff.hytechlogger.activities.MainActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//Presenter for logging in
public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword; //Fields needed to login
    private FirebaseAuth auth;  //Firebase authentication
    private ProgressBar progressBar;    //I forgot the exact purpose of this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //If the user is already logged in and has not just logged out, go to the main activity
        checkLoggedIn();

        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);

        //Set action for signup button
        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        //Set action for reset button
        findViewById(R.id.btn_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        //Set action for login button
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get supplied account information
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                //Make sure the fields are not empty, display error if necessary
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);    //I forgot what this does

                //Try to sign in with credentials
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    //Display error if not successful
                                    Toast.makeText(LoginActivity.this, "Login failed, check username and password", Toast.LENGTH_LONG).show();
                                } else {
                                    //Move to main activity if successful
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    /**
     * Check if the user is logged, skip to main activity if they are
     */
    private void checkLoggedIn() {
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null && !GlobalVariables.justLoggedOut) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        GlobalVariables.justLoggedOut = false;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}