package com.group04.minigame;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar1, seekBar2, seekBar3, seekBar4;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private EditText etBet1, etBet2, etBet3, etBet4;
    private Button startButton, resetButton;
    private TextView tvMoney;
    private Handler handler = new Handler();
    private Runnable runnable;
    private Random random = new Random();
    private int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar1 = findViewById(R.id.sbTank1);
        seekBar2 = findViewById(R.id.sbTank2);
        seekBar3 = findViewById(R.id.sbTank3);
        seekBar4 = findViewById(R.id.sbTank4);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        etBet1 = findViewById(R.id.etBet1);
        etBet2 = findViewById(R.id.etBet2);
        etBet3 = findViewById(R.id.etBet3);
        etBet4 = findViewById(R.id.etBet4);
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);
        tvMoney = findViewById(R.id.tvMoney);

        // Get the current money from the intent if it exists
        Intent intent = getIntent();
        if (intent.hasExtra("currentMoney")) {
            money = intent.getIntExtra("currentMoney", 1000);
        } else {
            money = 1000; // Initial money if no intent extra
        }

        tvMoney.setText("Số tiền: " + money);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateBets()) {
                    startRace();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private boolean validateBets() {
        // Ensure at least one tank is selected and the bets are valid
        boolean isValid = false;
        if (checkBox1.isChecked()) isValid = validateBet(etBet1);
        if (checkBox2.isChecked()) isValid = validateBet(etBet2);
        if (checkBox3.isChecked()) isValid = validateBet(etBet3);
        if (checkBox4.isChecked()) isValid = validateBet(etBet4);

        if (!isValid) {
            Toast.makeText(this, "Please select a tank and enter a valid bet!", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    private boolean validateBet(EditText betField) {
        String betStr = betField.getText().toString();
        if (!betStr.isEmpty()) {
            int bet = Integer.parseInt(betStr);
            if (bet > 0 && bet <= money) {
                return true;
            }
        }
        return false;
    }

    private void startRace() {
        seekBar1.setProgress(0);
        seekBar2.setProgress(0);
        seekBar3.setProgress(0);
        seekBar4.setProgress(0);
        startButton.setEnabled(false);

        runnable = new Runnable() {
            @Override
            public void run() {
                int progress1 = seekBar1.getProgress();
                int progress2 = seekBar2.getProgress();
                int progress3 = seekBar3.getProgress();
                int progress4 = seekBar4.getProgress();

                if (progress1 < 100 && progress2 < 100 && progress3 < 100 && progress4 < 100) {
                    progress1 += random.nextInt(5);
                    progress2 += random.nextInt(5);
                    progress3 += random.nextInt(5);
                    progress4 += random.nextInt(5);

                    seekBar1.setProgress(progress1);
                    seekBar2.setProgress(progress2);
                    seekBar3.setProgress(progress3);
                    seekBar4.setProgress(progress4);

                    handler.postDelayed(runnable, 100);
                } else {
                    determineWinner(progress1, progress2, progress3, progress4);
                }
            }
        };

        handler.postDelayed(runnable, 100);
    }

    private void determineWinner(int progress1, int progress2, int progress3, int progress4) {
        List<Integer> winners = new ArrayList<>();

        // Determine the winners
        if (progress1 >= 100) winners.add(1);
        if (progress2 >= 100) winners.add(2);
        if (progress3 >= 100) winners.add(3);
        if (progress4 >= 100) winners.add(4);

        List<Integer> selectedTanks = getSelectedTanks();
        List<Integer> betAmounts = getBetAmounts(selectedTanks);
        boolean isWinner = false;
        boolean isTie = winners.size() > 1;

        int totalBet = 0;
        for (int betAmount : betAmounts) {
            totalBet += betAmount;
        }

        int newMoney = calculateMoney(winners, selectedTanks, betAmounts);

        // Start ResultActivity
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putIntegerArrayListExtra("selectedTanks", new ArrayList<>(selectedTanks));
        intent.putIntegerArrayListExtra("winningTanks", new ArrayList<>(winners));
        intent.putIntegerArrayListExtra("betAmounts", new ArrayList<>(betAmounts));
        intent.putExtra("isWinner", isWinner);
        intent.putExtra("isTie", isTie);
        intent.putExtra("newMoney", newMoney);
        intent.putExtra("betAmount", totalBet);
        startActivity(intent);

        startButton.setEnabled(true);
    }


    private List<Integer> getSelectedTanks() {
        List<Integer> selectedTanks = new ArrayList<>();
        if (checkBox1.isChecked()) selectedTanks.add(1);
        if (checkBox2.isChecked()) selectedTanks.add(2);
        if (checkBox3.isChecked()) selectedTanks.add(3);
        if (checkBox4.isChecked()) selectedTanks.add(4);
        return selectedTanks;
    }

    private List<Integer> getBetAmounts(List<Integer> selectedTanks) {
        List<Integer> betAmounts = new ArrayList<>();
        for (int tank : selectedTanks) {
            switch (tank) {
                case 1:
                    betAmounts.add(Integer.parseInt(etBet1.getText().toString()));
                    break;
                case 2:
                    betAmounts.add(Integer.parseInt(etBet2.getText().toString()));
                    break;
                case 3:
                    betAmounts.add(Integer.parseInt(etBet3.getText().toString()));
                    break;
                case 4:
                    betAmounts.add(Integer.parseInt(etBet4.getText().toString()));
                    break;
            }
        }
        return betAmounts;
    }

    private int calculateMoney(List<Integer> winners, List<Integer> selectedTanks, List<Integer> betAmounts) {
        int totalWinnings = 0;

        for (int i = 0; i < selectedTanks.size(); i++) {
            int selectedTank = selectedTanks.get(i);
            int betAmount = betAmounts.get(i);

            if (winners.contains(selectedTank)) {
                totalWinnings += betAmount;
            } else {
                totalWinnings -= betAmount;
            }
        }

        money += totalWinnings;
        return money;
    }

    private void resetGame() {
        seekBar1.setProgress(0);
        seekBar2.setProgress(0);
        seekBar3.setProgress(0);
        seekBar4.setProgress(0);
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        etBet1.setText("");
        etBet2.setText("");
        etBet3.setText("");
        etBet4.setText("");
        startButton.setEnabled(true);
        tvMoney.setText("Số tiền: " + money);
    }
}