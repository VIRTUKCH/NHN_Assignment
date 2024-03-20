package com.nhnacademy;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonTest {
    public static void main(String[] args) {
        // 기능 배열 생성
        JSONArray 기능Array = new JSONArray();
        기능Array.put("정보검색");
        기능Array.put("데이터분석");
        기능Array.put("사용자지원");

        // 버전 배열 생성
        JSONArray 버전Array = new JSONArray();
        버전Array.put(1);
        버전Array.put(2);
        버전Array.put(3);
        버전Array.put(4);

        // 개발 정보 객체 생성
        JSONObject 개발정보Object = new JSONObject();
        개발정보Object.put("개발사", "뤼튼 Inc.");
        개발정보Object.put("국가", "대한민국");

        // 최종 JSON 객체 생성
        JSONObject 최종객체 = new JSONObject();
        최종객체.put("이름", "뤼튼");
        최종객체.put("유형", "AI 서비스");
        최종객체.put("기능", 기능Array);
        최종객체.put("활성화", true);
        최종객체.put("개발정보", 개발정보Object);
        최종객체.put("버전", 버전Array);

        String jsonString = 최종객체.toString(4);

        try (FileWriter fileWriter = new FileWriter("output.json")) {
            fileWriter.write(jsonString);
            System.out.println("파일이 성공적으로 작성되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
