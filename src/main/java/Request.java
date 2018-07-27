public class Request {

    private RequestMethod RequestMethod;
    private String URI;

    public void setRequestMethod(RequestMethod RequestMethod) {
        this.RequestMethod = RequestMethod;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public RequestMethod getRequestMethod() {
        return RequestMethod;
    }

    public String getURI() {
        return URI;
    }
}
