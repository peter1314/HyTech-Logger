package com.peter.wagstaff.hytechlogger.activities;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.peter.wagstaff.hytechlogger.GlobalFunctions;
import com.peter.wagstaff.hytechlogger.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputUsername, inputPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();

        inputEmail = findViewById(R.id.email);
        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);


        findViewById(R.id.btn_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String name = inputUsername.getText().toString();
                String password = inputPassword.getText().toString().trim();


                if(!GlobalFunctions.checkUserEmailRequirements(email, getApplicationContext())) {return;}
                if(!GlobalFunctions.checkUserNameRequirements(name, getApplicationContext())) {return;}
                if(!GlobalFunctions.checkUserPasswordRequirements(password, getApplicationContext())) {return;}

                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "Account Created: " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Registration failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
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