package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.WhichUrl;
import com.example.myapplication.db.MyDBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class AddActivityNext extends AppCompatActivity {

    private ProgressBar progressBar;
    private double amount;
    private EditText edTitle;
    private EditText edAmount;
    private Button btnAdd;
    private RequestQueue requestQueue;
    private static double priceNowUsd;
    private static double priceNow;
    private boolean addOrUpd = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_next);

        progressBar = findViewById(R.id.progressBar);
        progressBar.animate();
        progressBar.setVisibility(View.INVISIBLE);
        requestQueue = Volley.newRequestQueue(this);
        edTitle = findViewById(R.id.edTitle);
        edAmount = findViewById(R.id.edAmount);
        btnAdd = findViewById(R.id.btnAdd);

        doRequestToApi("USD", WhichUrl.learnUrl("USD", null), addOrUpd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String str = edAmount.getText().toString();
                amount = Double.parseDouble(str);
                doRequestToApi(AddActivity.selected, WhichUrl.learnUrl(AddActivity.selected, edTitle.getText().toString().trim()), addOrUpd);
            }
        });
    }
    public void doRequestToApi(String type, String urlAsset, boolean addOrUpd) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlAsset, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean status = false;
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                switch (type){
                    case ("USD"):
                        priceNowUsd = (jsonObject.getJSONArray("response").getJSONObject(0).getDouble("o"));
                        break;
                    case ("??????????"):
                        if(jsonObject.getJSONArray("response").getJSONObject(0).getString("ccy").trim().equals("USD")){
                            priceNow = priceNowUsd * (jsonObject.getJSONArray("response").getJSONObject(0).getDouble("c"));
                        }else{
                            priceNow = (jsonObject.getJSONArray("response").getJSONObject(0).getDouble("c"));
                        }
                        break;
                    case ("????????????"):
                        priceNow = (jsonObject.getJSONArray("response").getJSONObject(0).getDouble("o"));
                        break;
                    case ("????????????????????????"):
                        priceNow = priceNowUsd * (jsonObject.getJSONArray("response").getJSONObject(0).getDouble("o"));
                        break;
                }
                    status = jsonObject.getBoolean("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    addBD(type, status);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddActivityNext.this, "No internet", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }

    public void addBD(String type, boolean status){
        if(type != "USD" && status) {
            MyDBHelper myDB = new MyDBHelper(AddActivityNext.this);
            myDB.addAsset(edTitle.getText().toString().trim().toUpperCase(), Math.round(priceNow * 100.0) / 100.0, amount, AddActivity.selected);
            progressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(AddActivityNext.this, MainActivity.class);
            startActivity(intent);
        }else if(type != "USD"){
            progressBar.setVisibility(View.INVISIBLE);
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.customToast));

            TextView text = layout.findViewById(R.id.text);
            text.setText("?????????? ???? ????????????(");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
    }

    public double getPriceNow(double priceNow) {
        return priceNow;
    }
}