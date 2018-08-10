package http.Responders.Handlers;

import http.Logger;
import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;

public class BasicAuthHandler extends Handler {

    private Authenticator authenticator;
    private Logger logger;
    private String authenticateMessage;

    public BasicAuthHandler(Logger logger) {
        authenticateMessage = "Basic realm=\"Access the logs file.\"";
        this.logger = logger;
        authenticator = new Authenticator();
        addHandledVerb(HTTPVerb.GET);
        addHandledPathSegment("logs");
    }

    @Override
    public Response getResponse(Request request) {
        Response response;
        String logsAction = authenticator.handleLogs(request);
        response = routeLogs(logsAction);
        return response;
    }

    private Response routeLogs(String logsAction) {
        switch (logsAction) {
            case "NotAllowed":
                return setMethodNotAllowed();
            case "Unauthorised":
                return setUnauthorised();
            default:
                return GETFile();
        }
    }

    private Response setMethodNotAllowed() {
        Response response = new Response();
        response.setStatus(ResponseStatus.METHODNOTALLOWED);
        response.setBodyContent(ResponseStatus.METHODNOTALLOWED.getStatusBody());
        return response;
    }

    private Response setUnauthorised() {
        Response response = new Response();
        response.setUnauthorisedHeader(authenticateMessage);
        response.setBodyContent(ResponseStatus.UNAUTHORISED.getStatusBody());
        response.setStatus(ResponseStatus.UNAUTHORISED);
        return response;
    }

    private Response GETFile() {
        Response response = new Response();
        String fullContents = logger.getLogsBody();
        response.setBodyContent(fullContents.getBytes());
        response.setStatus(ResponseStatus.OK);
        return response;
    }

}
