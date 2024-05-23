package com.group04.minigame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStart;
    private Button btnHuongDan;
    private Button btnSignOut;
    MediaPlayer ClickIn;
    MediaPlayer ClickOut;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Intent intentFromLogin = getIntent();
        username = intentFromLogin.getStringExtra("username");

        btnStart = findViewById(R.id.btnStart);
        btnHuongDan = findViewById(R.id.btnHuongDan);
        btnSignOut = findViewById(R.id.btnSignOut);

        ClickIn = MediaPlayer.create(this, R.raw.clickbutton);
        ClickOut = MediaPlayer.create(this, R.raw.backbutton);

        btnStart.setOnClickListener(this);
        btnHuongDan.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
    }


    private void start() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    private void huongDan() {
        Intent intent = new Intent(this, InstructActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    private void signOut() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnStart) {
            ClickIn.start();
            start();
        }
        if (v.getId() == R.id.btnHuongDan) {
            ClickIn.start();
            huongDan();
        }
        if (v.getId() == R.id.btnSignOut) {
            ClickOut.start();
            signOut();
        }
    }
}
