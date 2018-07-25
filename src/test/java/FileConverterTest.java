import org.junit.Test;

import static org.junit.Assert.*;

public class FileConverterTest {

    @Test
    public void convertFile() {
        String file1Contents = "file1 contents";
        FileConverter fileConverter = new FileConverter();

        String actualContents = fileConverter.convertFile("/Users/devlin/cob_spec/public/file1");

        assertEquals(file1Contents, actualContents);
    }
}