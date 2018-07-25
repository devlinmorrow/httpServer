import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class RequestParser {

    private Request currentRequest;
    private String[] requestLine;

    public RequestParser(Request currentRequest) {
        this.currentRequest = currentRequest;
    }

    public Request assembleRequest(InputStream clientIn) {
        BufferedReader reader = attachReader(clientIn);
        requestLine = extractLine(reader).split(" ");
        setHTTPVerb();
        setURI();
        return currentRequest;
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

    private void setHTTPVerb() {
        if (requestLine[0].equals("GET")) {
            currentRequest.setHttpVerb(HTTPVerb.GET);
        }
    }

    private void setURI() {
        currentRequest.setURI(requestLine[1]);
    }

}
