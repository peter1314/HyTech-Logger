package com.peter.wagstaff.hytechlogger.activities.acountActivities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.peter.wagstaff.hytechlogger.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//Presenter for resetting a password
public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;    //Email associated with account to reset
    private FirebaseAuth auth;  //Firebase Authentication
    private ProgressBar progressBar;    //I forgot what this does

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputEmail = findViewById(R.id.email);
        progressBar = findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        //Set action for back button
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Set action for reset button
        findViewById(R.id.btn_reset_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get the entered email
                String email = inputEmail.getText().toString().trim();

                //Display error if the email is invalid
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                //I forgot what this does
                progressBar.setVisibility(View.VISIBLE);

                //Attempt to reset the account associated with the email
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //Display message if successful
                                    Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    //Display message if failed
                                    Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
