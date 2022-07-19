package com.example.utoken;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class Admin_Login extends AppCompatActivity {

    String[] Users = {"Tom", "Hank", "c"};
    String[] Pws = {"cruise", "griffin", "c"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        Button Login = (Button) findViewById(R.id.btn_login);
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pw = password.getText().toString();
                if(user.equals("") || pw.equals("")){
                    Toast.makeText(Admin_Login.this, "Please enter Username and Password", Toast.LENGTH_SHORT).show();
                }
                else if (!Arrays.asList(Users).contains(user)){
                    Toast.makeText(Admin_Login.this, "Username not Registered", Toast.LENGTH_SHORT).show();
                }
                else if (!Pws[(Arrays.asList(Users).indexOf(user))].equals(pw)){
                    Toast.makeText(Admin_Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Admin_Login.this, "Log In Successful", Toast.LENGTH_SHORT).show();
                    //TODO: Admin home

                }

            }
        });
    }
}