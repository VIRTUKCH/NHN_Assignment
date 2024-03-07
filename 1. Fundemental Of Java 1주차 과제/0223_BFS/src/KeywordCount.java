import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class KeywordCount {
    static String[] keyWords = { "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "false", "final", "finally",
            "float", "for", "If", "goto", "Implements", "Import", "Instanceof", "Int", "interface", "long", "native",
            "new", "null", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
            "super", "switch", "synchronize", "this", "throw", "throws", "transient", "true", "try", "void", "volatile",
            "while" };

    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        HashMap<String, Integer> hashMap = new HashMap<>();

        for (String keyword : keyWords) {
            hashMap.put(keyword, 0);
        }

        // 1. FileInputStream으로 파일을 바이트 단위로 읽고
        // 2. InputStreamReader로 문자로 변환한 후,
        // 3. BufferedReader로 한 줄씩 읽기 위한 준비를 한다.
        FileInputStream fis = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line;
        while ((line = br.readLine()) != null) { // 파일이 끝날 때까지 라인을 읽어 들여
            for (String keyword : keyWords) { // 키워드를 찢어서 확인
                StringTokenizer st = new StringTokenizer(line, " "); // 라인을 토큰 단위로 찢어

                while (st.hasMoreTokens()) {
                    if (st.nextToken().contains(keyword)) { // 토큰에 있으면
                        hashMap.replace(keyword, hashMap.get(keyword) + 1); // keyWord의 Value를 +1로 수정
                    }
                }
            }
        }

        for (String keyWord : keyWords) {
            if (hashMap.get(keyWord) != 0) {
                System.out.println(keyWord + ": " + hashMap.get(keyWord));
            }
        }

        fis.close();
        br.close();
    }
}