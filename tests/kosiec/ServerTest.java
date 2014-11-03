package kosiec;

import kosiec.Server.SocketHandler;
import kosiec.Server.Server;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;

public class ServerTest {

	@Test
	public void testAcceptAndHandleClient() throws Exception
	{

	}

	@Test
	public void testServerIntegration() throws Exception
	{
		MockServerSocket mockServerSocket = new MockServerSocket();
		MockHandler mockHandler = new MockHandler();

		Server server = new Server(mockServerSocket, mockHandler);

		server.acceptAndHandleClient();

		// make sure we've handled the socket
		assertTrue(mockHandler.handled);
		assertSame(mockHandler.handledSocket, mockServerSocket.ourSocket);

	}

	static class MockServerSocket extends ServerSocket {

		Socket ourSocket = new Socket();

		public MockServerSocket() throws IOException
		{
		}

		@Override
		public Socket accept() throws IOException
		{
			return ourSocket;
		}

	}

	static class MockHandler implements SocketHandler {

		Socket handledSocket = null;
		boolean handled = false;

		@Override
		public void handle(Socket socket)
		{
			handledSocket = socket;
			handled = true;
		}

	}

}