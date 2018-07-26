import java.io.File;

public class RequestServicer {

    private StringBuilder fullResponse;
    private Request request;
    private Response response;
    private Handler handler;
    private ResponseBuilder responseBuilder;

    public String serviceRequest(Request request) {
        this.request = request;
        responseBuilder = new ResponseBuilder();
        response = new Response();
        actionRequest();
        return responseBuilder.makeResponse(response);
    }

    private void actionRequest() {
        if (requestCannotBeServiced()) {
            set404();
        } else {
            handler = findHandlerType();
            handler.executeRequest(request, response);
        }
    }

    private void set404() {
        response.setBodyContent(ResponseStatus.FOUROHFOUR.getStatusBody());
        response.setStatus(ResponseStatus.FOUROHFOUR);
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

}
