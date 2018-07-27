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
        requestLine = extractLine(clientIn).split(" ");
        setHTTPVerb();
        setURI();
        return currentRequest;
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

    private void setHTTPVerb() {
        if (requestLine[0].equals("GET")) {
            currentRequest.setRequestMethod(RequestMethod.GET);
        }
    }

    private void setURI() {
        currentRequest.setURI(HardcodedValues.RESOURCEPATH.getS() + requestLine[1]);
    }

}
