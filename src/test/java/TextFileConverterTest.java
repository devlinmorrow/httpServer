import org.junit.Test;

import static org.junit.Assert.*;

public class TextFileConverterTest {

    @Test
    public void readFile1() {
        String file1Contents = "file1 contents";
        TextFileConverter textFileConverter = new TextFileConverter();

        String actualContents = textFileConverter.convertFile("/Users/devlin/cob_spec/public/file1");

        assertEquals(file1Contents, actualContents);
    }

    @Test
    public void readFile2() {
        String file2Contents = "file2 contents\n";
        TextFileConverter textFileConverter = new TextFileConverter();

        String actualContents = textFileConverter.convertFile("/Users/devlin/cob_spec/public/file2");

        assertEquals(file2Contents, actualContents);
    }
}