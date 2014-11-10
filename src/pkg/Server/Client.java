package pkg.Server;

import pkg.Server.Command.Permission;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chad on 11/5/2014.
 */
public class Client {

	private final Socket socket;
	private final BufferedReader in;
	private final PrintStream out;
	private final Set<Permission> permissions;
	private boolean connected;

	public Client(Socket socket) throws IOException
	{
		this.socket = socket;

		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintStream(socket.getOutputStream());

		this.permissions = new HashSet<Permission>();

		this.connected = socket.isConnected();
	}

	public void addPermission(Permission permission)
	{
		permissions.add(permission);
	}

	public boolean hasPermission(Permission permission)
	{
		return permissions.contains(permission);
	}

	public String read() throws IOException
	{
		return in.readLine();
	}

	public void send(String message) throws IOException
	{
		out.println(message);
	}

	public boolean isConnected()
	{
		return connected && socket.isConnected() && !socket.isClosed();
	}

	public void disconnect() throws IOException
	{
		socket.close();

		connected = false;
	}

	public InetAddress getInetAddress()
	{
		return socket.getInetAddress();

	}

    public InetAddress getServerInetAddress()
    {
        return socket.getLocalAddress();
    }
}
