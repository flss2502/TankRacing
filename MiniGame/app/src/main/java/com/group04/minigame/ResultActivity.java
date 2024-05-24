package com.group04.minigame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer ClickOut;
    private MediaPlayer resultSound;
    private String username;
    private int newMoney, totalWinning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        LinearLayout imgSelectedTanks = findViewById(R.id.imgSelectedTanks);
        LinearLayout imgWinningTanks = findViewById(R.id.imgWinningTanks);
        TextView tvResult = findViewById(R.id.tvResult);
        TextView tvMoney = findViewById(R.id.tvMoney);
        Button btnReset = findViewById(R.id.btnReset);
        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        ClickOut = MediaPlayer.create(this, R.raw.backbutton);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        List<Integer> selectedTanks = intent.getIntegerArrayListExtra("selectedTanks");
        List<Integer> winningTanks = intent.getIntegerArrayListExtra("winningTanks");
        boolean isWinner = intent.getBooleanExtra("isWinner", false);
        boolean isTie = intent.getBooleanExtra("isTie", false);
        int betAmount = intent.getIntExtra("betAmount", 0);
        newMoney = intent.getIntExtra("currentMoney", 0);
        totalWinning = intent.getIntExtra("totalWinning", 0);
        Intent musicIntent = new Intent(this, MusicService.class);
        stopService(musicIntent);

        if (isWinner) {
            tvResult.setText("Bạn nhận được: " + totalWinning + "$");
            resultSound = MediaPlayer.create(this, R.raw.win);
        } else if (isTie) {
            tvResult.setText("Bạn nhận lại số tiền đã cược: " + totalWinning + "$");
            resultSound = MediaPlayer.create(this, R.raw.tie);
        } else {
            tvResult.setText("Bạn mất: " + betAmount + "$");
            resultSound = MediaPlayer.create(this, R.raw.lose);
        }

        resultSound.start();

        for (int tank : selectedTanks) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            imageView.setImageResource(getTankDrawable(tank));
            imgSelectedTanks.addView(imageView);
        }

        for (int tank : winningTanks) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            imageView.setImageResource(getTankDrawable(tank));
            imgWinningTanks.addView(imageView);
        }

        tvMoney.setText("Số tiền còn lại: " + newMoney + "$");
        btnReset.setOnClickListener(this);
        btnBackToMain.setOnClickListener(this);
    }

    private int getTankDrawable(int tankNumber) {
        switch (tankNumber) {
            case 1:
                return R.drawable.tank_1;
            case 2:
                return R.drawable.tank_2;
            case 3:
                return R.drawable.tank_3;
            case 4:
                return R.drawable.tank_4;
            default:
                return R.drawable.tank_1;
        }
    }

    private void reset() {
        Intent musicIntent = new Intent(this, MusicService.class);
        startService(musicIntent);
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("currentMoney", newMoney);
        startActivity(intent);
        finish();
    }

    private void backToMain() {
        Intent musicIntent = new Intent(this, MusicService.class);
        startService(musicIntent);
        Intent intent = new Intent(ResultActivity.this, StartActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("currentMoney", newMoney);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnReset) {
            ClickOut.start();
            resultSound.stop();
            resultSound.release();
            reset();
        }
        if (v.getId() == R.id.btnBackToMain) {
            ClickOut.start();
            resultSound.stop();
            resultSound.release();
            backToMain();
        }
    }
}