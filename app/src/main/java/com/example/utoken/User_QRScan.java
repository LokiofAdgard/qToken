package com.example.utoken;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User_QRScan extends AppCompatActivity {

    //String[] codes = new String[]{"say", "you", "me"};
    //List<String> codeList = new ArrayList<>(Arrays.asList(codes));

    TextView display;

    DatabaseReference databaseUser;

    String idA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_qrscan);

        Button btn_scan = (Button) findViewById(R.id.btn_scan);
        display = (TextView) findViewById(R.id.display);

        idA = getIntent().getStringExtra("id");

        databaseUser = FirebaseDatabase.getInstance().getReference("user");

        btn_scan.setOnClickListener(view -> {
            scanCode();
        });

    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->{

        String code = result.getContents();
        if (code.contains(idA)) {
            String id = code.replaceAll(idA, "");

            databaseUser.child(id).child("qr").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        Boolean v = (Boolean) task.getResult().getValue();
                        if (result.getContents() != null) {
                            //Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                            if (v) {
                                display.setText("APPROVED");
                                display.setBackgroundColor(Color.parseColor("#406050"));       //grey
                                databaseUser.child(id).child("qr").setValue(false);
                            } else {
                                display.setText("INVALID");
                                display.setTextColor(Color.parseColor("#ffffff"));             //black
                                display.setBackgroundColor(Color.parseColor("#9297a1"));       //grey
                            }
                        }
                    }
                }
            });
        }
        else{
            Toast.makeText(this, "Wrong Station", Toast.LENGTH_SHORT).show();
            display.setText("INVALID");
            display.setTextColor(Color.parseColor("#ffffff"));             //black
            display.setBackgroundColor(Color.parseColor("#9297a1"));       //grey
        }
    });
}