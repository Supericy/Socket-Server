package kosiec.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Chad on 11/1/2014.
 */
public class Client {

	public static final String DISCONNECT_MSG = "Disconnect";

	private final Socket socket;
	private final BufferedReader in;
	private final PrintStream out;
	private boolean open;
	private final List<ResponseListener> listeners;

	public Client(String ip, int port) throws IOException
	{
		this.socket = new Socket(ip, port);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintStream(socket.getOutputStream());

		this.open = true;
		this.listeners = new ArrayList<ResponseListener>();
	}

	public boolean isOpen()
	{
		return !socket.isClosed() && open;
	}

	public void sendMsg(String msg) throws IOException
	{
		if (!open)
			throw new IOException("socket closed");

		out.println(msg);
	}

	public String readMsg() throws IOException
	{
		if (!open)
			throw new IOException("socket closed");

		return in.readLine();
	}

	public void addListener(ResponseListener listener)
	{
		listeners.add(listener);
	}

	public void listen() throws IOException
	{
		if (!open)
			throw new IOException("socket closed");

		String msg = readMsg();

		if ("Disconnected".equals(msg))
		{
			socket.close();
			open = false;
		}

		for (ResponseListener listener : listeners)
		{
			listener.handle(msg);
		}
	}
}
