import java.io.File;

public class RequestServicer {

    private StringBuilder fullResponse;
    private Request request;
    private Response response;
    private Handler handler;

    public String serviceRequest(Request request) {
        setUp(request);
        return makeResponse();
    }

    private void setUp(Request request) {
        this.request = request;
        response = new Response();
        if (requestCannotBeServiced()) {
            response.setBodyContent(ResponseStatus.FOUROHFOUR.getStatusBody());
            response.setStatus(ResponseStatus.FOUROHFOUR);
        } else {
            handler = findHandlerType();
            handler.executeRequest(request, response);
        }
    }

    private boolean requestCannotBeServiced() {
        return resourceDoesNotExist();
    }

    private boolean resourceDoesNotExist() {
        File file = new File(request.getURI());
        return !file.exists();
    }

    private Handler findHandlerType() {
        return (request.getRequestMethod() == RequestMethod.GET) ? new GETHandler() : null;
    }

    private String makeResponse() {
        fullResponse = new StringBuilder();
        makeStatusLine();
        return String.valueOf(fullResponse);
    }

    private void makeStatusLine() {
        appendHTTPVersion();
        fullResponse.append(response.getResponseStatus().getReasonPhrase());
        fullResponse.append(HardcodedValues.BLANKLINE.getS());
        fullResponse.append(response.getBodyContent());
    }

    private void appendHTTPVersion() {
        fullResponse.append(response.getHttpVersion()).append(" ");
    }
}
