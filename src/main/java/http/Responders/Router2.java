package http.Responders;

import http.Requesters.Request;
import http.Responders.Handlers.FormFields;
import http.Responders.Handlers.GETHandler;
import http.Responders.Handlers.Handler;
import http.Responders.Handlers.OPTIONSHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Router2 {

    private final ArrayList<Handler> handlers = new ArrayList<>();

    public Router2(String rootPath) {
        addHandlers(Arrays.asList(
                new GETHandler(rootPath, new FormFields(new HashMap<>())),
                new OPTIONSHandler()
        ));
    }

    public void addHandlers(List<Handler> handlers) {
        for (Handler handler : handlers) {
            addHandler(handler);
        }
    }

    public void addHandler(Handler handler) {
        handlers.add(handler);
    }

    public Response handle(Request request) {
        for (Handler handler : handlers) {
            if (handler.handles(request)) {
                return handler.getResponse(request);
            }
        }
        return methodNotAllowedResponse();
    }

    private Response methodNotAllowedResponse() {
        Response response = new Response();
        response.setStatus(ResponseStatus.METHODNOTALLOWED);
        return response;
    }
}
