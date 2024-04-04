package com.example.androidstudiocollections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.Stack;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator2);
    }

    public void evaluate(Stack<Float> operands, Stack<Character>operators){
        Character operator = operators.pop();
        Float value2 = operands.pop();
        Float value1 = operands.pop();
        float result = 0;
        switch(operator){
            case '+':
                result = value1 + value2;
                break;
            case '-':
                result = value1 - value2;
                break;
            case '*':
                result = value1 * value2;
                break;
            case '/':
                if(value2 == 0){
                    Toast.makeText(CalculatorActivity.this, "bayot", Toast.LENGTH_SHORT).show();
                    throw new ArithmeticException("Division by zero");
                }
                result = value1 / value2;
                break;
            case '%':
                result = value1 % value2;
                break;

        }
        operands.push(result);

    }

    public int precedence(Character symbol){
        switch(symbol){
            case '^':
                return 3;
            case '*':
            case '/':
            case '%':
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                break;
        }
        return 0;
    }
}