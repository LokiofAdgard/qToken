package com.example.utoken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Request extends AppCompatActivity {

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        String id = getIntent().getStringExtra("id");

        databaseUser = FirebaseDatabase.getInstance().getReference("user");

        Button btn = (Button) findViewById(R.id.btn);

        Spinner dropdown1 = findViewById(R.id.spinner1);
        String[] items1 = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);

        Spinner dropdown2 = findViewById(R.id.spinner2);
        String[] items2 = new String[]{"Petrol", "Diesel"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(Request.this)
                        .setTitle("Confirm Request")
                        .setMessage("Are you sure you want to submit the request?")
                        .setNegativeButton("CANCEL", null)
                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                //Toast.makeText(Request.this, "Request Sent", Toast.LENGTH_SHORT).show();
                                //TODO: Make request


                                databaseUser.child(id).child("approved").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        }
                                        else {
                                            //Toast.makeText(AdminStocks.this, String.valueOf(task.getResult().getValue()), Toast.LENGTH_SHORT).show();
                                            Boolean v = (Boolean) task.getResult().getValue();
                                            if (v) {
                                                Toast.makeText(Request.this, "approved", Toast.LENGTH_SHORT).show();

                                                //TODO: Check availability and approve

                                                Intent myIntent = new Intent(Request.this, User_Home1.class);
                                                Request.this.startActivity(myIntent);
                                            }
                                            else {
                                                Toast.makeText(Request.this, "Not Approved", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            }
                        })
                        .show();
            }
        });
    }
}