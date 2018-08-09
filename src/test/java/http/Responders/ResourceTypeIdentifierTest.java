package http.Responders;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class ResourceTypeIdentifierTest {

    private String mockFileURI = "src/test/resources/testFile1.txt";

    @Test
    public void getType_TXT() {
        File resource = new File(mockFileURI);
        ResourceTypeIdentifier resourceTypeIdentifier = new ResourceTypeIdentifier();

        assertEquals(ContentType.TXT, resourceTypeIdentifier.getType(resource));
    }
}