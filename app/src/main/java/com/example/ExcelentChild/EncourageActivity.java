package com.example.ExcelentChild;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

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
import java.util.TreeSet;

public class EncourageActivity extends AppCompatActivity{


    private final String TAG = EncourageActivity.class.getSimpleName();
    Spinner spinner, spinner2, spinner3;

    EditText editText;
    Button button;
    TextView textView;
    String text , yourName;
    Behaviour behav;

    ValueEventListener listener;
    ArrayAdapter<String>adapter;
    ArrayAdapter<String>adapter2;
    ArrayAdapter<String>adapter3;
    ArrayList<String> spinnerDataList;
    ArrayList<String> spinnerDataList2;
    ArrayList<String> spinnerDataList3;

    Button btnLogout;
    Button btnBack;
    Button btnChildDetails;
    Button btnSurprise;
    DatabaseReference reference, behaviourRef;
    FirebaseDatabase database;


    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encourage);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);



        spinner = findViewById(R.id.mySpinner);
        spinner2 = findViewById(R.id.mySpinner2);
        spinner3 = findViewById(R.id.mySpinner3);

        btnLogout = findViewById(R.id.logout);
        btnBack = findViewById(R.id.back);
        btnChildDetails = findViewById(R.id.childDetils);
        btnSurprise = findViewById(R.id.surprise);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        database = FirebaseDatabase.getInstance();
        assert user != null;
        String uid = user.getUid();
        reference = database.getReference().child(uid);
        behaviourRef = database.getReference("Behaviour");

        spinnerDataList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(EncourageActivity.this,android.R.layout.simple_spinner_dropdown_item,spinnerDataList);
        spinner.setAdapter(adapter);
        retrieveData();

        spinnerDataList2 = new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(EncourageActivity.this,android.R.layout.simple_spinner_dropdown_item,spinnerDataList2);
        spinner2.setAdapter(adapter2);

        spinnerDataList3 = new ArrayList<>();
        adapter3 = new ArrayAdapter<String>(EncourageActivity.this,android.R.layout.simple_spinner_dropdown_item,spinnerDataList3);
        spinner3.setAdapter(adapter3);




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String childSelected = parent.getSelectedItem().toString();

                retrieveData2(childSelected);

                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        final String behaviourSelected = parent.getSelectedItem().toString();

                        retrieveData3(behaviourSelected);

                        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                final String value = parent.getSelectedItem().toString();


                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot ds: dataSnapshot.getChildren()){
                                                    updateChildBehaviour(ds, behaviourSelected, childSelected, value);
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(EncourageActivity.this, LoginActivity.class);
                startActivity(intToMain);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                Intent intToMain = new Intent(EncourageActivity.this, HomeActivity.class);
                startActivity(intToMain);
            }
        });
        btnChildDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance();
                Intent intToMain = new Intent(EncourageActivity.this, childDetailsActivity.class);
                startActivity(intToMain);
            }
        });


    }

    public void updateChildBehaviour(final DataSnapshot ds, final String behaviourSelected, final String childSelected, final String value){

        Member member = ds.getValue(Member.class);
        String child = member.getName();
        String cat1 = member.getCat1();
        String cat2 = member.getCat2();
        String cat3 = member.getCat3();
        String cat4 = member.getCat4();



        if(childSelected.equals(child)){

            String key = ds.getKey();
            if(behaviourSelected.equals(cat1)){
                member.setCat1value(value);
            }
            else if(behaviourSelected.equals(cat2)){
                member.setCat2value(value);
            }
            else if(behaviourSelected.equals(cat3)){
                member.setCat3value(value);
            }
            else{
                member.setCat4value(value);
            }


            HashMap<String, Object> has = new HashMap<>();
            HashMap<String, Object> hasTWo = member.toMap();

            if(member.getDates() == null){
                HashMap<String, Object> newDatesTwo = new HashMap<>();

                HashMap<String, Object> newDates = new HashMap<>();

                newDatesTwo.put(behaviourSelected, value);
                newDates.put(toDay(new Date().getDay()), newDatesTwo);
                hasTWo.put("dates", newDates);
                has.put(key, hasTWo);

            }else {
                HashMap<String, Object> dates = member.getDates();
                HashMap<String, Object> newDatesTwo = new HashMap<>();
                hasTWo.remove("dates");


                Log.i(TAG, dates.toString());

                String day = toDay(new Date().getDay());

                if(dates.containsKey(day)){
                    HashMap<String, Object> val = (HashMap<String, Object>) dates.get(day);
                    val.put(behaviourSelected, value);
                    dates.remove(day);
                    dates.put(day, val);
                }else{
                    newDatesTwo.put(behaviourSelected, value);
                    dates.put(day, newDatesTwo);
                }


                Log.i(TAG, dates.toString());

                hasTWo.put("dates", dates);
                has.put(key, hasTWo);
            }

            reference.updateChildren(has).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i(TAG, "Updated Succesfull");
                    Toast.makeText(getApplicationContext(), "Data inserted successfully", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i(TAG, "Failed to updated object");

                    Toast.makeText(getApplicationContext(), "Failed to insert data successfully", Toast.LENGTH_LONG).show();

                }
            });


        }
    }

    private String toDay(int day){
        switch (day){
            case 0: return "Sunday";
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
        }
        return "";
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

    public void retrieveData2(final String childSelected){

        listener =  reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> list = new HashSet();
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    Member mem = item.getValue(Member.class);
                    String childName = mem.getName();
                    if(childSelected.equals(childName)){
                        String cat1 = mem.getCat1();
                        String cat2 = mem.getCat2();
                        String cat3 = mem.getCat3();
                        String cat4 = mem.getCat4();
                        if(cat1!=null){
                            list.add(cat1);
                        }
                        if(cat2!=null){
                            list.add(cat2);
                        }
                        if(cat3!=null){
                            list.add(cat3);
                        }
                        if(cat4!=null){
                            list.add(cat4);
                        }
                    }
                }
                spinnerDataList2.clear();
                spinnerDataList2.addAll(list);
                //adapter.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void retrieveData3 (final String behaviourSelected){
        listener = behaviourRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> encList = new HashSet();
                for(DataSnapshot item: dataSnapshot.getChildren()){
                    behav = item.getValue(Behaviour.class);
                    String behaviour = item.getKey();

                    if(behaviourSelected.equals(behaviour)){
                        String value1 = behav.getValue1();
                        String value2 = behav.getValue2();
                        String value3 = behav.getValue3();

                        encList.add(value1);
                        encList.add(value2);
                        encList.add(value3);
                    }
                }

                spinnerDataList3.clear();
                spinnerDataList3.addAll(encList);
                adapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
