package com.group04.minigame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;

public class InstructActivity extends AppCompatActivity {

    private Button btnBack;
    private MediaPlayer ClickOut;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruct);

        Intent intentFromLogin = getIntent();
        username = intentFromLogin.getStringExtra("username");

        btnBack = findViewById(R.id.btnBack);
        ClickOut = MediaPlayer.create(this, R.raw.backbutton);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickOut.start();
                Intent intent = new Intent(InstructActivity.this, StartActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}
