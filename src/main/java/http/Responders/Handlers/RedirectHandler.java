package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;

public class RedirectHandler extends Handler {

    public RedirectHandler() {
        addHandledVerb(HTTPVerb.GET);
        addHandledPathSegment("redirect");
    }

    @Override
    public Response getResponse(Request request) {
        Response response = new Response();
        response.setLocationHeader("/");
        response.setBodyContent(ResponseStatus.FOUND.getStatusBody());
        response.setStatus(ResponseStatus.FOUND);
        return response;
    }

}
