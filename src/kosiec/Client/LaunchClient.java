package kosiec.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Chad on 10/31/2014.
 */
public class LaunchClient {

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
		while (client.isOpen() && !Client.DISCONNECT_MSG.equals(msg))
		{
			msg = consoleIn.readLine();
			client.sendMsg(msg);
		}
	}

	public static Client createClient() throws IOException
	{
		final Client client = new Client("127.0.0.1", 5555);

		// start listening for responses on a seperate thread
		new Thread(new Runnable() {
			@Override
			public void run()
			{
				try
				{
					while (client.isOpen() && !Thread.interrupted())
						client.listen();
				}
				catch (IOException e)
				{
					System.err.println("IOException, client has stopped listening");
//					e.printStackTrace();
				}
			}
		}).start();

		return client;
	}

}
