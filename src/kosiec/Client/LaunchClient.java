package kosiec.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Created by Chad on 10/31/2014.
 */
public class LaunchClient {

	public static Thread listenerThread;

	public static void main(String[] args) throws IOException
	{
		Client client = createClient();

		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

		// new listener to output responses to console
		client.addListener(new ResponseListener() {
			@Override
			public void handle(String response)
			{
				System.out.println(response);
			}
		});

		String msg = null;
		// send all the commands we type
		while (client.isConnected() && listenerThread.isAlive() && !Client.DISCONNECT_MESSAGE.equals(msg))
		{
			msg = consoleIn.readLine();
			client.sendMsg(msg);
		}

		if (listenerThread.isAlive())
			listenerThread.interrupt();
	}

	public static Client createClient() throws IOException
	{
		final Client client = new Client("127.0.0.1", 5556);

		// start listening for responses on a seperate thread
		listenerThread = new Thread(new Runnable() {
			@Override
			public void run()
			{
				try
				{
					while (client.isConnected() && !Thread.interrupted())
						client.listen();
				}
				catch (IOException e)
				{
					System.err.println("IOException: " + e.getMessage());
//					e.printStackTrace();
				}
			}
		});

		listenerThread.start();

		return client;
	}

}
