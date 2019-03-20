package com.app0.calculatorpro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    double firstNumberDouble = 0;
    String firstNumber = "";
    double secondNumberDouble = 0;
    String secondNumber = "";
    String calculationType;
    TextView displayTextView;
    String displayedText;
    double result;
    double valueInMemory;

    /* TODO:
    - kasowanie pojedynczych cyfr
    - blokada klikania kilku znaków funkcyjnych pod rząd (+ - *) (ew. zamiana na inny)
    - liczby ujemne
    - nie można zaczynać od 0
    - obliczenia na większej ilości liczb
    - na telefonie doubles dont work
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayTextView =  findViewById(R.id.displayTextView);
    }

    public void numberPick(View view) {
        //dostaje tag klikniętego przycisku, równego liczbie
        // wyświetla i zapamiętuje pierwszą i drugą liczbę do późniejszych operacji
        Button clickedButton = (Button) view;
        String pickedNumber = clickedButton.getTag().toString();


        if (firstNumberDouble == 0) {
            firstNumber += pickedNumber;
            displayedText = firstNumber;
            displayTextView.setText(displayedText);
        } else { //if first number is already chosen
            secondNumber += pickedNumber;
            displayedText += pickedNumber;
            displayTextView.setText(displayedText);
        }
    }

    public void add(View view) {
        if(!firstNumber.isEmpty()) {
            firstNumberDouble = Double.parseDouble(firstNumber);
            calculationType = "add";
            displayedText += "+";
            displayTextView.setText(displayedText);
        }
    }
    public void subtract(View view) {
        if(!firstNumber.isEmpty()) {
            firstNumberDouble = Double.parseDouble(firstNumber);
            calculationType = "subtract";
            displayedText += "-";
            displayTextView.setText(displayedText);
        }
    }
    public void multiply(View view) {
        if(!firstNumber.isEmpty()) {
            firstNumberDouble = Double.parseDouble(firstNumber);
            calculationType = "multiply";
            displayedText += "*";
            displayTextView.setText(displayedText);
        }
    }
    public void divide(View view) {
        if(!firstNumber.isEmpty()) {
            firstNumberDouble = Double.parseDouble(firstNumber);
            calculationType = "divide";
            displayedText += ":";
            displayTextView.setText(displayedText);
        }
    }

    //odejmuje lub dodaje określony procent od pewnej liczby (firstNumber +/- (secondNumber%))
    public void percent(View view) {
        if(!firstNumber.isEmpty() && !secondNumber.isEmpty()) {
            secondNumberDouble = Double.parseDouble(secondNumber);
            if (calculationType.equals("add")){
                double resultUnlimited = firstNumberDouble + 0.01 * secondNumberDouble * firstNumberDouble;
                result = limitToThreeDecimalPlaces(resultUnlimited);
            } else if (calculationType.equals("subtract")){
                double resultUnlimited = firstNumberDouble - 0.01 * secondNumberDouble * firstNumberDouble;
                result = limitToThreeDecimalPlaces(resultUnlimited);
            }
            if(!displayedText.contains("=")){
                displayedText += "%";
                displayResult();
            }

        }
    }

    public void squareRoot(View view) {
        if(!firstNumber.isEmpty()) {
            firstNumberDouble = Double.parseDouble(firstNumber);
            double resultUnlimited = Math.sqrt(firstNumberDouble);
            result = limitToThreeDecimalPlaces(resultUnlimited);

            if(!displayedText.contains("=")){
                displayedText = "√" + displayedText;
                displayResult();
            }
        }
    }

    //oblicza wszystkie operacje, które wymagają kliknięcia znaku równa się
    public void equals(View view) {
        if(!firstNumber.isEmpty() && !secondNumber.isEmpty()){
            Log.i("info", "firstNumber AND second FOUND");
            secondNumberDouble = Double.parseDouble(secondNumber);
            switch(calculationType) {
                case "add":
                    result = firstNumberDouble + secondNumberDouble;
                    break;
                case "subtract":
                    result = firstNumberDouble - secondNumberDouble;
                    break;
                case "multiply":
                    result = firstNumberDouble * secondNumberDouble;
                    break;
                case "divide":

                    double resultUnlimited = firstNumberDouble/secondNumberDouble;
                    result = limitToThreeDecimalPlaces(resultUnlimited);
                    break;

            }
            Log.i("wynik", Double.toString(result));
            if(!displayedText.contains("=")){
                displayResult();
            }
        }
    }

    //ograniczaja wyniki do 3 miejsc po przecinku
    public double limitToThreeDecimalPlaces(double resultUnlimited) {

        String resultString = String.format("%.3f", resultUnlimited);
        result = Double.parseDouble(resultString);
        return result;
    }
    //prezentuje wynik
    public void displayResult(){
        displayedText += "=";
        int intResult = (int)result;
        //aby liczby całkowite wyświetlały się w formie 5 a nie 5.0
        if (result == intResult) {
            displayedText += intResult;
        } else {
            displayedText += result;
        }
        displayTextView.setText(displayedText);
    }

    //dodaje aktualnie wyświetlany wynik (lub gdy go nie ma - wyświetlaną liczbę) do pamięci
    public void memorize(View view) {
        if (result == 0) {
            if (!firstNumber.isEmpty()) {
                firstNumberDouble = Double.parseDouble(firstNumber);
                valueInMemory = firstNumberDouble;
                Log.i("zapisana liczba", Double.toString(valueInMemory));
            }
        } else {
            valueInMemory = result;
            Log.i("zapisana liczba", Double.toString(valueInMemory));
        }
    }
    //przypisuje wartość z pamięci do firstNumber, jeśli jej nie ma, a jeśli jest, to do secondNumber
    public void getFromMemory(View view) {
          if(firstNumber.isEmpty()) {
              int valueInMemoryInt = (int)valueInMemory;
              if (valueInMemoryInt == valueInMemory) {
                  firstNumber = Integer.toString(valueInMemoryInt);
              } else {
                  firstNumber = Double.toString(valueInMemory);

              }
              displayedText = firstNumber;
              displayTextView.setText(displayedText);
          } else {
              int valueInMemoryInt = (int)valueInMemory;
              if (valueInMemoryInt == valueInMemory) {
                  secondNumber = Integer.toString(valueInMemoryInt);
              } else {
                  secondNumber = Double.toString(valueInMemory);

              }
              displayedText += secondNumber;
              displayTextView.setText(displayedText);
          }
    }

    public void clear(View view) {
        firstNumberDouble = 0;
        secondNumberDouble = 0;
        firstNumber = "";
        secondNumber = "";
        displayTextView.setText("0");
        displayedText = "";
        calculationType = "";
        result = 0;
    }
}

