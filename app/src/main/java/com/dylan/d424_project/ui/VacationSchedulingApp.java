package com.dylan.d424_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.dylan.d424_capstone.R;


public class VacationSchedulingApp extends AppCompatActivity {

    public static int numberOfAlerts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button enterButton = findViewById(R.id.button);
        enterButton.setOnClickListener(view -> {
            Intent enterApp = new Intent(VacationSchedulingApp.this, VacationList.class);
            startActivity(enterApp);
        });
    }
}