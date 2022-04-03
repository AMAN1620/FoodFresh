package com.example.cu.foodfresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodfresh-f8ce7-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText mobileNum = findViewById(R.id.mobileNumLogin);
        final EditText password = findViewById(R.id.passwordLogin);
        final Button signIn = findViewById(R.id.loginButton);
        final TextView registerNowBtn = findViewById(R.id.registerNow);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneTxt = mobileNum.getText().toString();
                String passwordTxt = password.getText().toString();

                if(phoneTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(Login.this,"Invalid Email or Password",Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(phoneTxt)){
                                final String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);

                                if(getPassword.equals(passwordTxt)){
                                    Toast.makeText(Login.this,"Success",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this,MainActivity.class));
                                    finish();

                                }else{
                                    Toast.makeText(Login.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(Login.this,"Mobile Number not Registered !!",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        registerNowBtn.setOnClickListener(new View.OnClickListener() {  
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });

    }

}