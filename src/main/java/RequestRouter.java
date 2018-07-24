import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class RequestRouter {

    private GETHandler getHandler;
    private RequestAnalyser requestAnalyser;

    public RequestRouter(GETHandler getHandler) {
        this.getHandler = getHandler;
        requestAnalyser = new RequestAnalyser();
    }

    public void route(String request) {
        String currentRequest = findVerb(request);
        if (isGET(currentRequest)) {
            getHandler.handleGET(request);
        }
    }

    private boolean isGET(String httpVerb) {
        return httpVerb.equals(HTTPVerb.GET.getStringRep());
    }

    private String findVerb(String request) {
        return requestAnalyser.findHTTPVerb(request);
    }

}
