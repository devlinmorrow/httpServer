//package http.Responders;
//
//import http.Requesters.HTTPVerb;
//import http.Requesters.Request;
//import http.Responders.Handlers.Handler;
//import http.Responders.Handlers.HandlerFactory;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Router {
//
//    private HandlerFactory handlerFactory;
//    private Response response;
//    private Map<String, Handler> handlers;
//
//    public Router() {
//        handlers = new HashMap<>();
//        handlerFactory = new HandlerFactory();
//    }
//
//    public void addHandler(String handlerIdentifier, Handler handler) {
//        handlers.put(handlerIdentifier, handler);
//    }
//
//    public Handler getHandler(String handlerIdentifier) {
//        return handlers.get(handlerIdentifier);
//    }
//
//    public Response respondTo(Request request) {
//        response = new Response();
//        File resource = new File(request.getFullURI());
//        if (methodNotAllowed(request.getHTTPVerb(), resource.getName())) {
//            setMethodNotAllowedResponse();
//        } else if (tempMoved(resource.getName())) {
//            setFoundResponse();
//        } else {
//            Handler handler = handlerFactory.buildHandler(request);
//            response = handler.handle(request);
//        }
//        return response;
//    }
//
//    private void setFoundResponse() {
//        response.setLocationHeader("/");
//        response.setBodyContent(ResponseStatus.FOUND.getStatusBody());
//        response.setStatus(ResponseStatus.FOUND);
//    }
//
//    private boolean tempMoved(String URI) {
//        return (URI.equals("redirect"));
//    }
//
//    private boolean methodNotAllowed(HTTPVerb httpVerb, String resourceName) {
//        if (resourceName.toLowerCase().contains("logs")) {
//            return httpVerb.isNotAllowedForLogs();
//        } else if (resourceName.toLowerCase().contains("file1")) {
//            return httpVerb.isNotAllowed();
//        } else {
//            return false;
//        }
//    }
//
//    private void setMethodNotAllowedResponse() {
//        response.setStatus(ResponseStatus.METHODNOTALLOWED);
//        response.setBodyContent(ResponseStatus.METHODNOTALLOWED.getStatusBody());
//    }
//
//
//
//}
