package com.example.ExcelentChild;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;





import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SummaryActivity extends AppCompatActivity {

    Spinner spinner;
    TextView textView;
    Behaviour behav;

    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;

    Button btnLogout;
    Button btnBack;
    Button btnEncourage;
    Button btnChildDetails;
    DatabaseReference reference, behaviourRef;
    FirebaseDatabase database;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        btnLogout = findViewById(R.id.logout);
        btnBack = findViewById(R.id.back);
        btnEncourage = findViewById(R.id.encourage);
        btnChildDetails = findViewById(R.id.childDetils);

        spinner = findViewById(R.id.mySpinner);
        textView = findViewById(R.id.textView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        database = FirebaseDatabase.getInstance();
        assert user != null;
        String uid = user.getUid();
        reference = database.getReference().child(uid);
        behaviourRef = database.getReference("Behaviour");

        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(SummaryActivity.this,android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
        spinner.setAdapter(adapter);
        retrieveData();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String childSelected = parent.getSelectedItem().toString();

              //  retrieveData2(childSelected);

             //   spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

              //      @Override
               //     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               //         final String behaviourSelected = parent.getSelectedItem().toString();

               //         retrieveData3(behaviourSelected);


                //    }
                 //   @Override
                 //   public void onNothingSelected(AdapterView<?> parent) {

                    }

         //   @Override
         //   public void onNothingSelected(AdapterView<?> parent) {

          //  }
       // });
        //    }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(SummaryActivity.this, LoginActivity.class);
                startActivity(intToMain);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                Intent intToMain = new Intent(SummaryActivity.this, HomeActivity.class);
                startActivity(intToMain);
            }
        });
        btnEncourage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                Intent intToMain = new Intent(SummaryActivity.this, EncourageActivity.class);
                startActivity(intToMain);
            }
        });
        btnChildDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                Intent intToMain = new Intent(SummaryActivity.this, childDetailsActivity.class);
                startActivity(intToMain);
            }
        });
    }

    public void retrieveData (){

        listener =  reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> list = new HashSet();
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    Member mem = item.getValue(Member.class);
                    String name = mem.getName();
                    list.add(name);
                }
                spinnerDataList.clear();
                spinnerDataList.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getSelectedUser(View view){

    }
}
