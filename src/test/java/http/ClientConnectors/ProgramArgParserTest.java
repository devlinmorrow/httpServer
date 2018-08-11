package http.ClientConnectors;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ProgramArgParserTest {

    @Test
    public void whenGivenPortNumberArg_setPort() {
        String portArg = "5000";
        int port = Integer.parseInt(portArg);
        String[] programArgs = {"-p", portArg};

        int defaultPort = 5000;
        ProgramArgParser programArgParser = new ProgramArgParser(programArgs, defaultPort);

        assertEquals(port, programArgParser.getPort());
    }

    @Test
    public void whenGivenDirArg_setRootDir() {
        String dirArg = "src/test/resources/";
        String[] programArgs = {"-d", dirArg};

        int defaultPort = 5000;
        ProgramArgParser programArgParser = new ProgramArgParser(programArgs, defaultPort);

        assertEquals(dirArg, programArgParser.getRootPath());
    }

    @Test
    public void whenGivenPortAndDirArgs_setBoth() {
        String portArg = "5000";
        int port = Integer.parseInt(portArg);
        String dirArg = "src/test/resources/";
        String[] programArgs = {"-p", portArg, "-d", dirArg};

        int defaultPort = 5000;
        ProgramArgParser programArgParser = new ProgramArgParser(programArgs, defaultPort);

        assertEquals(port, programArgParser.getPort());
        assertEquals(dirArg, programArgParser.getRootPath());
    }

    @Test
    public void whenNotGivenPort_setDefault() {
        String[] programArgs = {};
        int defaultPort = 5000;
        ProgramArgParser programArgParser = new ProgramArgParser(programArgs, defaultPort);

        assertEquals(5000, programArgParser.getPort());
    }
}