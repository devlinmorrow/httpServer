package http;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertArrayEquals;

public class FileContentConverterTest {

    @Test
    public void getFileContents() {
        byte[] file1Contents = "file1 contents\n".getBytes();
        FileContentConverter fileContentConverter = new FileContentConverter();

        byte[] actualContents = fileContentConverter.getContents(new File
                ("src/test/resources/dummyFile1.txt"));

        assertArrayEquals(file1Contents, actualContents);
    }
}