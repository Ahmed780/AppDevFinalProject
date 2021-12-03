package com.example.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Category  extends AppCompatActivity {

    int select_photo = 1;
    Uri uri;
    Button imagebtn, submitbtn, viewbtn;
    EditText title, price, description;
    ImageView imageView;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_category);
        imagebtn = findViewById(R.id.image_button);
        submitbtn = findViewById(R.id.submit_btn);
        viewbtn = findViewById(R.id.view_btn);
        title = findViewById(R.id.EditTitle);
        price = findViewById(R.id.editPrice);
        recyclerView = findViewById(R.id.recyclerView);
        description = findViewById(R.id.editDescription);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Ad");

        imageView = findViewById(R.id.back);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAdData();
            }
        });

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Category.this, ViewAd.class);
                startActivity(i);
                finish();
            }
        });

        imageView = findViewById(R.id.image);
        getSupportActionBar().hide();

        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, select_photo);
            }
        });
    }

    private void insertAdData() {

        String adTitle = title.getText().toString();
        String adPrice = price.getText().toString();
        String adDes = description.getText().toString();

        if(TextUtils.isEmpty(adTitle)){
            title.setError("Please enter a title");
            return;
        }

        if(TextUtils.isEmpty(adPrice)){
            price.setError("Please enter a price");
            return;
        }
        AdModel adModel = new AdModel(adTitle, adPrice, adDes);
        databaseReference.push().setValue(adModel);
        Toast.makeText(Category.this, "Ad posted!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Category.this,Categories.class));
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == select_photo && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                Intent intent1 = new Intent(Category.this, Home.class);
                this.startActivity(intent1);
                return true;
            case R.id.categoryMenu:
                Intent intent2 = new Intent(Category.this, Categories.class);
                this.startActivity(intent2);
                return true;
            case R.id.accountMenu:
                Intent intent3 = new Intent(Category.this, MyAccount.class);
                this.startActivity(intent3);
                return true;
            case R.id.contactMenu:
                Intent intent4 = new Intent(Category.this, ContactUs.class);
                this.startActivity(intent4);
                return true;
            case R.id.loginMenu:
                Intent intent5 = new Intent(Category.this, MainActivity.class);
                this.startActivity(intent5);
                return true;
            case R.id.registerMenu:
                Intent intent6 = new Intent(Category.this, Register.class);
                this.startActivity(intent6);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
