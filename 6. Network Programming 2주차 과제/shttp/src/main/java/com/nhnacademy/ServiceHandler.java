package com.nhnacademy;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Client와 일대일로 소통하는 서버처럼 사용하면 된다.
public class ServiceHandler implements Runnable {
    static final String CRLF = "\r\n";
    Thread thread; // 쓰레드를 포함하고 있는 구조 + Runnable
    Socket socket;
    Logger log;

    public ServiceHandler(Socket socket) {
        this.socket = socket;
        thread = new Thread(this); // 본인의 쓰레드가 본인을 돌림.
        log = LogManager.getLogger(this.getClass().getSimpleName());
    }

    String getFileList(Path path) {
        StringBuilder builder = new StringBuilder();

        try (Stream<Path> stream = Files.list(path)) {
            stream.filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .forEach(p -> builder.append(p.toString()).append(CRLF));
        } catch (IOException ignore) {
            throw new InvalidStatusException(403);
        }

        return builder.toString();
    }

    String getFile(Path path) {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(x -> builder.append(x).append(CRLF));
        } catch (IOException e) {
            throw new InvalidStatusException(403);
        }

        return builder.toString();
    }

    public Response process(Request request) {
        try {
            if (request.getMethod().equals("GET")) {
                Path relativePath = Paths.get("." + request.getPath());

                Response response = new Response(request.getVersion(), 200, "OK");
                StringBuilder contentType = new StringBuilder();
                contentType.append("text");
                if (Files.isDirectory(relativePath)) {
                    contentType.append("; charset=utf-8");

                    response.setBody(getFileList(relativePath).getBytes(StandardCharsets.UTF_8));
                } else if (Files.isRegularFile(relativePath)) {
                    String filename = relativePath.getFileName().toString();
                    if (filename.contains(".")) {
                        throw new UnknownContentTypeException();
                    }

                    contentType.append("/")
                            .append(filename.substring(filename.lastIndexOf(".") + 1))
                            .append("; charset=utf-8");

                    response.setBody(getFile(relativePath).getBytes(StandardCharsets.UTF_8));
                }
                response.addField("content-type", contentType.toString());

                return response;
            }

            throw new InvalidStatusException(400);
        } catch (InvalidStatusException e) {
            return new Response(request.getVersion(), e.getCode());
        }
    }

    // Server가 Client와 연결이 되면, start() 메서드를 부름.
    public void start() {
        thread.start();
    }

    // 연결된 후 실행되는 코드들
    @Override
    public void run() {
        log.trace("Start thread : {}", thread.getId());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedOutputStream writer = new BufferedOutputStream(socket.getOutputStream())) {
            while (!Thread.currentThread().isInterrupted()) {
                String requestLine = reader.readLine();
                if (requestLine == null) {
                    break;
                }

                // [GET FORMAT]
                // 1. [GET /get HTTP/1.1] + \r\n
                // 2. [Host: naver.com] + \r\n
                // 3. \r\n

                // 1. 첫째 줄 받기 [GET /get HTTP/1.1]
                String[] fields = requestLine.split("\\s", 3); // 정규 표현식. 스페이스바, 탭 등으로 구분.
                if (fields.length != 3) {
                    throw new InvalidHttpRequestException();
                }
                // GET /get HTTP/1.1 같은 걸 받으면 -> Request의 생성자로 넘어 감. -> 멤버 변수에 저장만 했다.
                Request request = new Request(fields[0], fields[1], fields[2]);

                String fieldLine;
                while ((fieldLine = reader.readLine()) != null) {
                    if (fieldLine.length() == 0) {
                        break;
                    }
                    request.addField(fieldLine); // 다음 줄에 필드를 더한대.
                }

                // 2. 둘째 줄 받기 [Host: naver.com]
                if (request.hasField(Request.FIELD_CONTENT_LENGTH)) {
                    char[] buffer = new char[request.getContentLength()];

                    int bodyLength = reader.read(buffer, 0, request.getContentLength());
                    if (bodyLength == request.getContentLength()) {
                        request.setBody(buffer);
                    }
                }

                // process 메서드 호출에 request를 넣으면서 + Response 객체 생성
                Response response = process(request);
                log.trace(response);

                writer.write(response.getBytes());
                writer.flush();
            }
        } catch (Exception ignore) {
            //
        } finally {
            try {
                socket.close();
            } catch (IOException ignore) {
                //
            }
        }

        log.trace("Finished thread : {}", thread.getId());
    }
}