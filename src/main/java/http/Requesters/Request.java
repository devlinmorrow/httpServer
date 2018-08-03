package http.Requesters;

import http.HardcodedValues;

import java.util.Map;

public class Request {

    private HTTPVerb HTTPVerb;
    private String URI;
    private Map<String, String> headers;
    private String bodyContent;

    public Request(HTTPVerb HTTPVerb, String URI, Map<String, String> headers, String bodyContent) {
        this.HTTPVerb = HTTPVerb;
        this.URI = URI;
        this.headers = headers;
        this.bodyContent = bodyContent;
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

    public String getBodyContent() {
        return bodyContent;
    }

    public String getRequestLine() {
        String lastPartURI = URI.replace(HardcodedValues.RESOURCEPATH.getS(), "");
        return HTTPVerb + " " + lastPartURI + " " + HardcodedValues.HTTPVERSION.getS();
    }
}
