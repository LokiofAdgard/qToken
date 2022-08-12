package com.example.utoken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminHome extends AppCompatActivity {

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        databaseUser = FirebaseDatabase.getInstance().getReference("admin");

        String id = getIntent().getStringExtra("id");

        Button stock = (Button) findViewById(R.id.stock);
        Button scan = (Button) findViewById(R.id.scan);
        TextView pt = (TextView) findViewById(R.id.ptr);
        TextView ds = (TextView) findViewById(R.id.die);

        databaseUser.child(id).child("petrol").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //Toast.makeText(AdminStocks.this, String.valueOf(task.getResult().getValue()), Toast.LENGTH_SHORT).show();
                    String v = String.valueOf(task.getResult().getValue()) + "L";
                    pt.setText(String.valueOf(task.getResult().getValue()) + "L");
                }
            }
        });

        databaseUser.child(id).child("diesel").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    //Toast.makeText(AdminStocks.this, String.valueOf(task.getResult().getValue()), Toast.LENGTH_SHORT).show();
                    ds.setText(String.valueOf(task.getResult().getValue()) + "L");
                }
            }
        });

        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AdminHome.this, AdminStocks.class);
                myIntent.putExtra("id", id);
                AdminHome.this.startActivity(myIntent);
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AdminHome.this, User_QRScan.class);
                AdminHome.this.startActivity(myIntent);
            }
        });
    }
}