package com.example.myapplication.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.WhichUrl;
import com.example.myapplication.db.MyDBHelper;

public class UpdateActivity extends AppCompatActivity {


    EditText titleUp, amountUp, costBuyUp;
    Button btnUpd, btnDel;

    boolean addOrUpd = false;

    String title, typeAsset, amount, costBuy, costNow, profitFromBuy, sum, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        titleUp = findViewById(R.id.titleUp);
        amountUp = findViewById(R.id.amountUp);
        costBuyUp = findViewById(R.id.costBuyUp);
        btnUpd = findViewById(R.id.update_button);
        btnDel = findViewById(R.id.delete_button);

        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        ab.setTitle(title);

        btnUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHelper myDB = new MyDBHelper(UpdateActivity.this);
                title=titleUp.getText().toString().trim();
                amount=amountUp.getText().toString().trim();
                costBuy=costBuyUp.getText().toString().trim();
                //costNow = String.valueOf(AddActivityNext.doRequestToApi(typeAsset, WhichUrl.learnUrl(typeAsset, null), addOrUpd));
                myDB.updateData(id, title, costBuy, amount, costNow.substring(0,costNow.indexOf(" ")));
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") &&
                getIntent().hasExtra("title") &&
                getIntent().hasExtra("amount") &&
                getIntent().hasExtra("costBuy") &&
                getIntent().hasExtra("costNow") &&
                getIntent().hasExtra("profitFromBuy") &&
                getIntent().hasExtra("sum") &&
                getIntent().hasExtra("typeAsset")){
            //Getting
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            amount = getIntent().getStringExtra("amount");
            costBuy = getIntent().getStringExtra("costBuy");
            costNow = getIntent().getStringExtra("costNow");
            profitFromBuy = getIntent().getStringExtra("profitFromBuy");
            sum = getIntent().getStringExtra("sum");
            typeAsset = getIntent().getStringExtra("typeAsset");

            //Setting
            titleUp.setText(title);
            amountUp.setText(amount);
            costBuyUp.setText(costBuy.substring(0,costBuy.indexOf(" ")));

    }else{
        Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
    }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить " + title + " ?");
        builder.setMessage("Вы уверены что хотите удалить " + title + " ?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDBHelper myDB = new MyDBHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}