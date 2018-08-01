package http.Requesters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class RequestReader {

    private BufferedReader reader;

    public RequestReader(InputStream clientIn) {
        reader = new BufferedReader(new InputStreamReader(clientIn));
    }

    public String extractRequestLine() {
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public String extractHeaders() {
        StringBuilder headers = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    break;
                } else {
                    headers.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(headers);
    }

    public String extractBodyContent(int contentLength) {
        String body = "";
        char[] buffer = new char[contentLength];
        try {
            reader.read(buffer);
            for (char s : buffer) {
                body += s;
            }
            } catch (IOException e) {
            e.printStackTrace();
        }
        return body.trim();
    }

}
