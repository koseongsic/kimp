package com.kimp.api.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BybitService {

    public Map<String,Double> getList(List<String> coins) throws Exception {
        Map<String,Double> bybitMap =new HashMap<>();
        for (String coin : coins) {
            Double price = getPrice(coin);
            bybitMap.put(coin,price);
        }
        return bybitMap;
    }
    public Double getPrice(String coin) throws Exception {
        URL url = new URL("https://api.bybit.com/v2/public/liq-records?symbol="+coin+"USDT");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.connect();
        //http 요청 후 응답 받은 데이터를 버퍼에 쌓는다
        String responseData = "";
        BufferedReader br = null;
        StringBuffer sb = null;
        br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        sb = new StringBuffer();
        while ((responseData = br.readLine()) != null) {
            sb.append(responseData); //StringBuffer에 응답받은 데이터 순차적으로 저장 실시
        }
        String returnData = sb.toString();

        //http 요청 응답 코드 확인 실시
        String responseCode = String.valueOf(con.getResponseCode());
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(returnData);
        JSONArray jsonArray = (JSONArray) jsonObject.get("result");
        JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
        Object price = jsonObject1.get("price");
        double dp = 0;
        if(price instanceof Double){
            dp = (double) price;
        }else if(price instanceof Long){
            Long lp = (Long) price;
            dp =lp.doubleValue();
        }
        return dp;
    }

}
