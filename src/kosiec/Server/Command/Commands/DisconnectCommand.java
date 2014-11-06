package kosiec.Server.Command.Commands;

import kosiec.Server.Client;
import kosiec.Server.Command.Command;
import kosiec.Server.UserInterface;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Chad on 11/1/2014.
 */
public class DisconnectCommand implements Command {

	protected UserInterface ui;

	public DisconnectCommand(UserInterface ui)
	{
		this.ui = ui;
	}

	@Override
	public void execute(Client client, String[] args)
	{
		try
		{
			if (client.isConnected())
			{
				client.send("Disconnected");

				try
				{
					// give the client a chance to read the msg
					Thread.sleep(500);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				client.disconnect();
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		ui.display("Client disconnected from: " + client.getInetAddress());
	}
}
