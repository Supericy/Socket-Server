package kosiec;

import kosiec.Server.Client;
import kosiec.Server.Handler;
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

		try
		{
			server.acceptAndHandleClient();
			fail("Server should throw IOException, since socket hasn't connected");
		}
		catch (IOException e) {}


		// make sure we've handled the socket
//		assertTrue(mockHandler.handled);
//		assertNotNull(mockHandler.client);

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

	static class MockHandler implements Handler<Client> {

		Client client = null;
		boolean handled = false;

		@Override
		public void handle(Client socket)
		{
			client = socket;
			handled = true;
		}

	}

}