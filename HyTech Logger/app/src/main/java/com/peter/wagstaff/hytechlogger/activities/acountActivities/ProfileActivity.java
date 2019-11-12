package com.peter.wagstaff.hytechlogger.activities.acountActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
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

//Presenter for viewing and modifying a HyTech Logger account
public class ProfileActivity extends AppCompatActivity {

    private TextView userId;    //TextView to display username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userId = findViewById(R.id.username_textView);
        Button changeUsernameButton = findViewById(R.id.change_username_button);
        Button changePasswordButton = findViewById(R.id.change_password_button);
        Button changeBranchButton = findViewById(R.id.change_branch_button);
        Button deleteUserButton = findViewById(R.id.delete_user_button);
        Button signOutButton = findViewById(R.id.sign_out_button);

        //Sets the username to the username associated with the current user in Firebase
        userId.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        //Finalize dialog boxes
        final InputBox usernameInputDialog =  getUsernameInputDialog();
        final PasswordInputBox passwordInputDialog = getPasswordInputDialog();
        final InputBox rootInputDialog = getBranchInputDialog();

        //Set action for changeUsernameButton
        changeUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameInputDialog.show();
            }
        });

        //Set action for changePasswordButton
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordInputDialog.show();
            }
        });

        //Set action for changeBranchButton
        changeBranchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootInputDialog.show();
            }
        });

        //Set action for deleteUserButton
        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get current user
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    //Delete the user
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Display if deletion succeeded
                                        Toast.makeText(ProfileActivity.this, "Your profile is deleted", Toast.LENGTH_SHORT).show();
                                        //Set justLoggedOut to true and go to login page
                                        GlobalVariables.justLoggedOut = true;
                                        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                                        finish();
                                    } else {
                                        //Display if deletion failed
                                        Toast.makeText(ProfileActivity.this, "Failed to delete your account, try signing out and back in", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        //Set action for signoutButton
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sign out
                FirebaseAuth.getInstance().signOut();
                //Set justLoggedOut to true and go to login page
                GlobalVariables.justLoggedOut = true;
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    /**
     * Returns a properly configured InputBox
     * @return InputBox capable of changing the user's username
     */
    private InputBox getUsernameInputDialog() {
        return new InputBox(ProfileActivity.this, "Enter New Username", "", "") {
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
    }

    /**
     * Returns a properly configured InputBox
     * @return InputBox capable of changing the database branch
     */
    private InputBox getBranchInputDialog() {
        InputBox branchInput = new InputBox(ProfileActivity.this, "Enter New Database Branch", "Current Branch: " + GlobalVariables.databaseBranch, "") {
            @Override
            public boolean onPositiveClicked(String userInput) {
                if(!InputVerification.verifyRoot(userInput)) {
                    Toast.makeText(ProfileActivity.this, "Invalid Branch", Toast.LENGTH_SHORT).show();
                    return false;
                }
                //Store the new root for this instance of the app
                GlobalVariables.databaseBranch = userInput.trim();

                //Store the new root for future instances of the app
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("HYTECH_LOGGER_PREFS", 0).edit();
                editor.putString("databaseBranch", GlobalVariables.databaseBranch);
                editor.apply();

                Toast.makeText(ProfileActivity.this, "Branch set to " + GlobalVariables.databaseBranch, Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        branchInput.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        return branchInput;
    }

    /**
     * Returns a properly configured PasswordInputBox
     * @return PasswordInputBox capable of changing the user's password
     */
    private PasswordInputBox getPasswordInputDialog() {
        return new PasswordInputBox(ProfileActivity.this, "Enter New Password", "", "") {
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
    }
}
