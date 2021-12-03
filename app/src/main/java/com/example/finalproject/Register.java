package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private TextInputEditText rusername;
    private TextInputEditText remail;
    private TextInputEditText rpassword;
    FirebaseAuth fireAuth;
    String userID;
    Button registerbtn, loginLink;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        rusername = (TextInputEditText) findViewById(R.id.rusername);
        remail = (TextInputEditText) findViewById(R.id.remail);
        rpassword = (TextInputEditText) findViewById(R.id.rpassword);
        loginLink = findViewById(R.id.loginLink);
        getSupportActionBar().hide();
        registerbtn = findViewById(R.id.registerbtn);
        progressBar = findViewById(R.id.progressBar2);
        fireAuth = FirebaseAuth.getInstance();

//        if (fireAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,MainActivity.class);
                startActivity(intent);
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = remail.getText().toString().trim();
                String passowrd = rpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    remail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(passowrd)){
                  rpassword.setError("Password is required");
                    return;
                }

                if(rpassword.length()< 8){
                    rpassword.setError("Password must be 8 at least 8 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                fireAuth.createUserWithEmailAndPassword(email,passowrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser user = fireAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Register.this, "Verification email sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("tag", "Email not sent" + e.getMessage());
//                                    Toast.makeText(SecondActivity.this, "Email not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                            Toast.makeText(Register.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            userID = fireAuth.getCurrentUser().getUid();
//                            DocumentReference documentReference
                            startActivity(new Intent(getApplicationContext(),MyAccount.class));
                        }
                        else{
                            Toast.makeText(Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }


}