package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    ImageView imageView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;
    SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        searchView = (SearchView) findViewById(R.id.searchView);
        imageView = findViewById(R.id.back);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                Intent intent1 = new Intent(Home.this, Home.class);
                this.startActivity(intent1);
                return true;
            case R.id.categoryMenu:
                Intent intent2 = new Intent(Home.this, Categories.class);
                this.startActivity(intent2);
                return true;
            case R.id.accountMenu:
                Intent intent3 = new Intent(Home.this, MyAccount.class);
                this.startActivity(intent3);
                return true;
            case R.id.contactMenu:
                Intent intent4 = new Intent(Home.this, ContactUs.class);
                this.startActivity(intent4);
                return true;
            case R.id.loginMenu:
                Intent intent5 = new Intent(Home.this, MainActivity.class);
                this.startActivity(intent5);
                return true;
            case R.id.registerMenu:
                Intent intent6 = new Intent(Home.this, Register.class);
                this.startActivity(intent6);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
