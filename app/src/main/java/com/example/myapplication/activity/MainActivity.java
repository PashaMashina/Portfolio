package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.CustomerAdapter;
import com.example.myapplication.R;
import com.example.myapplication.db.MyDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addBtn;
    private RecyclerView recyclerView;

    ImageView empty;
    TextView emptyTxt;

    MyDBHelper myDB;
    ArrayList<String> id, title, costNow, costBuy, amount, sum, profitFromBuy, typeAsset;
    CustomerAdapter customerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.addBtn);
        recyclerView = findViewById(R.id.recyclerview);
        empty = findViewById(R.id.empty);
        emptyTxt = findViewById(R.id.emptyTxt);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new MyDBHelper(MainActivity.this);
        id = new ArrayList<>();
        title = new ArrayList<>();
        costNow = new ArrayList<>();
        costBuy = new ArrayList<>();
        amount = new ArrayList<>();
        sum = new ArrayList<>();
        profitFromBuy = new ArrayList<>();
        typeAsset = new ArrayList<>();

        storeDataInArray();

        customerAdapter = new CustomerAdapter(MainActivity.this,this, id, title, costNow, costBuy, amount, sum, profitFromBuy, typeAsset);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArray(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            empty.setVisibility(View.VISIBLE);
            emptyTxt.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                title.add(cursor.getString(1));
                costNow.add(cursor.getString(2));
                costBuy.add(cursor.getString(3));
                amount.add(cursor.getString(4));
                sum.add(cursor.getString(5));
                profitFromBuy.add(cursor.getString(6));
                typeAsset.add(cursor.getString(7));

            }
            empty.setVisibility(View.GONE);
            emptyTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.deleteAll){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить всё?");
        builder.setMessage("Вы уверены что хотите удалить всё?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDBHelper myDB = new MyDBHelper(MainActivity.this);
                myDB.deleteAllData();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                System.out.println("Удалено");
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