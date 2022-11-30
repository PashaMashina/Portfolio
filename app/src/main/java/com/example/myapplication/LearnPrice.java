package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.myapplication.activity.AddActivity;
import com.example.myapplication.activity.AddActivityNext;
import com.example.myapplication.db.MyDBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class LearnPrice {

    public static double  priceNow;
    static double  priceNowUsd;
    static String type;

    public static void priceAsset(String asset, String typeAsset){
        String key = "IquPwLCbF0VF4kSX6vC0";
        String urlCur = "https://fcsapi.com/api-v3/forex/latest?symbol=" + asset + "/RUB&access_key=" + key;
        String urlUsd = "https://fcsapi.com/api-v3/forex/latest?symbol=USD/RUB&access_key=" + key;
        String urlCry = "https://fcsapi.com/api-v3/crypto/latest?symbol=" + asset + "/USD&access_key=" + key;
        String urlSto = "https://fcsapi.com/api-v3/stock/latest?symbol=" + asset + "&access_key=" + key;
        type = "USD";
        GetURLData usd = new GetURLData();
        usd.execute(urlUsd);
        try {
            usd.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            type = typeAsset;
            switch (typeAsset){
                case ("Валюта"):
                    GetURLData cur = new GetURLData();
                    cur.execute(urlCur);
                    cur.get();
                    break;
                case ("Криптовалюта"):
                    GetURLData cry = new GetURLData();
                    cry.execute(urlCry);
                    cry.get();
                    break;
                case ("Акция"):
                    GetURLData sto = new GetURLData();
                    sto.execute(urlSto);
                    sto.get();
                    break;
                default:
                    break;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class GetURLData extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while((line = reader.readLine()) !=null )
                    buffer.append(line).append("\n");

                    JSONObject jsonObject = new JSONObject(buffer.toString());
                System.out.println(type);

                    switch (type){
                        case ("USD"):
                            System.out.println("НачалоUSD");
                            System.out.println(jsonObject);
                            priceNowUsd = (jsonObject.getJSONArray("response").getJSONObject(0).getDouble("o"));
                            System.out.println(priceNowUsd);
                            break;
                        case ("Акция"):
                            if (jsonObject.getJSONArray("response").getJSONObject(0).getString("ccy").trim().equals("USD")){
                                System.out.println("НачалоAKUSD");
                                System.out.println(jsonObject);
                                priceNow = priceNowUsd * (jsonObject.getJSONArray("response").getJSONObject(0).getDouble("c"));
                                System.out.println(priceNow);
                                break;
                            }else{
                                System.out.println("НачалоAK");
                                System.out.println(jsonObject);
                                priceNow = (jsonObject.getJSONArray("response").getJSONObject(0).getDouble("c"));
                                System.out.println(priceNow);
                                break;
                            }
                        case ("Валюта"):
                            System.out.println("НачалоВАЛ");
                            System.out.println(jsonObject);
                            priceNow = (jsonObject.getJSONArray("response").getJSONObject(0).getDouble("o"));
                            System.out.println(priceNow);
                            break;
                        case ("Криптовалюта"):
                            System.out.println("НачалоКрипто");
                            System.out.println(jsonObject);
                            priceNow = priceNowUsd * (jsonObject.getJSONArray("response").getJSONObject(0).getDouble("o"));
                            System.out.println(priceNow);
                            break;
                    }
                if(jsonObject.getBoolean("status")){
                    System.out.println("ХОРОШО");
                }else{
                    System.out.println("ПЛОХО");
                }

                return buffer.toString();

            } catch (JSONException e) {
                priceNow = 0;
                e.printStackTrace();
                System.out.println("Неподдерживаемый актив");
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null)
                    connection.disconnect();

                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
