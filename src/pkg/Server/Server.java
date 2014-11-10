package pkg.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.*;

/**
 * Created by Chad on 10/31/2014.
 */
public class Server {

	private static final Logger log = Logger.getLogger( Server.class.getName() );
	static {
		log.addHandler(new SimpleConsoleHandler());
		log.setLevel(Level.OFF);
	}

	private final ServerSocket serverSocket;
	private final Handler<Client> handler;
	private final List<Client> clientList;

	public Server(ServerSocket serverSocket, Handler<Client> handler) throws IOException
	{
		this.serverSocket = serverSocket;
		this.handler = handler;
		this.clientList = new ArrayList<Client>();
	}

	public void acceptAndHandleClient() throws IOException
	{
		log.log(Level.FINE, "Waiting for client...");
		Socket socket = serverSocket.accept();
		log.log(Level.FINE, "New client", new Object[] {socket.getInetAddress()});

		handler.handle(new Client(socket));
	}

	public List<Client> getClientList()
	{
		return clientList;
	}

	public void addClient(Client client)
	{
		log.log(Level.INFO, "Adding Client to Listing", new Object[] { client });
		clientList.add(client);
	}

	public void removeClient(Client client)
	{
		log.log(Level.INFO, "Removing Client from Listing", new Object[] { client });
		clientList.remove(client);
	}

}
