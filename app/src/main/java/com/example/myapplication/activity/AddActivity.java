package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

public class AddActivity extends AppCompatActivity {

    public static String selected;
    private Button btnNext;
    private Spinner spinnerAssets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add2);

        btnNext = findViewById(R.id.btnNext);
        spinnerAssets = findViewById(R.id.spinnerAssets);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = spinnerAssets.getSelectedItem().toString().trim();
                if (selected.equals("Нет выбора")) {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.customToast));

                    TextView text = layout.findViewById(R.id.text);
                    text.setText("Выберите актив");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
                else {
                    Intent intent = new Intent(AddActivity.this, AddActivityNext.class);
                    startActivity(intent);
                }
            }
        });
    }
}