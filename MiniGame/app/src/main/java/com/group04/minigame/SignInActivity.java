package com.group04.minigame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etUserName;
    private EditText etPassWord;
    private Button btnSignin;
    private final String REQUIRE = "Require";

    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        etUserName = (EditText) findViewById(R.id.etUsername);
        etPassWord = (EditText) findViewById(R.id.etPassword);
        btnSignin = (Button) findViewById(R.id.btnSignIn);
        mediaPlayer = MediaPlayer.create(this, R.raw.clickbutton);

        btnSignin.setOnClickListener(this);

        Intent musicIntent = new Intent(this, MusicService.class);
        startService(musicIntent);
    }

    private boolean checkInput(){
        if (TextUtils.isEmpty(etUserName.getText().toString())){
            etPassWord.setError(REQUIRE);
            return false;
        }

        if (TextUtils.isEmpty(etPassWord.getText().toString())){
            etPassWord.setError(REQUIRE);
            return false;
        }
        return true;
    }

    private void signIn() {
        if (!checkInput()) {
            return;
        }

        if ((TextUtils.equals(etUserName.getText().toString(), "admin"))
                && (TextUtils.equals(etPassWord.getText().toString(), "123456"))) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSignIn) {
            mediaPlayer.start();
            signIn();
        }
    }

}
