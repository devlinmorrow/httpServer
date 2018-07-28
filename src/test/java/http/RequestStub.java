package http;

public class RequestStub extends Request {

    private HTTPVerb HTTPVerb;
    private String URI;

    public RequestStub(HTTPVerb HTTPVerb, String URI) {
        super(HTTPVerb, URI);
    }
}
