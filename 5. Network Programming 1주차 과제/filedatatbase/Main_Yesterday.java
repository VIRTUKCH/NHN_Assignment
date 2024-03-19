package com.nhnacademy;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main_Yesterday {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String[] strings = new String[10];

        FileWriter file = new FileWriter("./recorder.json");

        /*
         * [형식]
         * 1. 추가
         * user add <id> <name>
         * ex) user add 0002 tiger
         * -> sout("사용자 tiger가 추가되었습니다.")
         * 
         * 2. 삭제
         * user del <id>
         * -> sout("사용자 tiger가 삭제되었습니다.")
         * 
         * 3. 목록
         * user list
         * -> 유저 리스트에 있는 객체 싹 다 알려줌
         */

        // JSONArray에 삽입, 삭제, 조회를 어떻게 할 것이냐.
        JSONArray userArray = new JSONArray();
        JSONArray itemArray = new JSONArray();

        while (true) {
            st = new StringTokenizer(br.readLine());

            int i = 0;
            while (st.hasMoreTokens()) {
                strings[i] = st.nextToken();
                i++;
            }

            // 종료하기
            if (strings[0].equals("q") || strings[0].equals("quit")) {
                break;
            }

            // -------------------------------- 유저 데이터 --------------------------------

            // 유저 데이터
            if (strings[0].equals("user")) {
                // 유저 데이터 추가
                if (strings[1].equals("add")) {
                    User user = new User(strings[2], strings[3]);
                    JSONObject userObject = new JSONObject(user); // user의 데이터를 관리하기 위해 JSONObject에 넣었다.
                    userArray.put(userObject); // 다음에 Object를 JSONArray에 넣었음.
                    System.out.println("사용자 " + userObject.getString("name") + "가 추가되었습니다.");

                }

                // 유저 데이터 삭제
                else if (strings[1].equals("del")) {
                    // id가 일치하면 삭제
                    for (int j = 0; j < userArray.length(); j++) {
                        JSONObject object = (JSONObject) userArray.get(j);
                        if (strings[2].equals(object.getString("id"))) {
                            // 삭제하기 코드 짜야 함.
                        }
                    }
                }
                // TODO ObjectMapper
                // 유저 데이터 조회
                else if (strings[1].equals("list")) {
                    System.out.println("ID\tName");
                    for (int j = 0; j < userArray.length(); j++) {
                        JSONObject object = (JSONObject) userArray.get(j);
                        System.out.println(object.getString("id") + "\t" + object.getString("name"));
                    }
                }

            }

            // -------------------------------- 아이템 데이터 --------------------------------

            if (strings[0].equals("item")) {
                // 유저 데이터 추가
                if (strings[1].equals("add")) {
                    Item item = new Item(strings[2], strings[3], Integer.parseInt(strings[4]),
                            Integer.parseInt(strings[5]), Integer.parseInt(strings[6]), Integer.parseInt(strings[7]),
                            Integer.parseInt(strings[8]));
                    JSONObject itemObject = new JSONObject(item); // user의 데이터를 관리하기 위해 JSONObject에 넣었다.
                    itemArray.put(itemObject); // 다음에 Object를 JSONArray에 넣었음.
                    System.out.println("아이템 " + itemObject.getString("model") + "가 추가되었습니다.");
                }

                // 유저 데이터 삭제
                else if (strings[1].equals("del")) {
                    // id가 일치하면 삭제
                    for (int j = 0; j < itemArray.length(); j++) {
                        JSONObject object = (JSONObject) itemArray.get(j);
                        if (strings[2].equals(object.getString("id"))) {
                            // 삭제하기 코드 짜야 함.
                        }
                    }
                }

                // 유저 데이터 조회
                else if (strings[1].equals("list")) {
                    System.out.println("ID\tModel>");
                    for (int j = 0; j < userArray.length(); j++) {
                        JSONObject object = (JSONObject) userArray.get(j);
                        System.out.println(object.getString("id") + "\t" + object.getString("model"));
                    }
                }
            }

            // -------------------------------- 전적 데이터 --------------------------------

            if (strings[0].equals("record")) {
                // 유저 데이터 추가
                if (strings[1].equals("add")) {
                    Record record = new Record(Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
                    JSONObject recordObject = new JSONObject(record); // user의 데이터를 관리하기 위해 JSONObject에 넣었다.
                    itemArray.put(recordObject); // 다음에 Object를 JSONArray에 넣었음.
                    System.out.println("전적이 추가되었습니다.");
                }

                // 유저 데이터 삭제
                else if (strings[1].equals("del")) {
                    // id가 일치하면 삭제
                    for (int j = 0; j < itemArray.length(); j++) {
                        JSONObject object = (JSONObject) itemArray.get(j);
                        if (strings[2].equals(object.getString("id"))) {
                            // 삭제하기 코드 짜야 함.
                        }
                    }
                }

                // 유저 데이터 조회
                else if (strings[1].equals("list")) {
                    System.out.println("ID\tModel>");
                    for (int j = 0; j < userArray.length(); j++) {
                        JSONObject object = (JSONObject) userArray.get(j);
                        System.out.println(object.getString("id") + "\t" + object.getString("model"));
                    }
                }
            }

            // -------------------------------- 변경 이력 데이터 --------------------------------
        }
        file.write(userArray.toString());
        file.flush();
        file.close();
    }
}