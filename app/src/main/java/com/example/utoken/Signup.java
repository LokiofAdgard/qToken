package com.example.utoken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Signup extends AppCompatActivity {

    String[] NICs = {"111111111111", "1111111111111", "c"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button Login = (Button) findViewById(R.id.btn_login);
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        EditText password2 = (EditText) findViewById(R.id.password2);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nic = username.getText().toString();
                String pw = password.getText().toString();
                String pw2 = password2.getText().toString();
                if(nic.equals("") || pw.equals("") || pw2.equals("")){
                    Toast.makeText(Signup.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else if (nic.length()!=12 && nic.length()!=13) {
                    Toast.makeText(Signup.this, "Invalid NIC", Toast.LENGTH_SHORT).show();
                }
                else if (Arrays.asList(NICs).contains(nic)) {
                    Toast.makeText(Signup.this, "NIC already registered", Toast.LENGTH_SHORT).show();
                }
                else if (pw.length()<5) {
                    Toast.makeText(Signup.this, "Password too short", Toast.LENGTH_SHORT).show();
                }
                else if (!(pw.equals(pw2))) {
                    Toast.makeText(Signup.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Signup.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    //TODO: add entry


                    Intent myIntent = new Intent(Signup.this, User_Login.class);
                    Signup.this.startActivity(myIntent);

                }


            }
        });


    }
}