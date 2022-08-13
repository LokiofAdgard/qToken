package com.example.utoken;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class User_Home1 extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home1);

        String id = getIntent().getStringExtra("id");
        String idA = getIntent().getStringExtra("idAdmin");
        String idN = getIntent().getStringExtra("nAdmin");

        TextView show = (TextView) findViewById(R.id.show);
        show.setText("Show the following QR code at Gas Station: " + idN);

        ImageView imageCode = findViewById(R.id.imageCode);

        //initializing MultiFormatWriter for QR code
        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            //BitMatrix class to encode entered text and set Width & Height
            BitMatrix mMatrix = mWriter.encode(idA + id, BarcodeFormat.QR_CODE, 400,400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
            imageCode.setImageBitmap(mBitmap);//Setting generated QR code to imageView
            // to hide the keyboard
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //manager.hideSoftInputFromWindow(etText.getApplicationWindowToken(), 0);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(User_Home1.this, User_Login.class);
        User_Home1.this.startActivity(myIntent);
    }
}