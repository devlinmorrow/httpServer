package http.Requesters;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private http.Requesters.HTTPVerb HTTPVerb;
    private String URI;
    private Map<String, String> headers;

    public Request(HTTPVerb HTTPVerb, String URI, Map<String, String> headers) {
        this.HTTPVerb = HTTPVerb;
        this.URI = URI;
        this.headers = headers;
    }

    public HTTPVerb getHTTPVerb() {
        return HTTPVerb;
    }

    public String getURI() {
        return URI;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
