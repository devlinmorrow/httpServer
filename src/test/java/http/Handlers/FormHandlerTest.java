package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseHeader;
import http.Response.ResponseStatus;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FormHandlerTest {

    private final String testRootPath = "src/test/resources";
    private final String formPath = "/cat-form/data";
    private final HashMap<String, String> emptyHeaders = new HashMap<>();
    private final String emptyBody = "";
    private final String tabbyCatData = "data=tabbycat";
    private final String fatCatData = "data=fatcat";
    private final FormHandler formHandler = new FormHandler(testRootPath);

    @Test
    public void givenGetRequestForFormWithoutRequestedData_setNotFoundResponse() {
        Request getRequest = new Request(HTTPVerb.GET, formPath, emptyHeaders, emptyBody);

        Response getResponse = formHandler.getResponse(getRequest);

        assertEquals(ResponseStatus.NOTFOUND, getResponse.getStatus());
    }

    @Test
    public void givenPostRequestForForm_setDataInFormAndSetOKResponse() {
        Request postRequest = new Request(HTTPVerb.POST, formPath, emptyHeaders, tabbyCatData);
        Response postResponse = formHandler.getResponse(postRequest);

        assertEquals(ResponseStatus.CREATED, postResponse.getStatus());
        assertArrayEquals(formPath.getBytes(),
                postResponse.getHeaders().get(ResponseHeader.LOCATION));

        Request getRequest = new Request(HTTPVerb.GET, formPath, emptyHeaders, emptyBody);
        Response getResponse = formHandler.getResponse(getRequest);

        assertEquals(ResponseStatus.OK, getResponse.getStatus());
        assertArrayEquals(tabbyCatData.getBytes(), getResponse.getBodyContent());
    }

    @Test
    public void givenPutRequestForFormWhenDataAlreadyExists_overwriteDataAndSetOKResponse() {
        Request postRequest = new Request(HTTPVerb.POST, formPath, emptyHeaders, fatCatData);
        formHandler.getResponse(postRequest);

        Request putRequest = new Request(HTTPVerb.PUT, formPath, emptyHeaders, tabbyCatData);
        Response putResponse = formHandler.getResponse(putRequest);

        assertEquals(ResponseStatus.OK, putResponse.getStatus());

        Request getRequest = new Request(HTTPVerb.GET, formPath, emptyHeaders, emptyBody);
        Response getResponse = formHandler.getResponse(getRequest);

        assertEquals(ResponseStatus.OK, getResponse.getStatus());
        assertArrayEquals(tabbyCatData.getBytes(), getResponse.getBodyContent());
    }

    @Test
    public void givenDeleteRequestForFormDataWhenDataExists_deleteDataAndSetOKResponse() {
        Request postRequest = new Request(HTTPVerb.POST, formPath, emptyHeaders, fatCatData);
        formHandler.getResponse(postRequest);

        Request deleteRequest = new Request(HTTPVerb.DELETE, formPath, emptyHeaders, emptyBody);
        Response deleteResponse = formHandler.getResponse(deleteRequest);

        assertEquals(ResponseStatus.OK, deleteResponse.getStatus());

        Request getRequest = new Request(HTTPVerb.GET, formPath, emptyHeaders, emptyBody);
        Response getResponse = formHandler.getResponse(getRequest);

        assertEquals(ResponseStatus.NOTFOUND, getResponse.getStatus());
    }
}