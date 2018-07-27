import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestParser {

    private Request currentRequest;
    private String[] requestLine;

    public RequestParser(Request currentRequest) {
        this.currentRequest = currentRequest;
    }

    public Request parseRequest(InputStream clientIn) {
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
            currentRequest.setRequestMethod(RequestMethod.GET);
        }
    }

    private void setURI() {
        currentRequest.setURI(HardcodedValues.RESOURCEPATH.getS() + requestLine[1]);
    }

}
