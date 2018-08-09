package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ParametersHandlerTest {

    HashMap<String, String> emptyHeaders = new HashMap<>();
    String emptyBody = "";

    @Test
    public void respondTo_oneQueryParam() {
        ParametersHandler parametersHandler = new ParametersHandler();
        Request request = new Request(HTTPVerb.GET,
                "/parameters?variable_1=a%20query%20string%20parameter",
                emptyHeaders, emptyBody);

        Response response = parametersHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals("variable_1 = a query string parameter\n".getBytes(),
                response.getBodyContent());
    }

    @Test
    public void respondTo_twoQueryParams() {
        ParametersHandler parametersHandler = new ParametersHandler();
        Request request = new Request(HTTPVerb.GET, "/parameters?variable_1=" +
                "Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2" +
                    "C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20" +
                    "that%20all%22%3F&variable_2=stuff", emptyHeaders, emptyBody);

        Response response = parametersHandler.getResponse(request);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertArrayEquals(("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]:" +
                        " \"is that all\"?\nvariable_2 = stuff\n").getBytes(),
                response.getBodyContent());
    }
}