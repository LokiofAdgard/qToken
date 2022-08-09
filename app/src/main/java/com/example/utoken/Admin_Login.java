package com.example.utoken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admin_Login extends AppCompatActivity {

    String[] Users = {"Tom", "Hank", "c"};
    String[] Pws = {"cruise", "griffin", "c"};
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> PWs = new ArrayList<>();

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        databaseUser = FirebaseDatabase.getInstance().getReference("admin");

        List<Admin> adminList = new ArrayList<>();
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adminList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Admin admin = postSnapshot.getValue(Admin.class);
                    adminList.add(admin);
                    assert admin != null;
                    Names.add(admin.name);
                    PWs.add(admin.password);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: ");
            }
        });

        Button Login = (Button) findViewById(R.id.btn_login);
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                String pw = password.getText().toString();
                if(name.equals("") || pw.equals("")){
                    Toast.makeText(Admin_Login.this, "Please enter Username and Password", Toast.LENGTH_SHORT).show();
                }
                else if (!Names.contains(name)){
                    Toast.makeText(Admin_Login.this, "Username not Registered", Toast.LENGTH_SHORT).show();
                }
                else if (!PWs.get(Names.indexOf(name)).equals(pw)){
                    Toast.makeText(Admin_Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Admin_Login.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                    //TODO: Admin home

                    Intent myIntent = new Intent(Admin_Login.this, User_QRScan.class);
                    Admin_Login.this.startActivity(myIntent);

                }

            }
        });
    }
}