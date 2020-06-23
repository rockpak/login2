package com.example.ExcelentChild;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;





import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener {


    private final String TAG = SummaryActivity.class.getSimpleName();
    private Spinner childName, daysSpinner;
    private Map<String,Map<String, Map<String, String>>> summary;
    private final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

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


        DatabaseReference reference = database.getReference(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("name").exists()){
                        names.add(dataSnapshot1.child("name").getValue(String.class));
                    }

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        daysSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days));

    }

    private void retriveDaySummary(){

        if(daysSpinner.getSelectedItem() != null && childName.getSelectedItem() != null){
            String daySelected = (String) daysSpinner.getSelectedItem();
            String selectedChild = (String) childName.getSelectedItem();

            Map<String, Map<String, String>> days = summary.get(selectedChild);

            if(days.isEmpty()){
                showDialog("No Data Has Been Yet Recorded for " + selectedChild);
            }else {
                if (days.containsKey(daySelected)) {
                    StringBuilder stringBuilder = new StringBuilder();

                    for (String keys : days.get(daySelected).keySet()) {
                        stringBuilder.append(keys + " " + days.get(daySelected).get(keys) + "\n");
                    }

                    showDialog(stringBuilder.toString());

                } else {
                    showDialog("No Record Found for the selected day");
                }
            }

        }else{
            showDialog("Please Select Child Name And Date");
        }

    }

    private void showDialog(String message){
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
                startActivity(new Intent(SummaryActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            }
        }
    }
}
