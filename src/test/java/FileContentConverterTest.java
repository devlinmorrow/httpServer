import org.junit.Test;

import static org.junit.Assert.*;

public class FileContentConverterTest {

    @Test
    public void getDummyFile1Contents() {
        byte[] file1Contents = "file1 contents\n".getBytes();
        FileContentConverter fileContentConverter = new FileContentConverter();

        byte[] actualContents = fileContentConverter.getFileContents("src/main/resources/dummyFile1.txt");

        assertArrayEquals(file1Contents, actualContents);
    }
}