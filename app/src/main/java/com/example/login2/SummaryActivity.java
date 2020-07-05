package com.example.login2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener {


    private final String TAG = SummaryActivity.class.getSimpleName();
    private Spinner childName, daysSpinner;
    private Map<String,Map<String, Map<String, String>>> summary;
    private final String[] behaviours = {"Excessive Phone Uses", "Eating Habit","Excessive Playing","Homework"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        final ArrayList<String> names = new ArrayList<>();

        childName = findViewById(R.id.mySpinner2);
        daysSpinner = findViewById(R.id.mySpinner);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        summary = new HashMap<>();

        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);


        final DatabaseReference reference = database.getReference(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> catsList = null;
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("name").exists()){
                        names.add(dataSnapshot1.child("name").getValue(String.class));
                    }

                    catsList = retriveCat(dataSnapshot1);
                    Map<String, Map<String,String>> days = new HashMap<>();

                    if(dataSnapshot1.child("dates").exists()){
                        for(DataSnapshot dataSnapshot2: dataSnapshot1.child("dates").getChildren()){
                            if(dataSnapshot2.hasChildren()){
                                Map<String, String> habs = new HashMap<>();
                                for(DataSnapshot dataSnapshot3: dataSnapshot2.getChildren()) {
                                    habs.put(dataSnapshot3.getKey(), (String) dataSnapshot3.getValue());
                                }
                                days.put(dataSnapshot2.getKey(), habs);
                            }
                        }
                    }

                    summary.put(dataSnapshot1.child("name").getValue(String.class), days);
                }

                childName.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, names));
                //daysSpinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, catsList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        childName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                daysSpinner.setAdapter(null);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> catsList = null;
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if((dataSnapshot1.child("name").getValue(String.class)).equals(childName.getSelectedItem().toString())) {
                                catsList = retriveCat(dataSnapshot1);
                            }
                        }

                        Log.i(TAG, childName.getSelectedItem().toString());

                        if(catsList != null){

                            daysSpinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, catsList));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private ArrayList<String> retriveCat(DataSnapshot dataSnapshot){

        ArrayList<String> catsList = new ArrayList<>();

        String[] cats = {"cat1","cat2","cat3","cat4"};
        for(String cat : cats) {
            if (dataSnapshot.child(cat).exists()) {
                catsList.add(dataSnapshot.child(cat).getValue(String.class));
            }
        }

        return catsList;
    }

    private void retriveDaySummary(){

        if(daysSpinner.getSelectedItem() != null && childName.getSelectedItem() != null){
            String daySelected = (String) daysSpinner.getSelectedItem();
            String selectedChild = (String) childName.getSelectedItem();

            Map<String, Map<String, String>> days = summary.get(selectedChild);


            if(days != null){
                if(days.isEmpty()) {
                    showDialog("No Data Has Been Yet Recorded for " + selectedChild);
                }else {
                        DialogFragment dialogFragment = new BehaviourDailog(days, daySelected);
                                       dialogFragment.showNow(getSupportFragmentManager(), "dialog");

                }
            }
        }else{
            showDialog("Please Select Child Name And Date");
        }

    }

    public void showDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               dialog.dismiss();
            }
        });
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button4:{
                retriveDaySummary();
                break;
            }
            case R.id.back:{
                startActivity(new Intent(SummaryActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            }
        }
    }

}
