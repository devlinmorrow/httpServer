package http.server;

import http.util.IOHelper;
import http.util.Logger;
import http.Handlers.RequestRouter;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class httpServerTest {

    private final IOHelper stdIO = new IOHelper("");
    private final PrintStream stdOut = stdIO.getPrintStream();
    private final Logger logger = new Logger();
    private final RequestRouter requestRouter = new RequestRouter("src/test/resources/", logger);
    private final ExecutorWithNoThreadsSpy executorWithNoThreadsSpy = new ExecutorWithNoThreadsSpy();

    @Test
    public void givenTwoRequests_twoConnectionsAreMadeAndTheyAreBothRespondedTo() throws IOException {
        SocketStubSpy clientOne = new SocketStubSpy("GET /testFile1.txt");
        SocketStubSpy clientTwo = new SocketStubSpy("GET /testFile1.txt");
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(Arrays.asList(clientOne, clientTwo));
        ServerStatusStub serverStatusForTwoRequests = new ServerStatusStub(2);

        httpServer httpServer = new httpServer(stdOut, serverSocketSpy,
                serverStatusForTwoRequests, executorWithNoThreadsSpy, requestRouter, logger);

        httpServer.start();

        assertEquals(2, serverSocketSpy.howManyTimesAcceptCalled());
        assertEquals(2, executorWithNoThreadsSpy.getTimesExecuteWasCalled());
    }
}
