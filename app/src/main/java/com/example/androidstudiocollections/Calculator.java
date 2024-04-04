package com.example.androidstudiocollections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Calculator extends CalculatorActivity {
    int i;
    ArrayList<Button> numbers = new ArrayList<>();
    ArrayList<Button> operations = new ArrayList<>();

    boolean isDot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        calculate();
    }


    public void calculate(){
        isDot = false;
        Button clearBtn =  (Button) findViewById(R.id.btnClearAll);
        Button equalBtn =  (Button) findViewById(R.id.btnEqual);
        Button removeBtn =  (Button) findViewById(R.id.btnRemove);
        Button pointBtn =  (Button) findViewById(R.id.btnPoint);
        TextView resultText =  (TextView) findViewById(R.id.txtResult);
        TextView expressionText =  (TextView) findViewById(R.id.txtExpression);

        float value1 = 0;
        float value2 = 0;

        numbers.add( (Button) findViewById(R.id.btnZero));
        numbers.add( (Button) findViewById(R.id.btnOne));
        numbers.add( (Button) findViewById(R.id.btnTwo));
        numbers.add( (Button) findViewById(R.id.btnThree));
        numbers.add( (Button) findViewById(R.id.btnFour));
        numbers.add( (Button) findViewById(R.id.btnFive));
        numbers.add( (Button) findViewById(R.id.btnSix));
        numbers.add( (Button) findViewById(R.id.btnSeven));
        numbers.add( (Button) findViewById(R.id.btnEighth));
        numbers.add( (Button) findViewById(R.id.btnNine));

        operations.add((Button) findViewById(R.id.btnAdd));
        operations.add((Button) findViewById(R.id.btnSubtract));
        operations.add((Button) findViewById(R.id.btnMultiply));
        operations.add((Button) findViewById(R.id.btnDivide));
        operations.add((Button) findViewById(R.id.btnModulo));

        for(int i = 0; i < numbers.size(); i++){
            numbers.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Button b = (Button) view;
                    String buttonText = b.getText().toString();
                    expressionText.append(buttonText);
                    try {
                        String expression = expressionText.getText() + "";
                        if (expression.equals("")) return;
                        Stack<Float> queueOperands = new Stack<>();
                        Stack<Character> queueOperators = new Stack<>();

                        for (int i = 0; i < expression.length(); i++) {
                            char current = expression.charAt(i);
                            if (Character.isDigit(current) || current == '.') {
                                StringBuilder sb = new StringBuilder();
                                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                                    sb.append(expression.charAt(i));
                                    i++;
                                }
                                i--;
                                queueOperands.push(Float.parseFloat(sb.toString()));
                            } else {
                                while (!queueOperators.isEmpty()) {
                                    evaluate(queueOperands, queueOperators);
                                }
                                queueOperators.push(current);
                            }
                        }
                        while (!queueOperands.isEmpty() && !queueOperators.isEmpty()) {
                            evaluate(queueOperands, queueOperators);
                        }

                        float result = queueOperands.pop();
                        if (result == (int) result) {

                            resultText.setText(String.valueOf((int) result));
                        } else {

                            resultText.setText(String.valueOf(result));
                        }
                    } catch (Exception e) {
                        resultText.setText(e.getMessage());
                    }
                }

            });
        }


        pointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String buttonText = b.getText().toString();
                String current = "" + expressionText.getText();
                if (current.length() > 0) {
                    char last = current.charAt(current.length() - 1);
                    if (last == '.') {
                        expressionText.setText(current.substring(0, current.length() - 1));
                        isDot = false;
                    } else if (isDot) {
                        return;
                    } else {
                        expressionText.append(buttonText);
                        isDot = true;
                    }
                } else {
                    expressionText.append(buttonText);
                    isDot = true;
                }
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDot = false;
                String current = String.valueOf(expressionText.getText());
                if(current.equals(""))return;
                expressionText.setText(current.substring(0, current.length() - 1));
            }
        });

        for(int i = 0; i < operations.size(); i++){
            operations.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isDot = false;
                    Button b = (Button) view;
                    String buttonText = b.getText().toString();
                    String current = "" + expressionText.getText();

                    if(current == "") return;

                    Character last = current.charAt(current.length() - 1);

                    if(last == '+' || last == '-' || last == '*' || last == '/' || last == '%'){
                        current = current.substring(0, current.length() - 1) + buttonText;
                        expressionText.setText(current);
                    }else{
                        expressionText.append(buttonText);
                    }
                }
            });
        }
        equalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    isDot = false;
                    String expression = expressionText.getText() + "";
                    if (expression.equals("")) return;
                    Stack<Float> queueOperands = new Stack<>();
                    Stack<Character> queueOperators = new Stack<>();

                    for (int i = 0; i < expression.length(); i++) {
                        char current = expression.charAt(i);
                        if (Character.isDigit(current) || current == '.') {
                            StringBuilder sb = new StringBuilder();
                            while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                                sb.append(expression.charAt(i));
                                i++;
                            }
                            i--;
                            queueOperands.push(Float.parseFloat(sb.toString()));
                        } else {
                            while (!queueOperators.isEmpty() && precedence(queueOperators.peek()) >= precedence(current)) {
                                evaluate(queueOperands, queueOperators);
                            }
                            queueOperators.push(current);
                        }
                    }
                    while (!queueOperands.isEmpty() && !queueOperators.isEmpty()) {
                        evaluate(queueOperands, queueOperators);
                    }

                    float result = queueOperands.pop();
                    if (result == (int) result) {

                        resultText.setText(String.valueOf((int) result));
                    } else {

                        resultText.setText(String.valueOf(result));
                    }
                } catch (Exception e) {
                    resultText.setText(e.getMessage());
                }
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDot = false;
                expressionText.setText("");
                resultText.setText("0");
            }
        });
    }
}