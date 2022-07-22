package com.example.utoken;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User_QRScan extends AppCompatActivity {

    String[] codes = new String[]{"say", "you", "me"};
    List<String> codeList = new ArrayList<>(Arrays.asList(codes));

    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_qrscan);

        Button btn_scan = (Button) findViewById(R.id.btn_scan);
        display = (TextView) findViewById(R.id.display);


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
        if (result.getContents() != null){
            //Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
            if(codeList.contains(result.getContents())){
                display.setText("APPROVED");
                display.setBackgroundColor(Color.parseColor("#406050"));       //grey
            }
            else{
                display.setText("INVALID");
                display.setTextColor(Color.parseColor("#ffffff"));             //black
                display.setBackgroundColor(Color.parseColor("#9297a1"));       //grey
            }
        }
    });

}