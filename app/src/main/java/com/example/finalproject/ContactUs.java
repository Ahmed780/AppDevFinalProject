package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactUs extends AppCompatActivity {

    EditText editText_name, editText_email, editText_subject, editText_message;
    Button button_send;
    ImageView imageView;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);

        imageView = findViewById(R.id.back);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editText_name = findViewById(R.id.editName);
        editText_email = findViewById(R.id.editEmail);
        editText_subject = findViewById(R.id.editSubject);
        editText_message = findViewById(R.id.editSubject);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Contact");
        button_send = findViewById(R.id.sendBtn);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertContactData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.homeMenu:
                Intent intent1 = new Intent(ContactUs.this, Home.class);
                this.startActivity(intent1);
                return true;
            case R.id.categoryMenu:
                Intent intent2 = new Intent(ContactUs.this, Categories.class);
                this.startActivity(intent2);
                return true;
            case R.id.accountMenu:
                Intent intent3 = new Intent(ContactUs.this, MyAccount.class);
                this.startActivity(intent3);
                return true;
            case R.id.contactMenu:
                Intent intent4 = new Intent(ContactUs.this, ContactUs.class);
                this.startActivity(intent4);
                return true;
            case R.id.loginMenu:
                Intent intent5 = new Intent(ContactUs.this, MainActivity.class);
                this.startActivity(intent5);
                return true;
            case R.id.registerMenu:
                Intent intent6 = new Intent(ContactUs.this, Register.class);
                this.startActivity(intent6);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void insertContactData() {

        String name = editText_name.getText().toString();
        String email = editText_email.getText().toString();
        String subject = editText_subject.getText().toString();
        String message = editText_message.getText().toString();

        if(TextUtils.isEmpty(name)){
            editText_name.setError("Name is required");
            return;
        }

        if(TextUtils.isEmpty(email)){
            editText_email.setError("Email is required");
            return;
        }
        ContactModelClass contactModel = new ContactModelClass (name,email,subject,message);
        databaseReference.push().setValue(contactModel);
        Toast.makeText(ContactUs.this, "Message sent!", Toast.LENGTH_SHORT).show();
    }
}
