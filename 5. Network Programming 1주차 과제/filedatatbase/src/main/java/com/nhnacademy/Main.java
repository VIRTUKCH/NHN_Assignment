package com.nhnacademy;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

// java -jar recorder -a -t user -i 1234 --name "xtra" -f ./recorder.json
public class Main {
    public static void main(String[] args) {
        // 1. Options 설정
        Options options = new Options();

        // 2. Option들 정의하기
        Option addOption = new Option("a", false, "Add");   //데이터 추가
        Option typeOption = new Option("t", false, "Type"); //데이터 종류
        Option idOption = new Option("i", false, "ID");     //아이디
        Option nameOption = new Option("n", false, "Name"); //이름
        Option listOption = new Option("l", false, "List"); //목록을 보여줌
        Option countOption = new Option("c", false, "Count"); //대전 횟수
        Option winOption = new Option("W", false, "Win"); //승리 횟수
        Option energyOption = new Option("e", false, "Energy"); //체력
        Option helpOption = new Option("h", false, "Help"); //도움말
        Option attackOption = new Option("at", false, "Attack"); //공격력

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

        // 3. 커맨드 라인에서 찢어서 받아 오기
        CommandLineParser parser = new DefaultParser(); // 커맨드 라인을 찢자
        try {
            CommandLine commandLine = parser.parse(options, args); // 애플리케이션에서 인식할 수 있는 모든 옵션들을 정의 + parse 메소드를 사용하여, 프로그램에 전달된 커맨드 라인 인자(args)들을 파싱
            if (commandLine.hasOption(helpOption.getOpt())) { // 혹시 헬프 옵션 있나요?
                HelpFormatter formatter = new HelpFormatter(); // 출력하기 위한 객체 생성
                formatter.printHelp("recoder", options); // 첫 번째 인수 "recoder"는 프로그램의 이름을 나타내고, 두 번째 인수 options는 사용 가능한 모든 커맨드 라인 옵션들을 포함하고 있습니다.
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
    }
}