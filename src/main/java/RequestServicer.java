public class RequestServicer {

    private StringBuilder fullResponse;
    private ResponseStatus responseStatus;
    private Request request;
    private Response response;
    private Handler handler;

    public String serviceRequest(Request request) {
        setRequest(request);
        response = new Response();
        handler = findHandlerType();
        responseStatus = handler.executeRequest(request, response);
        return makeResponse();
    }

    private Handler findHandlerType() {
        return (request.getRequestMethod() == RequestMethod.GET) ? new GETHandler() : null;
    }

    private String makeResponse() {
        fullResponse = new StringBuilder();
        makeStatusLine();
        return String.valueOf(fullResponse);
    }

    private void setRequest(Request request) {
        this.request = request;
    }

    private void makeStatusLine() {
        appendHTTPVersion();
        fullResponse.append(responseStatus.getReasonPhrase());
        fullResponse.append(HardcodedValues.BLANKLINE.getS());
        fullResponse.append(response.getBodyContent());
    }

    private void appendHTTPVersion() {
        fullResponse.append(response.getHttpVersion()).append(" ");
    }
}
