package http.ClientConnectors;

import http.IOHelper;
import http.Logger;
import http.Responders.RequestRouter;
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
    public void whenGivenTwoRequests_twoConnectionsAreMade() throws IOException {
        ServerSocketSpy serverSocketSpy = makeServerSocketWithTwoClientRequests();
        httpServer httpServer = new httpServer(stdOut, serverSocketSpy,
                runAcceptConnectionTwice(), executorWithNoThreadsSpy, requestRouter, logger);

        httpServer.start();

        assertEquals(2, serverSocketSpy.howManyTimesAcceptCalled());
    }

    @Test
    public void whenGivenTwoRequests_theyAreBothRespondedTo() throws IOException {
        ServerSocketSpy serverSocketSpy = makeServerSocketWithTwoClientRequests();

        httpServer httpServer = new httpServer(stdOut, serverSocketSpy,
                runAcceptConnectionTwice(), executorWithNoThreadsSpy, requestRouter, logger);

        httpServer.start();

        assertEquals(2, executorWithNoThreadsSpy.getTimesExecuteWasCalled());
    }

    private ServerSocketSpy makeServerSocketWithTwoClientRequests() throws IOException {
        SocketStubSpy clientOne = new SocketStubSpy("GET /testFile1");
        SocketStubSpy clientTwo = new SocketStubSpy("GET /testFile1");
        return new ServerSocketSpy(Arrays.asList(clientOne, clientTwo));
    }

    private ServerStatusStub runAcceptConnectionTwice() {
        return new ServerStatusStub(2);
    }
}
