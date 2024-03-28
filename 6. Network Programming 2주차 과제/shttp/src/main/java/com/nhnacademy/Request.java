package com.nhnacademy;

import java.util.HashMap;
import java.util.Map;

public class Request {
    public static final String FIELD_CONTENT_LENGTH = "content-length";
    public static final String CRLF = "\r\n";
    
    // 첫째 줄 (ex : GET /get HTTP/1.1)
    String method;
    String path;
    String version;
    
    // 둘째 줄 (ex : Host: www.naver.com)
    char[] body;

    // 둘째 줄 나눠 가짐 (ex : <Host, www.naver.com>)
    Map<String, String> fieldMap;

    // GET /get HTTP/1.1이 넘어 왔다고 치자.
    public Request(String method, String path, String version) {
        this.method = method; // GET
        this.path = path; // /get
        this.version = version; // HTTP/1.1
        this.fieldMap = new HashMap<>();
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    public void setBody(char[] body) {
        this.body = body;
    }

    // Host: www.naver.com 같은 것들이 넘어 올 것으로 기대됨.
    public void addField(String line) {
        String[] fields = line.split(":", 2);
        if (fields.length != 2) {
            throw new InvalidHttpRequestException();
        }
        addField(fields[0].trim(), fields[1].trim()); // 그러면 또 Key, Value로 넘겨
    }

    // Key : "Host"
    // Value : "naver.com"
    public void addField(String key, String value) {
        if (key.equalsIgnoreCase(FIELD_CONTENT_LENGTH)) {
            try {
                Integer.parseInt(value); // 이게 어떻게 정수가 되나?
            } catch (NumberFormatException ignore) {
                throw new InvalidHttpRequestException();
            }
        }
        fieldMap.put(key.toLowerCase(), value);// host, www.naver.com????
    }

    public String getField(String key) {
        return fieldMap.get(key);
    }

    public boolean hasField(String key) {
        return fieldMap.containsKey(key);
    }

    public int getContentLength() {
        return Integer.parseInt(getField(FIELD_CONTENT_LENGTH));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("%s %s %s%s", getMethod(), getPath(), getVersion(), CRLF));
        fieldMap.forEach((k, v) -> builder.append(String.format("%s: %s%s", k, v, CRLF)));

        return builder.toString();
    }
}
