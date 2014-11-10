package pkg.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chad on 11/1/2014.
 */
public class Client {

	public static final String DISCONNECT_COMMAND = "Disconnect";
	public static final String DISCONNECTED_RESPONSE = "Disconnected";

	private Socket socket;
	private BufferedReader in;
	private PrintStream out;
	private String ip;
	private int port;
	private final List<ResponseListener> listeners;
	private boolean connected;
	private Thread listenerThread;

	public Client(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
		this.listeners = new ArrayList<ResponseListener>();
		this.connected = false;
		this.listenerThread = null;

		addListener(new ResponseListener() {
			@Override
			public void handle(String response)
			{
				if (response.startsWith(DISCONNECTED_RESPONSE))
					disconnect(false, false);
			}
		});
	}

	public void listen()
	{
		listenerThread = new Thread(new Runnable() {
			@Override
			public void run()
			{
				try
				{
					while (isConnected() && !Thread.interrupted())
					{
						emit(readMsg());
					}
				}
				catch (IOException e)
				{
					System.err.println("Unexpected server shutdown");
//					disconnect(false, true);
					emit("Disconnected");
				}
			}
		});

		listenerThread.setDaemon(true);
		listenerThread.start();
	}

	public void connect() throws IOException
	{
		socket = new Socket();

		socket.connect(new InetSocketAddress(ip, port), 2000);

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintStream(socket.getOutputStream());

		connected = true;
	}

	public void disconnect()
	{
		disconnect(true, false);
	}

	public void disconnect(boolean disconnectHandshake, boolean forceEmit)
	{
		try
		{
			if (disconnectHandshake)
				sendMsg(DISCONNECT_COMMAND);

			if (forceEmit)
				emit(DISCONNECTED_RESPONSE);

			in.close();
			out.close();

			if (socket != null)
			{
				socket.close();
				socket = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		connected = false;
	}

	public boolean isConnected()
	{
		return socket != null && socket.isConnected() && connected;
	}

	public void sendMsg(String msg) throws IOException
	{
		if (!isConnected())
			throw new IOException("socket closed");

		if (msg == null)
			throw new IOException("msg can't be null");

		out.println(msg);
	}

	public String readMsg() throws IOException
	{
		if (!isConnected())
			throw new IOException("socket closed");

		return in.readLine();
	}

	public void addListener(ResponseListener listener)
	{
		listeners.add(listener);
	}

	public void emit(String msg)
	{
		if (msg != null)
		{
			for (ResponseListener listener : listeners)
			{
				listener.handle(msg);
			}
		}
	}

}