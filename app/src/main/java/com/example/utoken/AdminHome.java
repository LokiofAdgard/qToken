package com.example.utoken;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        String id = getIntent().getStringExtra("id");

        Button stock = (Button) findViewById(R.id.stock);
        Button scan = (Button) findViewById(R.id.scan);

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