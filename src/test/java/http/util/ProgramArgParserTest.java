package http.util;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ProgramArgParserTest {

    @Test
    public void givenPortAndDirArgs_setBoth() {
        String portArg = "5000";
        String dirArg = "src/test/resources/";
        String[] programArgs = {"-p", portArg, "-d", dirArg};

        ProgramArgParser programArgParser = new ProgramArgParser(programArgs);

        assertEquals(Integer.parseInt(portArg), programArgParser.getPort());
        assertEquals(dirArg, programArgParser.getRootPath());
    }

    @Test
    public void givenNoPort_defaultExists() {
        String[] emptyArgs = {};
        ProgramArgParser programArgParser = new ProgramArgParser(emptyArgs);

        assertEquals(5000, programArgParser.getPort());
    }
}