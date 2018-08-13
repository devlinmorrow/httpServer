package http.Handlers;

import http.util.Logger;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RequestRouter {

    private final ArrayList<Handler> handlers = new ArrayList<>();

    public RequestRouter(String rootPath, Logger logger) {
        addHandlers(Arrays.asList(
                new BasicAuthHandler(logger),
                new CookieHandler(),
                new FormHandler(rootPath),
                new RedirectHandler(),
                new TeapotHandler(),
                new ParametersHandler(),
                new DirectoryHandler(rootPath),
                new DeleteHandler(rootPath),
                new GetHandler(rootPath),
                new HeadHandler(rootPath),
                new OptionsHandler(),
                new PatchHandler(rootPath),
                new PutHandler(rootPath)
        ));
    }

    public Response handle(Request request) throws IOException {
        for (Handler handler : handlers) {
            if (handler.handles(request)) {
                return handler.getResponse(request);
            }
        }
        return methodNotAllowedResponse();
    }

    private void addHandlers(List<Handler> handlers) {
        for (Handler handler : handlers) {
            addHandler(handler);
        }
    }

    private void addHandler(Handler handler) {
        handlers.add(handler);
    }

    private Response methodNotAllowedResponse() {
        Response response = new Response();
        response.setStatus(ResponseStatus.METHODNOTALLOWED);
        return response;
    }
}
