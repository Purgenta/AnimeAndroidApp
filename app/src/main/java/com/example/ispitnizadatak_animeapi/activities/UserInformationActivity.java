package com.example.ispitnizadatak_animeapi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.ispitnizadatak_animeapi.R;
import com.example.ispitnizadatak_animeapi.fragments.InvalidInformation;

public class UserInformationActivity extends AppCompatActivity {
    private EditText username;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        initComponents();
        Button submitUserInformation = findViewById(R.id.buttonSubmitUserInfo);
        submitUserInformation.setOnClickListener(view -> this.addUserLoginFormValidation());
    }

    private void initComponents() {
        username = findViewById(R.id.userUsername);
        email = findViewById(R.id.userEmail);
    }

    private void addUserLoginFormValidation() {

        String submittedUsername = username.getText().toString().trim();
        String submittedEmail = email.getText().toString().trim();
        if (submittedUsername.length() < 5 || !submittedUsername.matches("^[a-z]{5,}$")) {
            openDialogFragment("Your username must be at least 5 characters long without number or uppercase ");
        } else if (!submittedEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            openDialogFragment("Your email must be a valid email");
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("MainActivitySharedPreferences", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", submittedUsername);
            editor.putString("email", submittedEmail);
            editor.apply();
            Intent intent = new Intent(UserInformationActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

    private void openDialogFragment(String message) {
        DialogFragment dialog = new InvalidInformation(message);
        dialog.show(getSupportFragmentManager(), "invalid information fragment");
    }
}