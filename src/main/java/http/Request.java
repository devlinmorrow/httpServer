package http;

public class Request {

    private HTTPVerb HTTPVerb;
    private String URI;

    public Request(HTTPVerb HTTPVerb, String URI) {
        this.HTTPVerb = HTTPVerb;
        this.URI = URI;
    }

    public HTTPVerb getHTTPVerb() {
        return HTTPVerb;
    }

    public String getURI() {
        return URI;
    }
}
