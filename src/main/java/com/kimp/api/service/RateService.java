package com.kimp.api.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Service
public class RateService {

    public double getRate() throws Exception {
        String URL = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%ED%99%98%EC%9C%A8";
        Double dRate = 0.0;
        try {
            Connection conn = Jsoup.connect(URL);
            Document html = conn.get(); // conn.post();
            Elements rateElements = html.getElementsByClass("rate_tlt");
            for (Element rateElement : rateElements) {
                Elements elements = rateElement.getElementsByTag("strong");
                Element elm = elements.get(0);
                String text = elm.text();
                if(text.contains(",")){
                    text = text.replace(",","");
                }
                dRate = Double.parseDouble(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dRate;
    }

}
