package http.util;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class ResourceTypeIdentifierTest {

    @Test
    public void givenTXTFile_getTXTType() {
        String filePath = "src/test/resources/testFile1.txt";
        File resource = new File(filePath);
        ResourceTypeIdentifier resourceTypeIdentifier = new ResourceTypeIdentifier();

        assertEquals(ContentType.TXT, resourceTypeIdentifier.getType(resource));
    }
}