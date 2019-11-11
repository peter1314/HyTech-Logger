package com.peter.wagstaff.hytechlogger.activities.acountActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.peter.wagstaff.hytechlogger.activities.MainActivity;
import com.peter.wagstaff.hytechlogger.inputs.InputVerification;
import com.peter.wagstaff.hytechlogger.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//Presenter for creating a HyTech Logger account
public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputUsername, inputPassword; //Fields needed to create an account
    private ProgressBar progressBar;    //I forgot the exact purpose of this
    private FirebaseAuth auth;  //Firebase authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputEmail = findViewById(R.id.email);
        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        //Set action for the reset password button
        findViewById(R.id.btn_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        //Set action for the sign in button
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        //Set action for the sign up button
        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get supplied account information
                String email = inputEmail.getText().toString().trim();
                String name = inputUsername.getText().toString();
                String password = inputPassword.getText().toString().trim();

                //Verify information, will display Toast if bad
                if(!InputVerification.verifyEmail(email, getApplicationContext())) {return;}
                if(!InputVerification.verifyUserName(name, getApplicationContext())) {return;}
                if(!InputVerification.verifyPassword(password, getApplicationContext())) {return;}

                progressBar.setVisibility(View.VISIBLE);    //I forgot what this does

                //Creates the user if all inputs are valid
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Display if task succeeded
                                Toast.makeText(SignupActivity.this, "Account Created: " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    //If not, display why
                                    Toast.makeText(SignupActivity.this, "Registration failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //If successful go to the main view
                                    FirebaseUser user = auth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(inputUsername.getText().toString()).build();
                                    user.updateProfile(profileUpdates);
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finish();
                                }
                            }});
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}