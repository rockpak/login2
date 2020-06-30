package com.bmlynarkiewicz.excelentChild;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;


public class childDetailsActivity extends AppCompatActivity {

    EditText txtName;
    CheckBox c1, c2, c3, c4;


    Button btnSubmit;
    Button btnLogout;
    Button btnBack;
    Button btnOther;
    Button btnEncourage;
    Button btnSurprise;
    FirebaseDatabase database;
    DatabaseReference reff;
    Member member;
    int maxid=0;


  /*  FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;

    public childDetailsActivity(FirebaseAuth.AuthStateListener mAuthStateListener) {
        this.mAuthStateListener = mAuthStateListener;
    } */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_details);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        assert user != null;
        String uid = user.getUid();
        reff = database.getInstance().getReference().child(uid);

        member = new Member();

        txtName = findViewById(R.id.childName);
        c1 = findViewById(R.id.homeWork);
        c2 = findViewById(R.id.eating);
        c3 = findViewById(R.id.playing);
        c4 = findViewById(R.id.phone);

        btnSubmit = findViewById(R.id.submit);
        btnLogout = findViewById(R.id.logout);
        btnBack = findViewById(R.id.back);
        btnOther = findViewById(R.id.otherChild);
        btnEncourage = findViewById(R.id.encourage);
        btnSurprise = findViewById(R.id.surprise);

        final String d1 = "Homework";
        final String d2 = "Eating Habit";
        final String d3 = "Excessive Playing";
        final String d4 = "Excessive Phone Uses";









       // reff = FirebaseDatabase.getInstance().getReference().child("Member");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    maxid= (int) dataSnapshot.getChildrenCount();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(childDetailsActivity.this, LoginActivity.class);
                startActivity(intToMain);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                Intent intToMain = new Intent(childDetailsActivity.this, HomeActivity.class);
                startActivity(intToMain);
            }
        });
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                Intent intToMain = new Intent(childDetailsActivity.this, childDetailsActivity.class);
                startActivity(intToMain);
            }
        });
        btnEncourage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                Intent intToMain = new Intent(childDetailsActivity.this, EncourageActivity.class);
                startActivity(intToMain);
            }
        });
        /*
        btnSurprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                Intent intToMain = new Intent(childDetailsActivity.this, SurpriseActivity.class);
                startActivity(intToMain);
            }
        });

         */
        btnSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                member.setName(txtName.getText().toString().trim());
                if (c1.isChecked()){
                    member.setCat1(d1);
                }
                else{}
                if (c2.isChecked()){
                    member.setCat2(d2);
                }
                else{}
                if (c3.isChecked()){
                    member.setCat3(d3);
                }
                else{}
                if (c4.isChecked()){
                    member.setCat4(d4);
                }
                else{}
                //reff.push().setValue(member);
                reff.child(String.valueOf(maxid+1)).setValue(member);

                Toast.makeText(childDetailsActivity.this, "Data Inserted successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
