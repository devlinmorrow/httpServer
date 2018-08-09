package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.io.File;

public class FormHandler extends Handler {

    private Response response;
    private Request request;
    private FormFields formFields;
    private String rootPath;

    public FormHandler(String rootPath, FormFields formFields) {
        this.rootPath = rootPath;
        addHandledVerb(HTTPVerb.DELETE);
        addHandledVerb(HTTPVerb.GET);
        addHandledVerb(HTTPVerb.POST);
        addHandledVerb(HTTPVerb.PUT);
        addHandledPathSegment("form");
        this.formFields = formFields;
    }

    @Override
    public Response getResponse(Request request) {
        response = new Response();
        this.request = request;
        HTTPVerb httpVerb = request.getHTTPVerb();
        if (httpVerb == HTTPVerb.GET) {
           response = getResponseToGet();
        } else if (httpVerb == HTTPVerb.POST) {
            response = getResponseToPost();
        } else if (httpVerb == HTTPVerb.PUT) {
            response = getResponseToPut();
        } else if (httpVerb == HTTPVerb.DELETE) {
            response = getResponseToDelete();
        }
        return response;
    }

    private Response getResponseToGet() {
        File resource = new File(rootPath + request.getResourcePath());
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

    private Response getResponseToPost() {
        String[] keyValuePair = request.getBodyContent().split("=");
        String keyOfData = keyValuePair[0];
        String valueOfData = keyValuePair[1];
        formFields.getFormFields().put(keyOfData, valueOfData);
        String formName = request.getResourcePath().split("/")[1];
        response.setLocationHeader("/" + formName + "/" + keyOfData);
        response.setStatus(ResponseStatus.CREATED);
        return response;
    }

    private Response getResponseToPut() {
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

    private Response getResponseToDelete() {
        File resource = new File(rootPath + request.getResourcePath());
        String keyRequest = resource.getName();
        formFields.getFormFields().remove(keyRequest);
        response.setStatus(ResponseStatus.OK);
        return response;
    }
}
