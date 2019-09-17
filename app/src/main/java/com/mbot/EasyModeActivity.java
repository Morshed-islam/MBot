package com.mbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class EasyModeActivity extends AppCompatActivity {

    private ImageView mDriveMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_easy_mode);

        mDriveMode = findViewById(R.id.drive_mode);

        mDriveMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDriveMode = new Intent(getApplicationContext(),DriveModeActivity.class);
                startActivity(goDriveMode);
            }
        });
    }
}
