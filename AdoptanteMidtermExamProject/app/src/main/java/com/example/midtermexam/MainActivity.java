package com.example.midtermexam;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnReturn, btnWin;
    int redcount = 0;
    int bluecount = 0;
    int purplecount = 0;
    int[][] colors;
    static boolean allSameColor = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(), "Welcome to the Game", Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn6 = findViewById(R.id.button6);
        btn7 = findViewById(R.id.button7);
        btn8 = findViewById(R.id.button8);
        btn9 = findViewById(R.id.button9);
        btnReturn = findViewById(R.id.btnReturn);
        btnWin = findViewById(R.id.btnWin);

        colors = new int[][]{{Randomize(), Randomize(), Randomize()},
                {Randomize(), Randomize(), Randomize()},
                {Randomize(), Randomize(), Randomize()}};

        updateButtons();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColors(0, 0);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColors(0, 1);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColors(0, 2);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColors(1, 0);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColors(1, 1);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColors(1, 2);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColors(2, 0);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColors(2, 1);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColors(2, 2);
            }
        });



        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        btnWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEndGame();
                Toast.makeText(getApplicationContext(), "You Won The Game", Toast.LENGTH_LONG).show();
                resetGame();
            }
        });
    }

    public void changeColors(int row, int col) {
        int color = colors[row][col];
        if (color == 1) {
            colors[row][col] = 2;
        } else if (color == 2) {
            colors[row][col] = 3;
        } else {
            colors[row][col] = 1;
        }


        if (row - 1 >= 0) {
            if (!(row - 1 == row && col == col)) {
                colors[row - 1][col] = getNextColor(colors[row - 1][col]);
            }
        }

        if (row + 1 < 3) {
            if (!(row + 1 == row && col == col)) {
                colors[row + 1][col] = getNextColor(colors[row + 1][col]);
            }
        }

        if (col - 1 >= 0) {
            if (!(row == row && col - 1 == col)) {
                colors[row][col - 1] = getNextColor(colors[row][col - 1]);
            }
        }

        if (col + 1 < 3) {
            if (!(row == row && col + 1 == col)) {
                colors[row][col + 1] = getNextColor(colors[row][col + 1]);
            }
        }

        updateButtons();
        checkEndGame();
    }


    private int getNextColor(int color) {
        if (color == 1) return 2;
        else if (color == 2) return 3;
        else return 1;
    }

    private void updateButtons() {
        btn1.setBackgroundColor(getColorValue(colors[0][0]));
        btn2.setBackgroundColor(getColorValue(colors[0][1]));
        btn3.setBackgroundColor(getColorValue(colors[0][2]));
        btn4.setBackgroundColor(getColorValue(colors[1][0]));
        btn5.setBackgroundColor(getColorValue(colors[1][1]));
        btn6.setBackgroundColor(getColorValue(colors[1][2]));
        btn7.setBackgroundColor(getColorValue(colors[2][0]));
        btn8.setBackgroundColor(getColorValue(colors[2][1]));
        btn9.setBackgroundColor(getColorValue(colors[2][2]));
    }

    private int getColorValue(int color) {
        switch (color) {
            case 1:
                return Color.RED;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.GREEN;
            default:
                return Color.WHITE;
        }
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                colors[i][j] = Randomize();
            }
        }
        updateButtons();
    }

    public void checkEndGame() {


        int firstColor = colors[0][0];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (colors[i][j] != firstColor) {
                    allSameColor = false;
                   return;
                }
            }
        }
        allSameColor = true;

      if(allSameColor == true){
          Toast.makeText(getApplicationContext(), "You Won The Game", Toast.LENGTH_LONG).show();
      }

    }

    public int Randomize() {
        return new Random().nextInt(3) + 1;
    }
}



