package com.example.utoken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Signup extends AppCompatActivity {

    ArrayList<String> NICs = new ArrayList<>();
    ArrayList<String> VIDs = new ArrayList<>();

    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
                    NICs.add(user.nic);
                    VIDs.add(user.vid);
                    //String name = userList.get(0).getId();
                    //Toast.makeText(Signup.this, userList.get(0).getId(), Toast.LENGTH_SHORT).show();
                    // here you can access to name property like university.name
                    //Toast.makeText(Signup.this, user.id, Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Signup.this, userList.get(1).getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: ");
            }
        });

        Button Login = findViewById(R.id.btn_login);
        EditText username = findViewById(R.id.username);
        EditText vnoL = findViewById(R.id.vnoL);
        EditText vnoN = findViewById(R.id.vnoN);
        EditText password = findViewById(R.id.password);
        EditText password2 = findViewById(R.id.password2);

        Login.setOnClickListener(view -> {
            String nic = username.getText().toString();
            String v1 = vnoL.getText().toString();
            String v2 = vnoN.getText().toString();
            String pw = password.getText().toString();
            String pw2 = password2.getText().toString();

            if(nic.equals("") || pw.equals("") || pw2.equals("") || v1.equals("") || v2.equals("")){
                Toast.makeText(Signup.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            }
            else if (nic.length()<10) {
                Toast.makeText(Signup.this, "Invalid NIC", Toast.LENGTH_SHORT).show();
            }
            else if (NICs.contains(nic)) {
                Toast.makeText(Signup.this, "NIC already registered", Toast.LENGTH_SHORT).show();
            }
            else if (v1.length()>3) {
                Toast.makeText(Signup.this, "Invalid Vehicle details", Toast.LENGTH_SHORT).show();
            }
            else if (v2.length()!=4) {
                Toast.makeText(Signup.this, "Invalid Vehicle details", Toast.LENGTH_SHORT).show();
            }
            else if (VIDs.contains(v1+v2)) {
                Toast.makeText(Signup.this, "Vehicle ID already registered", Toast.LENGTH_SHORT).show();
            }
            else if (pw.length()<5) {
                Toast.makeText(Signup.this, "Password too short", Toast.LENGTH_SHORT).show();
            }
            else if (!(pw.equals(pw2))) {
                Toast.makeText(Signup.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
            }
            else{
                String id = databaseUser.push().getKey();
                User user = new User(id, nic, (v1+v2), pw);
                assert id != null;
                databaseUser.child(id).setValue(user);
                Toast.makeText(Signup.this, "Successfully Registered", Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(Signup.this, User_Login.class);
                Signup.this.startActivity(myIntent);
            }
        });
    }
}