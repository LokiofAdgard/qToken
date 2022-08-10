package com.example.utoken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminStocks extends AppCompatActivity {

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_stocks);

        databaseUser = FirebaseDatabase.getInstance().getReference("admin");

        Button btn = (Button) findViewById(R.id.btn);
        EditText petrol = (EditText) findViewById(R.id.petrol);
        EditText diesel = (EditText) findViewById(R.id.diesel);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AdminStocks.this)
                        .setTitle("Confirm Request")
                        .setMessage("Are you sure you want to submit This update?")
                        .setNegativeButton("CANCEL", null)
                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                Toast.makeText(AdminStocks.this, "Request Sent", Toast.LENGTH_SHORT).show();
                                //TODO: Make request

                                int p = Integer.parseInt(petrol.getText().toString());
                                int d = Integer.parseInt(diesel.getText().toString());

                                databaseUser.child("Device3").child("petrol").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        }
                                        else {
                                            //Toast.makeText(AdminStocks.this, String.valueOf(task.getResult().getValue()), Toast.LENGTH_SHORT).show();
                                            String v = String.valueOf(task.getResult().getValue());
                                            Integer v1 = Integer.parseInt(v) + p;
                                            databaseUser.child("Device3").child("petrol").setValue(v1);
                                        }
                                    }
                                });

                                databaseUser.child("Device3").child("diesel").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        }
                                        else {
                                            //Toast.makeText(AdminStocks.this, String.valueOf(task.getResult().getValue()), Toast.LENGTH_SHORT).show();
                                            String v = String.valueOf(task.getResult().getValue());
                                            Integer v1 = Integer.parseInt(v) + d;
                                            databaseUser.child("Device3").child("diesel").setValue(v1);
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