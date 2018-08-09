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
import static org.junit.Assert.assertFalse;

public class FormHandlerTest {

    private final String testRootPath = "src/test/resources";
    private final String formPath = "/cat-form/data";
    private final String fullPath = testRootPath + formPath;

    @Test
    public void getResponse_getForm_dataNotFound() {
        Request request = new Request(HTTPVerb.GET, formPath, new HashMap<>(), "");
        FormFields formFields = new FormFields(new HashMap<>());
        FormHandler formHandler = new FormHandler(testRootPath, formFields);

        Response response = formHandler.getResponse(request);

        assertEquals(ResponseStatus.NOTFOUND, response.getStatus());
    }

    @Test
    public void getResponse_getForm_dataExists() {
        Request request = new Request(HTTPVerb.GET, formPath, new HashMap<>(), "");
        HashMap<String, String> dataField = new HashMap<>();
        dataField.put("data", "fatcat");
        FormFields formFields = new FormFields(dataField);
        FormHandler formHandler = new FormHandler(testRootPath, formFields);

        Response response = formHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals("data=fatcat".getBytes(), response.getBodyContent());
    }

    @Test
    public void getResponse_postAsForm() {
        Request request = new Request(HTTPVerb.POST, formPath, new HashMap<>(),
                "data=fatcat");
        FormFields formFields = new FormFields(new HashMap<>());
        FormHandler formHandler = new FormHandler(testRootPath, formFields);

        Response response = formHandler.getResponse(request);

        System.out.println(new String(response.getHeaders().get(ResponseHeader.LOCATION)));

        assertEquals(ResponseStatus.CREATED, response.getStatus());
        assertArrayEquals("/cat-form/data".getBytes(),
                response.getHeaders().get(ResponseHeader.LOCATION));
    }


    @Test
    public void handle_putForm() {
        Request mockGetRequest = new Request(HTTPVerb.PUT, formPath, new HashMap<>(), "data=tabbycat");
        HashMap<String, String> dataField = new HashMap<>();
        dataField.put("data", "fatcat");
        FormFields formFields = new FormFields(dataField);
        FormHandler formHandler = new FormHandler(testRootPath, formFields);

        Response mockResponse = formHandler.getResponse(mockGetRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
    }

    @Test
    public void handle_deleteForm() {
        Request request = new Request(HTTPVerb.DELETE, formPath, new HashMap<>(), "");
        HashMap<String, String> dataField = new HashMap<>();
        dataField.put("data", "fatcat");
        FormFields formFields = new FormFields(dataField);
        FormHandler formHandler = new FormHandler(testRootPath, formFields);

        Response response = formHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertFalse(formFields.getFormFields().containsKey("data"));
    }
}