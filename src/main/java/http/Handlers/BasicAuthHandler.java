package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;
import http.util.Logger;

import java.util.Base64;

public class BasicAuthHandler extends Handler {

    private Logger logger;
    private final String authenticateMessage;
    private final String authorizationHeader;
    private final String authorisingCreds;

    public BasicAuthHandler(Logger logger) {
        authenticateMessage = "Basic realm=\"Access the logs file.\"";
        authorizationHeader = "Authorization";
        authorisingCreds = "admin:hunter2";
        this.logger = logger;
        addHandledVerb(HTTPVerb.GET);
        addHandledPathSegment("logs");
    }

    @Override
    public Response getResponse(Request request) {
        Response response;
        if (!authorised(request)) {
            response = setUnauthorised();
        } else {
            response = setLogsResponse();
        }
        return response;
    }

    private boolean authorised(Request request) {
        if (authorizeHeaderExists(request)) {
            return checkAuthCreds(request);
        } else {
            return false;
        }
    }

    private boolean authorizeHeaderExists(Request request) {
        return (request.getHeaders().get(authorizationHeader) != null);
    }

    private boolean checkAuthCreds(Request request) {
        String decodedCreds = getDecodedCreds(request.getHeaders().get(authorizationHeader));
        return (decodedCreds).equals(authorisingCreds);
    }

    private String getDecodedCreds(String authorizeInfo) {
        String credentials = authorizeInfo.replace("Basic ", "");
        return new String(Base64.getDecoder().decode(credentials.getBytes()));
    }

    private Response setUnauthorised() {
        Response response = new Response();
        response.setUnauthorisedHeader(authenticateMessage);
        response.setBodyContent(ResponseStatus.UNAUTHORISED.getStatusBody());
        response.setStatus(ResponseStatus.UNAUTHORISED);
        return response;
    }

    private Response setLogsResponse() {
        Response response = new Response();
        response.setBodyContent(logger.getLogsBody().getBytes());
        response.setStatus(ResponseStatus.OK);
        return response;
    }

}
