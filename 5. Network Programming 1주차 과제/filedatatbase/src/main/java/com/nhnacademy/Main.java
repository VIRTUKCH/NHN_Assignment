package com.nhnacademy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) throws IOException {
        JSONObject userObject = new JSONObject();
        JSONObject itemObject = new JSONObject();
        JSONObject recordObject = new JSONObject();

        JSONArray userObjectArray = new JSONArray();
        JSONArray itemObjectArray = new JSONArray();
        JSONArray recordObjectArray = new JSONArray();

        JSONObject dataObject = new JSONObject(); // 데이터 오브젝트 내에는 유저, 아이템, 레코드 등이 있음.

        JSONObject data = new JSONObject(); // 데이터는 그냥 컨벤션 때문에 묶어 봤음.

        // 1. Options 설정
        Options options = new Options();

        // 2. Option들 정의하기
        Option addOption = new Option("a", false, "Add"); // 데이터 추가
        Option typeOption = new Option("t", true, "Type"); // 데이터 종류
        Option idOption = new Option("i", true, "ID"); // 아이디
        Option nameOption = new Option("n", true, "Name"); // 이름
        Option listOption = new Option("l", false, "List"); // 목록을 보여줌
        Option countOption = new Option("c", false, "Count"); // 대전 횟수
        Option winOption = new Option("W", false, "Win"); // 승리 횟수
        Option helpOption = new Option("h", false, "Help"); // 도움말
        Option energyOption = new Option("e", false, "Energy"); // 체력
        Option attackOption = new Option("at", false, "Attack"); // 공격력
        Option defenceOption = new Option("d", false, "Defence"); // 방어력
        Option movingSpeedOption = new Option("m", false, "MovingSpeed"); // 이동 속도
        Option attackSppedOption = new Option("A", false, "AttackSpeed"); // 공격 속도
        Option historyOption = new Option("L", false, "History"); // 변경 이력
        Option dbFileOption = new Option("f", true, "DB_File"); // 데이터 저장 파일

        options.addOption(addOption);
        options.addOption(typeOption);
        options.addOption(idOption);
        options.addOption(nameOption);
        options.addOption(listOption);
        options.addOption(countOption);
        options.addOption(winOption);
        options.addOption(helpOption);
        options.addOption(energyOption);
        options.addOption(attackOption);
        options.addOption(defenceOption);
        options.addOption(movingSpeedOption);
        options.addOption(attackSppedOption);
        options.addOption(historyOption);
        options.addOption(dbFileOption);

        // 3. 커맨드 라인에서 찢기
        CommandLineParser parser = new DefaultParser(); // 커맨드 라인을 찢자
        try {
            // 애플리케이션에서 인식할 수 있는 모든 옵션들을 정의 + parse 메소드를 사용하여, 프로그램에 전달된 커맨드 라인 인자(args)들을
            // 파싱
            CommandLine commandLine = parser.parse(options, args);

            File file;
            FileWriter fileWriter;            
            FileReader fileReader;

            // 0. 파일에 저장할 수 있는 환경 만들기.
            if (commandLine.hasOption(dbFileOption)) {
                String filepath = commandLine.getOptionValue(dbFileOption.getOpt());
                file = new File(filepath);
            } else {
                file = new File("./recorder.json");
            }
            fileWriter = new FileWriter(file);
            fileReader = new FileReader(file);

            // 0. 헬프 옵션이 들어왔을 경우에
            if (commandLine.hasOption(helpOption.getOpt())) { // 혹시 헬프 옵션 있나요?
                HelpFormatter formatter = new HelpFormatter(); // 출력하기 위한 객체 생성
                // 첫 번째 인수 "recoder"는 프로그램의 이름을 나타내고,
                // 두 번째 인수 options는 사용 가능한 모든 커맨드 라인 옵션들을 포함하고 있습니다.
                formatter.printHelp("recoder", options);
                fileWriter.close();
                fileReader.close();
                return;
            }

            // 1. -a 옵션으로 데이터를 추가한 경우
            if (commandLine.hasOption(addOption.getOpt())) {
                System.out.println(1);
                // 타입 받기
                if (commandLine.hasOption(typeOption)) {
                    String type = commandLine.getOptionValue(typeOption.getOpt());
                    System.out.println(2);

                    // 타입이 유저
                    if (type.equals("user")) {
                        System.out.println(3);
                        if (commandLine.hasOption(idOption) && commandLine.hasOption(nameOption)) {
                            System.out.println(4);
                            String id = commandLine.getOptionValue(idOption.getOpt());
                            String name = commandLine.getOptionValue(nameOption.getOpt()); // name에는 이름이 들어가 있음.
                            User user = new User(id, name);
                            System.out.println(5);
                            JSONObject newUserObject = new JSONObject(user);
                            userObjectArray.put(newUserObject);
                            userObject.put("user", userObjectArray);
                            fileWriter.write(userObject.toString());
                            System.out.println(6);
                            System.out.println("파일 덮어쓰기 성공 ><");
                        }
                    }

                    // 타입이 아이템
                    if (type.equals("item")) {
                        System.out.println("아싸 아이템~!");
                    }

                    // 타입이 전적
                    if (type.equals("record")) {
                        System.out.println("아싸 전적~!");
                    }
                }
            }

            BufferedReader bufferedFileReader = new BufferedReader(fileReader);

            // 예시 3 : java -jar recorder -l -t user -f ./recorder.json
            // => 유저 타입만 골라서 보여주기

            // 2. -l 옵션을 넣어서 목록을 보여 주는 경우.
            if (commandLine.hasOption(listOption)) {

                // 타입을 받아야 함.
                if (commandLine.hasOption(typeOption)) {
                    String type = commandLine.getOptionValue("t");

                    // 타입이 유저
                    if (type.equals("user")) {
                        String line;
                        while ((line = bufferedFileReader.readLine()) != null) {
                            System.out.println(line); // 예를 들어, 한 줄씩 콘솔에 출력할 수 있습니다.
                        }
                    }

                    // 타입이 아이템
                    if (type.equals("item")) {
                        // 아이템만 보여주기
                    }

                    // 타입이 전적
                    if (type.equals("record")) {
                        // 전적만 보여주기
                    }
                }
            }

            // 예시 1 : java -jar recorder -a -t user -i 1234 --name "xtra" -f ./recorder.json
            // 예시 3 : java -jar recorder -l -t user -f ./recorder.json
            fileWriter.close();
            bufferedFileReader.close();
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(0);

            
        }
    }
}