public class Request {

    private HTTPVerb httpVerb;
    private String URI;

    public void setHttpVerb(HTTPVerb httpVerb) {
        this.httpVerb = httpVerb;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public HTTPVerb getHttpVerb() {
        return httpVerb;
    }

    public String getURI() {
        return URI;
    }
}
