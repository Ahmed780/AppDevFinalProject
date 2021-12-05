package com.example.finalproject;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class Category  extends AppCompatActivity {

    int select_photo = 1;
    Uri uri;
    Button imagebtn, submitbtn, viewbtn;
    EditText title, price, description;
    ImageView imageView;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private ImageView itemPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_category);
//        imagebtn = findViewById(R.id.image_button);
        submitbtn = findViewById(R.id.submit_btn);
        title = findViewById(R.id.EditTitle);
        price = findViewById(R.id.editPrice);
        recyclerView = findViewById(R.id.recyclerView);
        description = findViewById(R.id.editDescription);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Ad");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        imageView = findViewById(R.id.back);
        itemPic = findViewById(R.id.itemPic);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        itemPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAdData();
            }
        });

//        imageView = findViewById(R.id.image);
//        getSupportActionBar().hide();

//        imagebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, select_photo);
//            }
//        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uri = data.getData();
            itemPic.setImageURI(uri);
            uploadPicture();
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference mountainsRef = storageReference.child("images/" + randomKey);
        mountainsRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded.", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed To Upload", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot tasksnapshot) {
                        double progressPercent = (100.00 * tasksnapshot.getBytesTransferred() / tasksnapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent + "%");
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
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == select_photo && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            uri = data.getData();
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                imageView.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
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
