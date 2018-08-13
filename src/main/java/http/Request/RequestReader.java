package http.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestReader {

    private BufferedReader reader;

    public RequestReader(InputStream clientIn) {
        reader = new BufferedReader(new InputStreamReader(clientIn));
    }

    public String extractRequestLine() throws IOException {
        return reader.readLine();
    }

    public String extractHeaders() throws IOException {
        StringBuilder headers = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            } else {
                headers.append(line).append("\n");
            }
        }
        return new String(headers);
    }

    public String extractBodyContent(int contentLength) throws IOException {
        StringBuilder body = new StringBuilder();
        char[] buffer = new char[contentLength];
        reader.read(buffer);
        for (char s : buffer) {
            body.append(s);
        }
        return body.toString().trim();
    }

}
