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
		Client client = new Client("127.0.0.1", 5555);

		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
		PrintStream consoleOut = new PrintStream(System.out);

		while (client.isOpen())
		{
			String msg = consoleIn.readLine();
			client.sendMsg(msg);

			if ("quit".equals(msg))
			{
				consoleOut.println("quit");
				break;
			}

			consoleOut.println(client.readMsg());
		}
	}

}
