package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FormHandlerTest {

    private final static String testRootPath = "src/test/resources";
    private final static String formPath = "/cat-form/data";
    private final static HashMap<String, String> emptyHeaders = new HashMap<>();
    private final static String emptyBody = "";

    @Test
    public void getResponse_getForm_dataNotFound() {
        Request request = new Request(HTTPVerb.GET, formPath, emptyHeaders, emptyBody);
        HashMap<String, String> emptyFormFields = new HashMap<>();

        Response response = getResponse(request, emptyFormFields);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
    }

    @Test
    public void getResponse_getForm_dataExists() {
        Request request = new Request(HTTPVerb.GET, formPath, emptyHeaders, emptyBody);
        HashMap<String, String> formField = createFatCatDataField();

        Response response = getResponse(request, formField);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals("data=fatcat".getBytes(), response.getBodyContent());
    }

    @Test
    public void getResponse_postAsForm() {
        String tabbyCatData = "data=tabbycat";
        Request request = new Request(HTTPVerb.POST, formPath, emptyHeaders, tabbyCatData);
        HashMap<String, String> formField = createFatCatDataField();

        Response response = getResponse(request, formField);

        assertEquals(ResponseStatus.CREATED, response.getStatus());
        assertArrayEquals("/cat-form/data".getBytes(),
                response.getHeaders().get(ResponseHeader.LOCATION));
    }

    @Test
    public void getResponse_putForm() {
        String tabbyCatData = "data=tabbycat";
        Request request = new Request(HTTPVerb.PUT, formPath, emptyHeaders, tabbyCatData);
        HashMap<String, String> formField = createFatCatDataField();

        Response response = getResponse(request, formField);

        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    public void getResponse_deleteForm() {
        Request request = new Request(HTTPVerb.DELETE, formPath, emptyHeaders, emptyBody);
        HashMap<String, String> formField = createFatCatDataField();

        Response response = getResponse(request, formField);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertFalse(formField.containsKey("data"));
    }

    private Response getResponse(Request request, Map<String, String> formFields) {
        FormHandler formHandler = new FormHandler(testRootPath, formFields);
        return formHandler.getResponse(request);
    }

    private HashMap<String, String> createFatCatDataField() {
        HashMap<String, String> formField = new HashMap<>();
        formField.put("data", "fatcat");
        return formField;
    }
}