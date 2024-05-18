package com.group04.minigame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStart;
    private Button btnHuongDan;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnStart = findViewById(R.id.btnStart);
        btnHuongDan = findViewById(R.id.btnHuongDan);
        btnSignOut = findViewById(R.id.btnSignOut);

        btnStart.setOnClickListener(this);
        btnHuongDan.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
    }


    private void start() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void huongDan() {
        Intent intent = new Intent(this, InstructActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnStart) {
            start();
        }
        if (v.getId() == R.id.btnHuongDan) {
            huongDan();
        }
        if (v.getId() == R.id.btnSignOut) {

        }
    }
}
