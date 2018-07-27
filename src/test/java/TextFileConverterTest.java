import org.junit.Test;

import static org.junit.Assert.*;

public class TextFileConverterTest {

    @Test
    public void getDummyFile1Contents() {
        byte[] file1Contents = "file1 contents\n".getBytes();
        TextFileConverter textFileConverter = new TextFileConverter();

        byte[] actualContents = textFileConverter.getFileContents("src/main/resources/dummyFile1.txt");

        assertArrayEquals(file1Contents, actualContents);
    }
}