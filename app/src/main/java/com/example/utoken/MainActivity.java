package com.example.utoken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Customer = (Button) findViewById(R.id.btn_login_customer);
        Button Admin = (Button) findViewById(R.id.btn_login_admin);

        Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, User_Login.class);
                MainActivity.this.startActivity(myIntent);

            }
        });

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainActivity.this, Admin_Login.class);
                MainActivity.this.startActivity(myIntent);

            }
        });

    }
}