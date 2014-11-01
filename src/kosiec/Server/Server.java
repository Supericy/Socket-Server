package kosiec.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Chad on 10/31/2014.
 */
public class Server {

	private final ServerSocket serverSocket;
	private final Handler handler;

	public Server(ServerSocket serverSocket, Handler handler) throws IOException
	{
		this.serverSocket = serverSocket;
		this.handler = handler;
	}

	public void acceptAndHandleClient() throws IOException
	{
		Socket socket = serverSocket.accept();

		handler.handle(socket);
	}

}
