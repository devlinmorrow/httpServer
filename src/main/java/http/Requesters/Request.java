package http.Requesters;

import http.HardcodedValues;

import java.util.Map;

public class Request {

    private HTTPVerb HTTPVerb;
    private String resourcePath;
    private Map<String, String> headers;
    private String bodyContent;

    public Request(HTTPVerb HTTPVerb, String resourcePath, Map<String, String> headers, String bodyContent) {
        this.HTTPVerb = HTTPVerb;
        this.resourcePath = resourcePath;
        this.headers = headers;
        this.bodyContent = bodyContent;
    }

    public HTTPVerb getHTTPVerb() {
        return HTTPVerb;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public String getRequestLine() {
        return HTTPVerb + " " + resourcePath + " " + HardcodedValues.HTTPVERSION.getS();
    }

    public String getResourcePath() {
        return resourcePath;
    }
}
