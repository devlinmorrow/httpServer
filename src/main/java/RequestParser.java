import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class RequestParser {


    public String getURI(InputStream clientIn) {
        BufferedReader reader = attachReader(clientIn);
        String requestLine = extractLine(reader);
        return findURI(requestLine);
    }

    private BufferedReader attachReader(InputStream clientIn) {
        return new BufferedReader(new InputStreamReader(clientIn));
    }

    private String extractLine(BufferedReader reader) {
        String requestLine = null;
        try {
            requestLine = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestLine;
    }

    private String findURI(String requestLine) {
        String[] requestLineWords = requestLine.split(" ");
        return requestLineWords[1];
    }

}
