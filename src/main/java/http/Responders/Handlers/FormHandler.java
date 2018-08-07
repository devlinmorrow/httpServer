package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.io.File;
import java.util.Map;

public class FormHandler implements Handler {

    private Response response;
    private Request request;
    private FormFields formFields;

    public FormHandler(FormFields formFields) {
        this.formFields = formFields;
    }

    @Override
    public Response handle(Request request) {
        response = new Response();
        this.request = request;
        HTTPVerb httpVerb = request.getHTTPVerb();
        if (httpVerb == HTTPVerb.GET) {
           response = respondToGet();
        } else if (httpVerb == HTTPVerb.POST) {
            response = respondToPost();
        } else if (httpVerb == HTTPVerb.PUT) {
            response = respondToPut();
        } else if (httpVerb == HTTPVerb.DELETE) {
            response = respondToDelete();
        }
        return response;
    }

    private Response respondToGet() {
        File resource = new File(request.getURI());
        String keyRequest = resource.getName();
        if (!formFields.getFormFields().containsKey(keyRequest)) {
            response.setStatus(ResponseStatus.NOTFOUND);
            response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBody());
        } else {
            response.setStatus(ResponseStatus.OK);
            response.setBodyContent((keyRequest + "=" + formFields.getFormFields().get(keyRequest)).getBytes());
        }
        return response;
    }

    private Response respondToPost() {
        String[] keyValuePair = request.getBodyContent().split("=");
        String keyOfData = keyValuePair[0];
        String valueOfData = keyValuePair[1];
        formFields.getFormFields().put(keyOfData, valueOfData);
        File resource = new File(request.getURI());
        response.setLocationHeader("/" + resource.getName() + "/" + keyOfData);
        response.setStatus(ResponseStatus.CREATED);
        return response;
    }

    private Response respondToPut() {
        String[] keyValuePair = request.getBodyContent().split("=");
        String keyOfData = keyValuePair[0];
        String valueOfData = keyValuePair[1];
        if (formFields.getFormFields().containsKey(keyOfData)) {
            formFields.getFormFields().replace(keyOfData, valueOfData);
        } else {
            formFields.getFormFields().put(keyOfData, valueOfData);
        }
        response.setStatus(ResponseStatus.OK);
        return response;
    }

    private Response respondToDelete() {
        File resource = new File(request.getURI());
        String keyRequest = resource.getName();
        formFields.getFormFields().remove(keyRequest);
        response.setStatus(ResponseStatus.OK);
        return response;
    }
}
