package com.adriancasado.endevinanumero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "RecordsPrefs";
    private ArrayList<Record> recordsList;
    private RecordsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        recordsList = loadRecordsFromPreferences();

        Intent intent = getIntent();
        if (intent != null) {
            String newUser = intent.getStringExtra("user");
            int newTries = intent.getIntExtra("tries", -1);

            if (newUser != null && !newUser.isEmpty() && newTries != -1) {
                Log.d("TEST", "Nuevo registro: User=" + newUser + ", Tries=" + newTries);

                recordsList.add(new Record(newUser, newTries));
                saveRecordsToPreferences(recordsList);
            } else {
                Log.e("TEST", "Datos inválidos: User o Tries no están definidos");
            }
        }

        ListView listView = findViewById(R.id.records_list_view);
        adapter = new RecordsAdapter(this, recordsList);
        listView.setAdapter(adapter);
    }

    private void saveRecordsToPreferences(ArrayList<Record> records) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        StringBuilder users = new StringBuilder();
        StringBuilder tries = new StringBuilder();

        for (Record record : records) {
            users.append(record.getUser()).append(",");
            tries.append(record.getTries()).append(",");
        }

        editor.putString("users", users.toString());
        editor.putString("tries", tries.toString());
        editor.apply();

        Log.d("TEST", "Registros guardados: " + users.toString() + " | " + tries.toString());
    }

    private ArrayList<Record> loadRecordsFromPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String users = prefs.getString("users", "");
        String tries = prefs.getString("tries", "");

        ArrayList<Record> records = new ArrayList<>();

        if (!users.isEmpty() && !tries.isEmpty()) {
            String[] usersArray = users.split(",");
            String[] triesArray = tries.split(",");

            for (int i = 0; i < usersArray.length && i < triesArray.length; i++) {
                try {
                    String user = usersArray[i];
                    int triesCount = Integer.parseInt(triesArray[i]);
                    records.add(new Record(user, triesCount));
                } catch (NumberFormatException e) {
                    Log.e("TEST", "Error al cargar registros: " + e.getMessage());
                }
            }
        }

        Log.d("TEST", "Registros cargados: " + records.size());
        return records;
    }
}