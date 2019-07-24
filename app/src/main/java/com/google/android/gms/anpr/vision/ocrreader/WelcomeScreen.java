package com.google.android.gms.anpr.vision.ocrreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeScreen extends AppCompatActivity {

    private Button scanPlate_btn, history_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        scanPlate_btn = (Button) findViewById(R.id.scan_plate);
        history_btn = (Button) findViewById(R.id.History);


        scanPlate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeScreen.this, OcrCaptureActivity.class);
                startActivity(intent);
                fileList();
            }
        });


        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeScreen.this, ShowResults.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
