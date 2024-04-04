package com.example.androidstudiocollections;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Handler;



public class Match3 extends AppCompatActivity {
    private static final int ANIMATION_DELAY = 300; // Delay in milliseconds
    private static final int GRID_SIZE = 5;
    private static final int MATCH_THRESHOLD = 3;
    private static final int SCORE_PER_MATCH = 1;

    private GridView gridView;
    private Button restartButton;
    private int[][] grid;
    private int score;
    private int selectedRow = -1;
    private int selectedCol = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        score = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match3);

        gridView = findViewById(R.id.grid_view);
        restartButton = findViewById(R.id.restart_button);

        initializeGrid();
        updateGridAdapter();

        restartButton.setOnClickListener(v -> restartGame());

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            int row = position / GRID_SIZE;
            int col = position % GRID_SIZE;

            if (selectedRow == -1 && selectedCol == -1) {
                selectedRow = row;
                selectedCol = col;
            } else {
                if (isValidSwap(selectedRow, selectedCol, row, col)) {
                    swapTiles(selectedRow, selectedCol, row, col);
                    checkMatches();
                }
                selectedRow = -1;
                selectedCol = -1;
            }
            updateGridAdapter();
        });
    }

    private void initializeGrid() {
        grid = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = generateRandomTile();
            }
        }
        // Check for matches after initializing the grid
        while (checkInitialMatches()) {
            // If matches are found, regenerate the grid until no matches are found
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    grid[i][j] = generateRandomTile();
                }
            }
        }
        score = 0;
    }

    private boolean checkInitialMatches() {
        // Check for matches in rows
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE - 2; j++) {
                if (grid[i][j] == grid[i][j + 1] && grid[i][j] == grid[i][j + 2]) {
                    return true; // Match found
                }
            }
        }

        // Check for matches in columns
        for (int j = 0; j < GRID_SIZE; j++) {
            for (int i = 0; i < GRID_SIZE - 2; i++) {
                if (grid[i][j] == grid[i + 1][j] && grid[i][j] == grid[i + 2][j]) {
                    return true; // Match found
                }
            }
        }

        return false; // No match found
    }

    private int generateRandomTile() {
        Random rand = new Random();
        return rand.nextInt(4) + 1;
    }

    private boolean isValidSwap(int row1, int col1, int row2, int col2) {
        // Check if the same tile is selected
        if (row1 == row2 && col1 == col2) {
            return false;
        }

        // Check if the selected tiles are adjacent
        if (Math.abs(row1 - row2) == 1 && col1 == col2) { // Check for adjacent tiles in the same column
            // Check if there are already three adjacent candies in the same column
            if ((row1 < GRID_SIZE - 2 && grid[row1][col1] == grid[row1 + 1][col1] && grid[row1][col1] == grid[row1 + 2][col1]) ||
                    (row2 < GRID_SIZE - 2 && grid[row2][col2] == grid[row2 + 1][col2] && grid[row2][col2] == grid[row2 + 2][col2])) {
                return false;
            }
            return true;
        } else if (Math.abs(col1 - col2) == 1 && row1 == row2) { // Check for adjacent tiles in the same row
            // Check if there are already three adjacent candies in the same row
            if ((col1 < GRID_SIZE - 2 && grid[row1][col1] == grid[row1][col1 + 1] && grid[row1][col1] == grid[row1][col1 + 2]) ||
                    (col2 < GRID_SIZE - 2 && grid[row2][col2] == grid[row2][col2 + 1] && grid[row2][col2] == grid[row2][col2 + 2])) {
                return false;
            }
            return true;
        }

        return false; // Not adjacent
    }


    private void swapTiles(int row1, int col1, int row2, int col2) {
        int temp = grid[row1][col1];
        grid[row1][col1] = grid[row2][col2];
        grid[row2][col2] = temp;
    }

    private void checkMatches() {

        for (int i = 0; i < GRID_SIZE; i++) {
            int count = 1;
            for (int j = 1; j < GRID_SIZE; j++) {
                if (grid[i][j] == grid[i][j - 1]) {
                    count++;
                    if (count == MATCH_THRESHOLD) {
                        replaceTiles(i, j - 2, i, j);
                        score += SCORE_PER_MATCH;
                        updateScore();
                    }
                } else {
                    count = 1;
                }
            }
        }


        for (int j = 0; j < GRID_SIZE; j++) {
            int count = 1;
            for (int i = 1; i < GRID_SIZE; i++) {
                if (grid[i][j] == grid[i - 1][j]) {
                    count++;
                    if (count == MATCH_THRESHOLD) {
                        replaceTiles(i - 2, j, i, j);
                        score += SCORE_PER_MATCH;
                        checkMatches();
                    }
                } else {
                    count = 1;
                }
            }
        }
    }

    private void updateScore() {
        // Update the score display
        // For example, if you have a TextView to display the score:
        TextView scoreTextView = findViewById(R.id.score_text_view);
        scoreTextView.setText("Score: " + score);
    }

    private void replaceTiles(int startRow, int startCol, int endRow, int endCol) {
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                int previousValue = grid[i][j];
                grid[i][j] = generateRandomTile();
                animateTileReplacement(i, j, previousValue, grid[i][j]);
            }
        }
    }

    private void animateTileReplacement(int row, int col, int previousValue, int newValue) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.tile_animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation started
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation ended
                updateGridAdapter();
                checkMatches(); // Check matches after animation finishes
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeated
            }
        });

        // Start animation on the corresponding view in the GridView
        gridView.getChildAt(row * GRID_SIZE + col).startAnimation(animation);
    }

    private void updateGridAdapter() {
        GridAdapter adapter = new GridAdapter(this, grid);
        gridView.setAdapter(adapter);
    }

    private void restartGame() {
        initializeGrid();
        updateGridAdapter();
        score = 0;
        updateScore();
        Toast.makeText(this, "Game restarted", Toast.LENGTH_SHORT).show();
    }
}
