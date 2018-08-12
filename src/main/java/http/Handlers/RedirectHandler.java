package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;

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
