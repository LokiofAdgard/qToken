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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Request extends AppCompatActivity {

    ArrayList<String> NAMEs = new ArrayList<>();
    ArrayList<String> LOCs = new ArrayList<>();
    ArrayList<String> IDs = new ArrayList<>();
    ArrayList<Integer> Petrol = new ArrayList<>();
    ArrayList<Integer> Diesel = new ArrayList<>();

    DatabaseReference databaseUser;
    DatabaseReference databaseAdmin;
    DatabaseReference databaseControl;

    int quota = 10;
    boolean enabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        String id = getIntent().getStringExtra("id");

        databaseUser = FirebaseDatabase.getInstance().getReference("user");
        databaseAdmin = FirebaseDatabase.getInstance().getReference("admin");
        databaseControl = FirebaseDatabase.getInstance().getReference("control");

        List<Admin> adminList = new ArrayList<>();
        databaseAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Admin admin = postSnapshot.getValue(Admin.class);
                    adminList.add(admin);
                    assert admin != null;
                    NAMEs.add(admin.name);
                    IDs.add(admin.id);
                    LOCs.add(admin.location);
                    Petrol.add(admin.petrol);
                    Diesel.add(admin.diesel);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: ");
            }
        });

        databaseControl.child("quota").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    quota = Integer.parseInt(String.valueOf(task.getResult().getValue()));
                }
            }
        });

        databaseControl.child("enabled").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    enabled = (Boolean) task.getResult().getValue();
                }
            }
        });


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
                                            if (v && enabled) {
                                                //Toast.makeText(Request.this, "approved", Toast.LENGTH_SHORT).show();
                                                String loc = dropdown1.getSelectedItem().toString().toLowerCase(Locale.ROOT);
                                                String type = dropdown2.getSelectedItem().toString().toLowerCase(Locale.ROOT);
                                                int q = adminChoose(loc, type);
                                                //Toast.makeText(Request.this, String.valueOf(q), Toast.LENGTH_SHORT).show();
                                                if (q>=0) {

                                                    databaseAdmin.child(IDs.get(q)).child(type).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                            if (!task.isSuccessful()) {
                                                                Log.e("firebase", "Error getting data", task.getException());
                                                            } else {
                                                                String v = String.valueOf(task.getResult().getValue());
                                                                Integer v1 = Integer.parseInt(v) - quota;
                                                                databaseAdmin.child(IDs.get(q)).child(type).setValue(v1);
                                                                databaseUser.child(id).child("approved").setValue(false);
                                                                databaseUser.child(id).child("qr").setValue(true);
                                                                databaseUser.child(id).child("station").setValue(NAMEs.get(q));
                                                                databaseUser.child(id).child("aid").setValue(IDs.get(q));
                                                                databaseUser.child(id).child("time").setValue(System.currentTimeMillis());
                                                            }
                                                        }
                                                    });

                                                    Intent myIntent = new Intent(Request.this, User_Home1.class);
                                                    myIntent.putExtra("id", id);
                                                    myIntent.putExtra("idAdmin", IDs.get(q));
                                                    myIntent.putExtra("nAdmin", NAMEs.get(q));
                                                    Request.this.startActivity(myIntent);
                                                }
                                                else {Toast.makeText(Request.this, "No Stocks", Toast.LENGTH_SHORT).show();}
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

    public int adminChoose(String loc, String type){
        ArrayList<Integer> idx = new ArrayList<>();
        ArrayList<Integer> amt = new ArrayList<>();
        int max = 0;
        if (type.equals("petrol")){
            for (int i = 0; i<LOCs.size(); i++){
                if ((LOCs.get(i)).equals(loc)) {
                    idx.add(i);
                    amt.add(Petrol.get(i));
                    if (Petrol.get(i)>max) max = Petrol.get(i);
                }
            }
        }
        else {
            for (int i = 0; i<LOCs.size(); i++){
                if ((LOCs.get(i)).equals(loc)) {
                    idx.add(i);
                    amt.add(Diesel.get(i));
                    if (Diesel.get(i)>max) max = Diesel.get(i);
                }
            }
        }
        if (max>quota) return idx.get(amt.indexOf(max));
        else return (-1);
    }
}