package com.peter.wagstaff.hytechlogger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.peter.wagstaff.hytechlogger.inputs.InputVerification;
import com.peter.wagstaff.hytechlogger.GlobalVariables;
import com.peter.wagstaff.hytechlogger.customFragments.dialogs.InputBox;
import com.peter.wagstaff.hytechlogger.customFragments.dialogs.PasswordInputBox;
import com.peter.wagstaff.hytechlogger.R;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final TextView userId = findViewById(R.id.username_textView);
        Button changeUsernameButton = findViewById(R.id.change_username_button);
        Button changePasswordButton = findViewById(R.id.change_password_button);
        Button deleteUserButton = findViewById(R.id.delete_user_button);
        Button signOutButton = findViewById(R.id.sign_out_button);

        userId.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        final InputBox usernameInputDialog = new InputBox(ProfileActivity.this, "Enter New Username", "", "") {
            @Override
            public boolean onPositiveClicked(String userInput) {
                if(!InputVerification.verifyUserName(userInput, getApplicationContext())) {return false;}
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userInput).build();
                user.updateProfile(profileUpdates);
                userId.setText(userInput);
                return true;
            }
        };

        changeUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameInputDialog.show();
            }
        });

        final PasswordInputBox passwordInputDialog = new PasswordInputBox(ProfileActivity.this, "Enter New Password", "", "") {
            @Override
            public boolean onPositiveClicked(String userInput) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(!InputVerification.verifyPassword(userInput, getApplicationContext())) {return false;}

                user.updatePassword(userInput).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Password is updated!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to update password, try signing out and back in", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return true;
            }
        };

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordInputDialog.show();
            }
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfileActivity.this, "Your profile is deleted", Toast.LENGTH_SHORT).show();
                                        GlobalVariables.justLoggedOut = true;
                                        startActivity(new Intent(ProfileActivity.this, LoginPresenter.class));
                                        finish();
                                    } else {
                                        Toast.makeText(ProfileActivity.this, "Failed to delete your account, try signing out and back in", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                GlobalVariables.justLoggedOut = true;
                startActivity(new Intent(ProfileActivity.this, LoginPresenter.class));
                finish();
            }
        });
    }
}
