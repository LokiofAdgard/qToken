package com.example.utoken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class User_Login extends AppCompatActivity {

    String[] Users = {"111111111111", "1111111111111", "c"};
    String[] Pws = {"hunt", "louis", "c"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

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
                else if (nic.length()!=12 && nic.length()!=13) {
                    Toast.makeText(User_Login.this, "Invalid NIC", Toast.LENGTH_SHORT).show();
                }
                else if (!Arrays.asList(Users).contains(nic)){
                    Toast.makeText(User_Login.this, "NIC not Registered", Toast.LENGTH_SHORT).show();
                }
                else if (!Pws[(Arrays.asList(Users).indexOf(nic))].equals(pw)){
                    Toast.makeText(User_Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(User_Login.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                    //TODO: User home

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