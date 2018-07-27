import java.io.File;

public class RequestServicer {

    private Request request;
    private Response response;
    private Handler handler;
    private ResponseBuilder responseBuilder;
    private File requestedFile;

    public byte[] respondTo(Request request) {
        this.request = request;
        responseBuilder = new ResponseBuilder();
        response = new Response();
        requestedFile = new File(request.getURI());
        actionRequest();
        return responseBuilder.makeResponse(response);
    }

    private void actionRequest() {
        if (requestCannotBeServiced()) {
            set404();
        } else {
            setResourceType();
            handler = findHandlerType();
            handler.executeRequest(request, response);
        }
    }

    private boolean requestCannotBeServiced() {
        return resourceDoesNotExist();
    }

    private void set404() {
        response.setStatus(ResponseStatus.FOUROHFOUR);
        response.setBodyContent(ResponseStatus.FOUROHFOUR.getStatusBody());
    }

    private boolean resourceDoesNotExist() {
        return !requestedFile.exists();
    }

    private void setResourceType() {
        String resourceName = requestedFile.getName();
        if (resourceName.contains(".jpeg")) {
            response.setContentType(ContentType.JPEG);
        } else if (resourceName.contains(".gif")) {
            response.setContentType(ContentType.GIF);
        } else if (resourceName.contains(".png")) {
            response.setContentType(ContentType.PNG);
        } else {
            response.setContentType(ContentType.TXT);
        }
    }

    private Handler findHandlerType() {
        return (request.getRequestMethod() == RequestMethod.GET) ? new GETHandler() : null;
    }

}
