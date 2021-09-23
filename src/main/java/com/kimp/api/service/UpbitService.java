package com.kimp.api.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UpbitService {

    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; //드라이버 ID
    public static final String WEB_DRIVER_PATH = "C:/Users/seongsic/Desktop/chromedriver_win32/chromedriver.exe"; //드라이버 경로

    public Map<String,Double> getList(List<String> coins) throws Exception {
        Map<String,Double> upbitMap =new HashMap<>();
        for (String coin : coins) {
            Double price = getPrice(coin);
            upbitMap.put(coin,price);
        }
        return upbitMap;
    }

    public double getPrice(String coin) throws Exception {
        URL url = new URL("https://api.upbit.com/v1/ticker?markets=KRW-"+coin);
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
        JSONArray jsonArray = (JSONArray) parser.parse(returnData);
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        Double tradePrice = (Double) jsonObject.get("trade_price");
        NumberFormat f = NumberFormat.getInstance();
        f.setGroupingUsed(false);
        con.disconnect();
        return tradePrice;
    }
}
