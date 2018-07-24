import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ConnectionAcceptorTest {

    @Test
    public void acceptsConnection() throws IOException {
        IOHelper serverIO = new IOHelper("");

        IOHelper socketIO = new IOHelper("");
        SocketStub socketStub = new SocketStub(socketIO.getIn(), socketIO.getOut());
        ServerSocketStub serverSocketStub = new ServerSocketStub(socketStub);

        SocketHandler socketHandler = new SocketHandler(new RequestRouter(new GETHandler()));
        ConnectionAcceptor connectionAcceptor =
                new ConnectionAcceptor(serverSocketStub, serverIO.getPrintStream(), socketHandler);

        connectionAcceptor.start();

        assertEquals(Message.CONNECTED.getS(), serverIO.getStringOutput());
    }
}
