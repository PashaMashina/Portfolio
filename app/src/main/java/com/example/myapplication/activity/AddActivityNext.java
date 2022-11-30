package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.LearnPrice;
import com.example.myapplication.R;
import com.example.myapplication.db.MyDBHelper;

public class AddActivityNext extends AppCompatActivity {

    private double amount;
    private EditText edTitle;
    private EditText edAmount;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_next);

        edTitle = findViewById(R.id.edTitle);
        edAmount = findViewById(R.id.edAmount);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edAmount.getText().toString();
                amount = Double.parseDouble(str);
                LearnPrice.priceAsset(edTitle.getText().toString().trim(), AddActivity.selected);
                MyDBHelper myDB = new MyDBHelper(AddActivityNext.this);
                myDB.addAsset(edTitle.getText().toString().trim(), Math.round(LearnPrice.priceNow * 100.0) / 100.0,amount, AddActivity.selected);
                System.out.println(LearnPrice.priceNow);
                Intent intent = new Intent(AddActivityNext.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}