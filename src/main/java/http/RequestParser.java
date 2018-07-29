package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestParser {

    public Request parse(InputStream clientIn) {
        String[] requestLine = extractLine(clientIn).split(" ");
        return new Request(findHTTPVerb(requestLine[0]), findURI(requestLine[1]));
    }

    private String extractLine(InputStream clientIn) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientIn));
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    private HTTPVerb findHTTPVerb(String requestedVerb) {
        return HTTPVerb.find(requestedVerb);
    }

    private String findURI(String requestLine) {
        return HardcodedValues.RESOURCEPATH.getS() + requestLine;
    }

}
