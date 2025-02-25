package com.adriancasado.endevinanumero;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int usrNum;
    int randNum;
    Random rand = new Random();

    int tries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        randNum = rand.nextInt(50);
        // Toast.makeText(MainActivity.this, "Numero a endevinar: "+randNum, Toast.LENGTH_SHORT).show();

        EditText input = findViewById(R.id.input);
        Button button = findViewById(R.id.button);

        TextView triesCount = findViewById(R.id.triesCount);
        TextView historyText = findViewById(R.id.historyText);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String inputText = input.getText().toString();
                if (!inputText.isEmpty()) {
                    usrNum = Integer.parseInt(inputText);
                    int check = checkNum();
                    input.setText("");
                    if(check == 0) {
                        Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
                        intent.putExtra("tries",tries);

                        tries = 0;
                        triesCount.setText(Integer.toString(tries));
                        historyText.setText("");
                        EditText nameInput = new EditText(MainActivity.this);
                        nameInput.setHint("Nom");
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("CORRECTE!")
                                .setMessage("Vols guardar el teu record?")
                                .setView(nameInput)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String nameToSave = nameInput.getText().toString();
                                        if(!nameToSave.isEmpty()) {
                                            intent.putExtra("user",nameToSave);
                                            startActivity(intent);
                                        }

                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .show();
                        randNum = rand.nextInt(50);


                    }
                    else {
                        tries ++;
                        triesCount.setText(Integer.toString(tries));
                        historyText.setText(inputText + (check == -1 ? " -" : " +")+"\n"+historyText.getText());
                    }
                }

            }
        });
    }

    private int checkNum() {
        if(usrNum == randNum) {
            return 0;
        }
        if(usrNum < randNum) {
            Toast.makeText(MainActivity.this, "El número que busques és major", Toast.LENGTH_SHORT).show();
            return 1;
        } else {
            Toast.makeText(MainActivity.this, "El número que busques és menor", Toast.LENGTH_SHORT).show();
            return -1;
        }

    }
}