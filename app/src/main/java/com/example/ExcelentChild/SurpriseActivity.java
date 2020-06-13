package com.example.ExcelentChild;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SurpriseActivity extends AppCompatActivity {
    Button btnLogout;
    Button btnBack;
    Button btnEncourage;
    Button btnChildDetails;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surprise);

        btnLogout = findViewById(R.id.logout);
        btnBack = findViewById(R.id.back);
        btnEncourage = findViewById(R.id.encourage);
        btnChildDetails = findViewById(R.id.childDetils);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(SurpriseActivity.this, LoginActivity.class);
                startActivity(intToMain);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                Intent intToMain = new Intent(SurpriseActivity.this, HomeActivity.class);
                startActivity(intToMain);
            }
        });
        btnEncourage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                Intent intToMain = new Intent(SurpriseActivity.this, EncourageActivity.class);
                startActivity(intToMain);
            }
        });
        btnChildDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                Intent intToMain = new Intent(SurpriseActivity.this, childDetailsActivity.class);
                startActivity(intToMain);
            }
        });
    }
}
