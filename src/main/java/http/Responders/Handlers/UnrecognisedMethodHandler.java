package http.Responders.Handlers;

import http.Requesters.Request;
import http.Responders.Handlers.Handler;
import http.Responders.Response;
import http.Responders.ResponseStatus;

public class UnrecognisedMethodHandler implements Handler {

    @Override
    public Response handle(Request request) {
        Response response = new Response();
        response.setStatus(ResponseStatus.SERVERERROR);
        return response;
    }
}
