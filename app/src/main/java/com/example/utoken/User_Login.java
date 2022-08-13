package com.example.utoken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class User_Login extends AppCompatActivity {

    ArrayList<String> IDs = new ArrayList<>();
    ArrayList<String> NICs = new ArrayList<>();
    ArrayList<String> VIDs = new ArrayList<>();
    ArrayList<String> PWs = new ArrayList<>();
    ArrayList<Long> TIMEs = new ArrayList<>();
    ArrayList<String> STs = new ArrayList<>();
    ArrayList<String> ISTs = new ArrayList<>();

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        databaseUser = FirebaseDatabase.getInstance().getReference("user");

        List<User> userList = new ArrayList<>();
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    userList.add(user);
                    assert user != null;
                    IDs.add(user.id);
                    NICs.add(user.nic);
                    VIDs.add(user.vid);
                    PWs.add(user.password);
                    TIMEs.add(user.time);
                    STs.add(user.station);
                    ISTs.add(user.aid);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: ");
            }
        });


        Button Login = (Button) findViewById(R.id.btn_login);
        Button Signup = (Button) findViewById(R.id.btn_signup);
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nic = username.getText().toString();
                String pw = password.getText().toString();
                if(nic.equals("") || pw.equals("")){
                    Toast.makeText(User_Login.this, "Please enter Username and Password", Toast.LENGTH_SHORT).show();
                }
                else if (nic.length()<10) {
                    Toast.makeText(User_Login.this, "Invalid NIC", Toast.LENGTH_SHORT).show();
                }
                else if (!(NICs.contains(nic))){
                    Toast.makeText(User_Login.this, "NIC not Registered", Toast.LENGTH_SHORT).show();
                }
                //else if (!PWs.get((Arrays.asList(NICs).indexOf(nic))).equals(pw)){
                else if (!PWs.get(NICs.indexOf(nic)).equals(pw)){
                    Toast.makeText(User_Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseUser.child(IDs.get(NICs.indexOf(nic))).child("qr").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            } else {
                                if ((System.currentTimeMillis() - TIMEs.get(NICs.indexOf(nic)))>100000){
                                    databaseUser.child(IDs.get(NICs.indexOf(nic))).child("approved").setValue(true);
                                }
                                Boolean v = (Boolean) task.getResult().getValue();
                                if (v) {
                                    Toast.makeText(User_Login.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                                    Intent myIntent = new Intent(User_Login.this, User_Home1.class);
                                    myIntent.putExtra("id", IDs.get(NICs.indexOf(nic)));
                                    myIntent.putExtra("idAdmin", ISTs.get(NICs.indexOf(nic)));
                                    myIntent.putExtra("nAdmin", STs.get(NICs.indexOf(nic)));
                                    User_Login.this.startActivity(myIntent);
                                }
                                else {
                                    Toast.makeText(User_Login.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                                    Intent myIntent = new Intent(User_Login.this, Request.class);
                                    myIntent.putExtra("id", IDs.get(NICs.indexOf(nic)));
                                    User_Login.this.startActivity(myIntent);
                                }
                            }
                        }
                    });
                }
            }
        });
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(User_Login.this, Signup.class);
                User_Login.this.startActivity(myIntent);
            }
        });



    }
}