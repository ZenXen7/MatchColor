package com.example.androidstudiocollections;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class  ButtonExercise extends AppCompatActivity {
    Button btnInvis;
    Button btnBack;
    Button btnBG;
    Button btnChangeBG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_exercise);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnInvis = (Button) findViewById(R.id.btnMakeInvis);
        btnBG = (Button)  findViewById(R.id.btnChangeBG);
        btnChangeBG = (Button) findViewById(R.id.btnChangeBGBtn);

        btnInvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnInvis.setAlpha(0);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(ButtonExercise.this, MainActivity.class);
                startActivity(intent3);
            }
        });

        final boolean[] status = {false};
        btnBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int min = 1;
                final int max = 10;
                final int random = new Random().nextInt((max -  min) + 1) + min;
                if(!status[0])
                {
                    setActivityBackgroundColor(1);
                    status[0] = true;
                }
                else {
                    setActivityBackgroundColor(0);
                    status[0] = false;
                }
            }
        });

        final boolean[] color = {false};
        btnChangeBG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int min = 1;
                final int max = 10;
                final int random = new Random().nextInt((max -  min) + 1) + min;
                if(!status[0])
                {
                    setButtonColor(1);
                    status[0] = true;
                }
                else {
                    setButtonColor(0);
                    status[0] = false;
                }
            }
        });



    }
    public void setActivityBackgroundColor(int color) {
        ConstraintLayout layout = findViewById(R.id.btnexercisebg);

        if(color == 1)
            layout.setBackgroundColor(BLACK);
        else
            layout.setBackgroundColor(WHITE);
    }

    public void setButtonColor(int color) {


        if(color == 1)
            btnChangeBG.setBackgroundColor(Color.parseColor("#FF0000"));
        else
            btnChangeBG.setBackgroundColor(BLACK);
    }


}