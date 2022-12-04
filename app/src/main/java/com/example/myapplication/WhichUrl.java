package com.example.myapplication;

public class WhichUrl {
    public static String learnUrl(String typeAsset, String asset){
        String urlRequest;
        String key = "IquPwLCbF0VF4kSX6vC0";
        String urlCur = "https://fcsapi.com/api-v3/forex/latest?symbol=" + asset + "/RUB&access_key=" + key;
        String urlUsd = "https://fcsapi.com/api-v3/forex/latest?symbol=USD/RUB&access_key=" + key;
        String urlCry = "https://fcsapi.com/api-v3/crypto/latest?symbol=" + asset + "/USD&access_key=" + key;
        String urlSto = "https://fcsapi.com/api-v3/stock/latest?symbol=" + asset + "&access_key=" + key;
            switch (typeAsset){
                case ("Валюта"):
                    urlRequest = urlCur;
                    break;
                case ("Криптовалюта"):
                    urlRequest = urlCry;
                    break;
                case ("Акция"):
                    urlRequest = urlSto;
                    break;
                default:
                    urlRequest = urlUsd;
                    break;
            }
        return urlRequest;
    }
}
