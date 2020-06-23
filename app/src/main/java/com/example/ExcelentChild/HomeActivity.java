package com.example.ExcelentChild;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.childDetils).setOnClickListener(this);
        findViewById(R.id.encourage).setOnClickListener(this);
        findViewById(R.id.childSummary).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.childDetils:{
                startActivity(new Intent(HomeActivity.this, childDetailsActivity.class));
                break;
            }
            case R.id.encourage:{
                startActivity(new Intent(HomeActivity.this, EncourageActivity.class));
                break;
            }
            case R.id.logout:{
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
            }
            case R.id.childSummary:{
                startActivity(new Intent(HomeActivity.this, SummaryActivity.class));
                break;
            }
        }
    }
}
