package com.raghavapp.connect3game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //0:empty, 1:red, 2:yellow
    int flag = 1;
    int count = 0;
    int[] gameState = new int[9];
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive = true;
    TextView turnTextView;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tag = Integer.parseInt(counter.getTag().toString());
        if (gameState[tag] == 0 && gameActive) {
            count++;
            counter.setTranslationY(-1500);
            gameState[tag] = flag;
            if (flag == 1) {
                flag = 2;
                counter.setImageResource(R.drawable.red);
                turnTextView.setText("Yellow's Turn");

            } else {
                flag = 1;
                counter.setImageResource(R.drawable.yellow);
                turnTextView.setText("Red's Turn");
            }
            counter.animate().translationYBy(1500).rotation(3000).setDuration(300);
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 0) {
                    //Someone has won
                    turnTextView.setVisibility(View.INVISIBLE);
                    gameActive = false;
                    String color = flag == 1 ? "Yellow" : "Red";     //Opposite
                    Button playAgain = findViewById(R.id.playAgainButton);
                    TextView winnerText = findViewById(R.id.winnerTextView);
                    winnerText.setText(color + " has won!");
                    playAgain.setVisibility(View.VISIBLE);
                    winnerText.setVisibility(View.VISIBLE);
                }
            }
            if (count == 9 && gameActive) {
                gameActive = false;
                turnTextView.setVisibility(View.INVISIBLE);
                Button playAgain = findViewById(R.id.playAgainButton);
                TextView winnerText = findViewById(R.id.winnerTextView);
                winnerText.setText("Match is drawn!");
                playAgain.setVisibility(View.VISIBLE);
                winnerText.setVisibility(View.VISIBLE);
            }
        }
    }

    public void playAgain(View view) {
        turnTextView.setText("Red's Turn");
        turnTextView.setVisibility(View.VISIBLE);
        Button playAgain = findViewById(R.id.playAgainButton);
        TextView winnerText = findViewById(R.id.winnerTextView);
        playAgain.setVisibility(View.INVISIBLE);
        winnerText.setVisibility(View.INVISIBLE);
        android.support.v7.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
        flag = 1;
        count = 0;
        for (int i=0; i<gameState.length; i++) {
            gameState[i] = 0;
        }
        gameActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        turnTextView = findViewById(R.id.turnTextView);
        turnTextView.setText("Red's Turn");
    }
}
