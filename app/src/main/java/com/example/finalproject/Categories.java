package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Categories extends AppCompatActivity {

    Button viewbtn,viewbtn2,viewbtn3,contact1,contact2,contact3;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
        viewbtn = findViewById(R.id.view_ad);
        viewbtn2 = findViewById(R.id.view_ad2);
        viewbtn3 = findViewById(R.id.view_ad3);
        contact1 = findViewById(R.id.contactSeller);
        contact2 = findViewById(R.id.contactSeller2);
        contact3 = findViewById(R.id.contactSeller3);


        contact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Categories.this, ContactSeller.class);
                startActivity(i);
                finish();
            }
        });

        contact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Categories.this, ContactSeller.class);
                startActivity(i);
                finish();
            }
        });

        contact3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Categories.this, ContactSeller.class);
                startActivity(i);
                finish();
            }
        });


        imageView = findViewById(R.id.back);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Categories.this, ViewAd.class);
                startActivity(i);
                finish();
            }
        });
        viewbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Categories.this, ViewAd.class);
                startActivity(i);
                finish();
            }
        });

        viewbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Categories.this, ViewAd.class);
                startActivity(i);
                finish();
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
                Intent intent1 = new Intent(Categories.this, Home.class);
                this.startActivity(intent1);
                return true;
            case R.id.categoryMenu:
            Intent intent2 = new Intent(Categories.this, Categories.class);
                this.startActivity(intent2);
                return true;
            case R.id.accountMenu:
                Intent intent3 = new Intent(Categories.this, MyAccount.class);
                this.startActivity(intent3);
                return true;
            case R.id.contactMenu:
                Intent intent4 = new Intent(Categories.this, ContactUs.class);
                this.startActivity(intent4);
                return true;
            case R.id.loginMenu:
                Intent intent5 = new Intent(Categories.this, MainActivity.class);
                this.startActivity(intent5);
                return true;
            case R.id.registerMenu:
                Intent intent6 = new Intent(Categories.this, Register.class);
                this.startActivity(intent6);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}