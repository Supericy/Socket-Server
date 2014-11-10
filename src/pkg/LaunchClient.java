package pkg;

import pkg.Client.Client;
import pkg.Client.ResponseListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Chad on 10/31/2014.
 */
public class LaunchClient {

	public static final String SERVER_ADDRESS = "192.168.0.100";
	public static final int SERVER_PORT = 5555;

	public static void main(String[] args) throws IOException, InterruptedException
	{
		Client client = createClient();

		final BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

		// set the empty so that we can enter the loop at least once
		String msg = "";
		// send all the commands we type
		while (client.isConnected() && msg != null && !Client.DISCONNECT_COMMAND.equals(msg))
		{
			msg = consoleIn.readLine();
			if (msg != null)
				client.sendMsg(msg);
		}

		// give the listener a chance to read any final messages before force closing
		Thread.sleep(500);
	}

	public static Client createClient() throws IOException
	{
		final Client client = new Client(SERVER_ADDRESS, SERVER_PORT);

		// new listener to output responses to console
		client.addListener(new ResponseListener() {
			@Override
			public void handle(String response)
			{
				System.out.println("ServerResponse: " + response);
			}
		});

		client.connect();
		client.listen();

		return client;
	}

}
