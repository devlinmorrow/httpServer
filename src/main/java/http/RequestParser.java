package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestParser {

    private String[] requestLine;

    public Request parse(InputStream clientIn) {
        requestLine = extractLine(clientIn).split(" ");
        HTTPVerb httpVerb = findHTTPVerb();
        String URI = findURI();
        return new Request(httpVerb, URI);
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

    private HTTPVerb findHTTPVerb() {
        if (requestLine[0].equals("GET")) {
            return HTTPVerb.GET;
        } else {
            return null;
        }
    }

    private String findURI() {
        return HardcodedValues.RESOURCEPATH.getS() + requestLine[1];
    }

}
