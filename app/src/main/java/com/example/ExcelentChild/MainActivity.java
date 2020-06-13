package com.example.ExcelentChild;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText cName, cPhone, cEmail, cPass, cRepeatPass;
    private Button submit, goback;
    private ProgressBar regProgressBar;
    private FirebaseDatabase database;
    private DatabaseReference mRef;
    private static final String TAG = "NewUserActivity";
    private User user;
    private static final Pattern PHONE_PATTERN = Pattern.compile("(0)?[8][0-9]{8}");
    //  private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
       // "(?=.[0-9])" +  //at least 1 digit
  //    "(?=.[a-z])" +  //at least 1 lowercase letter
      // "(?=.*[A-Z])" + //at least 1 uppercase letter
    //   "(?=.[@#$%^&\\-+=!_\\[\\]\\{\\}~'/><.,;:?\\(\\)])" +   //at least 1 special character
    //   "(?=\\S+$)" +   //no white spaces
      //  "$");

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        regProgressBar = findViewById(R.id.progressBar);
        // login Activity
        goback = (Button) findViewById(R.id.goBack);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // button will take to login Activity
                Intent i2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i2);

            }
        });
        submit = (Button) findViewById(R.id.new_Reg);
        cName = (EditText) findViewById(R.id.eName);
        cPhone = (EditText) findViewById(R.id.Phone);
        cEmail = (EditText) findViewById(R.id.Email);
        cPass = (EditText) findViewById(R.id.ePassword);
        cRepeatPass = (EditText) findViewById(R.id.rPassword);

        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("User");

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sstoring user info into variables
                String name = cName.getText().toString();
                String phone = cPhone.getText().toString();
                String email = cEmail.getText().toString();
                String password = cPass.getText().toString();

                user = new User(email, name, phone, password);
                registerUser(email, password);
            }
        });
    }
    private boolean validateName(){
        String nameInput = cName.getText().toString().trim();

        if(nameInput.isEmpty()){     // if name field is empty, show error
            cName.setError("Name is required");
            return false;
        }
        else if(nameInput.length()<3) {  // if name length is less than 3
            cName.setError("Name is too short");
            return false;
        }
        else if(nameInput.length()>20){  // if name length is too long
            cName.setError("Name is too long");
            return false;
        }
        else{
            cName.setError(null);
            return true;
        }
    }

    private boolean validatePhone(){    // checkig validate phone(only irish phone)
        String phoneInput = cPhone.getText().toString().trim();

        if(phoneInput.isEmpty()){  // if phone field is empty
            cPhone.setError("Phone is required");
            return false;
        }
        else if(phoneInput.length()!=10){
            cPhone.setError("Phone is not valid");
            return false;
        }
        else if(!PHONE_PATTERN.matcher(phoneInput).matches()){
            cPhone.setError("Phone is not valid");
            return false;
        }
        else{
            cPhone.setError(null);
            return true;
        }
    }

    private boolean validateEmail(){     //checking validate email
        String emailInput = cEmail.getText().toString().trim();

        if(emailInput.isEmpty()){    // if email field is empty
            cEmail.setError("E-mail is required");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            cEmail.setError("Please enter a valid e-mail address");
            return false;
        }
        else{
            cEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String passInput = cPass.getText().toString().trim();

        if(passInput.isEmpty()){  // if password field is empty
            cPass.setError("Password is required");
            return false;
        }
       //  else if(!PASSWORD_PATTERN.matcher(passInput).matches()){
        //  cPass.setError("Password should contain at least one uppercase, one lowercase, one special character and one number");
       //   return false;
      //  }
        else if(passInput.length()<8){  // if password length is too less than 8
            cPass.setError("Password should be at least 8 characters long");
            return false;
        }
        else {
            cPass.setError(null);
            return true;
        }
    }

    private boolean validateRepeatPass(){   // checking the repeat password
        String repeatPassInput = cRepeatPass.getText().toString().trim();
        String pass = cPass.getText().toString().trim();

        if(repeatPassInput.isEmpty()){
            cRepeatPass.setError("Confirm password is required");
            return false;
        }
        else if(!repeatPassInput.equals(pass)){
            cRepeatPass.setError("Passwords do not match");
            return false;
        }
        else{
            cRepeatPass.setError(null);
            return true;
        }
    }

    private void registerUser(String email, String password){
        //Check if info provided is valid
        if(!validateName() | !validatePhone() | !validateEmail() | !validatePassword() | !validateRepeatPass()){
            return;
        }

        //Authenticating user
        else{

            regProgressBar.setVisibility(View.VISIBLE);
            //creating user
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //Sign in successful, update UI with the signed-in user's info
                        Log.d(TAG, "Registration successful");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    }
                    else{
                        //If fails, display message to the user
                        Log.w(TAG, "Authentication failed", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
                        regProgressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }


    //Insert user info into database
    private void updateUI(FirebaseUser currentUser){
        String uid = currentUser.getUid();
        mRef.child(uid).setValue(user);
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));  // if user registered, start home activity
    }



}