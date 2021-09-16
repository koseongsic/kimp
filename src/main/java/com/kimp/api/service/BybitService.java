package com.kimp.api.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Service
public class BybitService {


    public Map<String,String> getList() throws Exception {
        Map<String,String> bybitMap =new HashMap<>();

        String  bybiturl = "https://www.bybit.com/data/markets/spot";
        URL url = new URL(bybiturl); // 호출할 url
        StringBuilder postData = new StringBuilder();
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes); // POST 호출
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String inputLine;
        StringBuilder sb = new StringBuilder();
        while((inputLine = in.readLine()) != null) { // response 출력
            sb.append(inputLine);

        }
        String xml = sb.toString();
        // 자신의 static 메서드를 가지고 객체를 생성 : 싱글톤 패턴
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // 다른 클래스의 객체를 가지고, 객체를 생성하면 팩토리 패턴.
        DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 팩토리 메서드 패턴  공장에서 찍어줌
        // 문자열을 InputStream으로 변환
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        Document doc = documentbuilder.parse(is);
        // xml을 메모리에 펼쳐놓고 루트를 elemnt에 저장
        Element element = doc.getDocumentElement();

        // 파싱할 태그의 리스트를 찾아온다
        // tmx 태그 전체를 list에 저장
        NodeList list = element.getElementsByTagName("markets-tbody__bg");
        for(int i=0; i<list.getLength(); i++){
            Node coinelement = list.item(i);
            getCoinName();
        }

        return bybitMap;
    }

    public String getCoinName(){

        return "";
    }

    public String getPrice(){

        return "";
    }
}
