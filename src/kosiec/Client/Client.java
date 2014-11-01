package kosiec.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Chad on 11/1/2014.
 */
public class Client {

	private final Socket socket;
	private final BufferedReader in;
	private final PrintStream out;
	private boolean open;

	public Client(String ip, int port) throws IOException
	{
		this.socket = new Socket(ip, port);
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintStream(socket.getOutputStream());

		this.open = true;
	}

	public boolean isOpen()
	{
		return open;
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

}
