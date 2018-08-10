package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FormHandlerTest {

    private final static String testRootPath = "src/test/resources";
    private final static String formPath = "/cat-form/data";
    private final static HashMap<String, String> emptyHeaders = new HashMap<>();
    private final static String emptyBody = "";
    private final static String tabbyCatData = "data=tabbycat";
    private final static String fatCatData = "data=fatcat";

    @Test
    public void getResponse_getForm_dataNotFound() {
        Request getRequest = new Request(HTTPVerb.GET, formPath, emptyHeaders, emptyBody);

        FormHandler formHandler = new FormHandler(testRootPath);
        Response getResponse = formHandler.getResponse(getRequest);

        assertEquals(ResponseStatus.NOTFOUND, getResponse.getStatus());
    }

    @Test
    public void getResponse_postForm() {
        Request postRequest = new Request(HTTPVerb.POST, formPath, emptyHeaders, tabbyCatData);

        FormHandler formHandler = new FormHandler(testRootPath);
        Response postResponse = formHandler.getResponse(postRequest);

        Request getRequest = new Request(HTTPVerb.GET, formPath, emptyHeaders, emptyBody);
        Response getResponse = formHandler.getResponse(getRequest);

        assertEquals(ResponseStatus.CREATED, postResponse.getStatus());
        assertArrayEquals(formPath.getBytes(),
                postResponse.getHeaders().get(ResponseHeader.LOCATION));
        assertEquals(ResponseStatus.OK, getResponse.getStatus());
        assertArrayEquals(tabbyCatData.getBytes(), getResponse.getBodyContent());
    }

    @Test
    public void getResponse_putForm_dataAlreadyExists() {
        Request postRequest = new Request(HTTPVerb.POST, formPath, emptyHeaders, fatCatData);

        FormHandler formHandler = new FormHandler(testRootPath);
        formHandler.getResponse(postRequest);

        Request putRequest = new Request(HTTPVerb.PUT, formPath, emptyHeaders, tabbyCatData);
        Response putResponse = formHandler.getResponse(putRequest);

        Request getRequest = new Request(HTTPVerb.GET, formPath, emptyHeaders, emptyBody);
        Response getResponse = formHandler.getResponse(getRequest);

        assertEquals(ResponseStatus.OK, putResponse.getStatus());
        assertEquals(ResponseStatus.OK, getResponse.getStatus());
        assertArrayEquals(tabbyCatData.getBytes(), getResponse.getBodyContent());
    }

    @Test
    public void getResponse_deleteForm() {
        Request postRequest = new Request(HTTPVerb.POST, formPath, emptyHeaders, fatCatData);

        FormHandler formHandler = new FormHandler(testRootPath);
        formHandler.getResponse(postRequest);

        Request deleteRequest = new Request(HTTPVerb.DELETE, formPath, emptyHeaders, emptyBody);
        Response deleteResponse = formHandler.getResponse(deleteRequest);

        Request getRequest = new Request(HTTPVerb.GET, formPath, emptyHeaders, emptyBody);
        Response getResponse = formHandler.getResponse(getRequest);

        assertEquals(ResponseStatus.OK, deleteResponse.getStatus());
        assertEquals(ResponseStatus.NOTFOUND, getResponse.getStatus());
    }
}