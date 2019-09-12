package com.mbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView mEasyMode,mLearningode,mCodingMode,mCustomPlayMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mEasyMode = findViewById(R.id.easy_mode);
        mLearningode = findViewById(R.id.learning_mode);
        mCodingMode = findViewById(R.id.coding_mode);
        mCustomPlayMode = findViewById(R.id.custom_play_mode);

        mEasyMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goEasyMode = new Intent(getApplicationContext(),EasyModeActivity.class);
                startActivity(goEasyMode);
            }
        });

        mLearningode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goLearningMode = new Intent(getApplicationContext(),LearningModeActivity.class);
                startActivity(goLearningMode);
            }
        });

        mCodingMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();

            }
        });

        mCustomPlayMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCustomPlay = new Intent(getApplicationContext(),CustomPlayMode.class);
                startActivity(goCustomPlay);
            }
        });
    }
}
