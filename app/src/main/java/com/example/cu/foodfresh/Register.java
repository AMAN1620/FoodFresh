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

public class Register extends AppCompatActivity {

    //DatabaseReference dataBaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodfresh-f8ce7-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullName = findViewById(R.id.fullname);
        final EditText email = findViewById(R.id.emailReg);
        final EditText mobile = findViewById(R.id.mobileRegister);
        final EditText password = findViewById(R.id.passwordRegister);
        final EditText conPass = findViewById(R.id.confirmPassword);
        final Button registerBtn = findViewById(R.id.registerButton);
        final TextView signInNowBtn = findViewById(R.id.signInNow);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullNameTxt = fullName.getText().toString();
                final String emailTxt = email.getText().toString();
                final String mobileTxt = mobile.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String confirmPassTxt = conPass.getText().toString();

                if(fullNameTxt.isEmpty() || emailTxt.isEmpty() || mobileTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(Register.this,"Something went Wrong",Toast.LENGTH_SHORT).show();
                }else if(!passwordTxt.equals(confirmPassTxt)){
                    Toast.makeText(Register.this,"Password Mismatches",Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // checking if phone number already existed
                            if(snapshot.hasChild(mobileTxt)){
                                Toast.makeText(Register.this,"Phone Number already Registered",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this,Login.class));

                            }else{
                                databaseReference.child("users").child(mobileTxt).child("name").setValue(fullNameTxt);
                                databaseReference.child("users").child(mobileTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(mobileTxt).child("password").setValue(passwordTxt);

                                Toast.makeText(Register.this,"Success",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        signInNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });

    }
}