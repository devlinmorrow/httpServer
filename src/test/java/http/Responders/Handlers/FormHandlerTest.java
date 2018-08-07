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

    @Test
    public void handle_getForm() {
        String mockFormPath = "src/test/resources/cat-form/data";

        Request mockRequest = new Request(HTTPVerb.GET, mockFormPath, new HashMap<>(), "");
        FormFields formFields = new FormFields(new HashMap<>());
        FormHandler formHandler = new FormHandler(formFields);

        Response mockResponse = formHandler.handle(mockRequest);

        assertEquals(ResponseStatus.NOTFOUND, mockResponse.getStatus());
    }

    @Test
    public void handle_postAsForm() {
        String mockFormPath = "src/test/resources/cat-form";

        Request mockRequest = new Request(HTTPVerb.POST, mockFormPath, new HashMap<>(),
                "data=fatcat");
        FormFields formFields = new FormFields(new HashMap<>());
        FormHandler formHandler = new FormHandler(formFields);

        Response mockResponse = formHandler.handle(mockRequest);

        assertEquals(ResponseStatus.CREATED, mockResponse.getStatus());
        assertArrayEquals("/cat-form/data".getBytes(),
                mockResponse.getHeaders().get(ResponseHeader.LOCATION));
    }

    @Test
    public void handle_getForm_afterPostRequest() {
        String mockFormPath = "src/test/resources/cat-form/data";

        Request mockGetRequest = new Request(HTTPVerb.GET, mockFormPath, new HashMap<>(), "");
        HashMap<String, String> dataField = new HashMap<>();
        dataField.put("data", "fatcat");
        FormFields formFields = new FormFields(dataField);
        FormHandler formHandler = new FormHandler(formFields);

        Response mockResponse = formHandler.handle(mockGetRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertArrayEquals("data=fatcat".getBytes(), mockResponse.getBodyContent());
    }

    @Test
    public void handle_putForm() {
        String mockFormPath = "src/test/resources/cat-form/data";

        Request mockGetRequest = new Request(HTTPVerb.PUT, mockFormPath, new HashMap<>(), "data=tabbycat");
        HashMap<String, String> dataField = new HashMap<>();
        dataField.put("data", "fatcat");
        FormFields formFields = new FormFields(dataField);
        FormHandler formHandler = new FormHandler(formFields);

        Response mockResponse = formHandler.handle(mockGetRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
    }

    @Test
    public void handle_deleteForm() {
        String mockFormPath = "src/test/resources/cat-form/data";

        Request mockGetRequest = new Request(HTTPVerb.DELETE, mockFormPath, new HashMap<>(), "");
        HashMap<String, String> dataField = new HashMap<>();
        dataField.put("data", "fatcat");
        FormFields formFields = new FormFields(dataField);
        FormHandler formHandler = new FormHandler(formFields);

        Response mockResponse = formHandler.handle(mockGetRequest);

        assertEquals(ResponseStatus.OK, mockResponse.getStatus());
        assertFalse(formFields.getFormFields().containsKey("data"));
    }
}