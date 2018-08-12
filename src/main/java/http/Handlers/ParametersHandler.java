package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ParametersHandler extends Handler {

    public ParametersHandler() {
        addHandledVerb(HTTPVerb.GET);
        addHandledPathSegment("parameter");
    }

    @Override
    public Response getResponse(Request request) {
        Response response = new Response();
        String body = getAllQParams(request.getResourcePath());
        response.setBodyContent(body.getBytes());
        response.setStatus(ResponseStatus.OK);
        return response;
    }

    private String getAllQParams(String fullURL) {
        String paramNoFirstPart = removeFirstPart(fullURL);
        String[] parameters = paramNoFirstPart.split("&");
        StringBuilder bodyContent = new StringBuilder();
        for (String param : parameters) {
            String[] splitVariable = param.split("=");
            bodyContent.append(decode(splitVariable[0]));
            bodyContent.append(" = ");
            bodyContent.append(decode(splitVariable[1]));
            bodyContent.append("\n");
        }
        return new String(bodyContent);
    }

    private String removeFirstPart(String fullURI) {
        int paramStart = fullURI.indexOf("?");
        return fullURI.substring(paramStart + 1);
    }

    private String decode(String fullParams) {
        String decoded = null;
        try {
            decoded = URLDecoder.decode(fullParams, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decoded;
    }
}
