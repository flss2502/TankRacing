package com.group04.minigame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private TextView tvResult, tvMoney;
    private LinearLayout imgSelectedTanks, imgWinningTanks;
    private Button btnReset, btnBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        imgSelectedTanks = findViewById(R.id.imgSelectedTanks);
        imgWinningTanks = findViewById(R.id.imgWinningTanks);
        tvResult = findViewById(R.id.tvResult);
        tvMoney = findViewById(R.id.tvMoney);
        btnReset = findViewById(R.id.btnReset);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        Intent intent = getIntent();
        List<Integer> selectedTanks = intent.getIntegerArrayListExtra("selectedTanks");
        List<Integer> winningTanks = intent.getIntegerArrayListExtra("winningTanks");
        boolean isWinner = intent.getBooleanExtra("isWinner", false);
        boolean isTie = intent.getBooleanExtra("isTie", false);
        int betAmount = intent.getIntExtra("betAmount", 0);
        int newMoney = intent.getIntExtra("newMoney", 0);

        if (isWinner) {
            tvResult.setText("Bạn đã thắng! Bạn nhận được: " + betAmount);
        } else if (isTie) {
            tvResult.setText("Trận đấu hòa! Bạn nhận lại số tiền đã cược: " + betAmount);
        } else {
            tvResult.setText("Bạn đã thua! Bạn mất: " + betAmount);
        }

        // Display selected tanks
        for (int tank : selectedTanks) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            imageView.setImageResource(getTankDrawable(tank));
            imgSelectedTanks.addView(imageView);
        }

        // Display winning tanks
        for (int tank : winningTanks) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
            imageView.setImageResource(getTankDrawable(tank));
            imgWinningTanks.addView(imageView);
        }

        tvMoney.setText("Số tiền còn lại: " + newMoney);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.putExtra("currentMoney", newMoney); // Pass the updated money
                startActivity(intent);
            }
        });

        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
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
}
