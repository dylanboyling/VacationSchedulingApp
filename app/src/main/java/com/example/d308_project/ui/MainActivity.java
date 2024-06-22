package com.example.d308_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.d308_project.R;

public class MainActivity extends AppCompatActivity {

    public static int numberOfAlerts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button enterButton = findViewById(R.id.button);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent enterApp = new Intent(MainActivity.this, VacationList.class);
                enterApp.putExtra("test", "Information sent");
                startActivity(enterApp);
            }
        });
    }
}