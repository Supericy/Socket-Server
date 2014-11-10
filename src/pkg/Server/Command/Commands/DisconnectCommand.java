package pkg.Server.Command.Commands;

import pkg.Server.Client;
import pkg.Server.Command.Command;
import pkg.Server.Server;
import pkg.Server.UserInterface;

import java.io.IOException;

/**
 * Created by Chad on 11/1/2014.
 */
public class DisconnectCommand implements Command {

	private static final String NO_REASON = "not specified";

	protected UserInterface ui;
	private final Server server;

	public DisconnectCommand(UserInterface ui, Server server)
	{
		this.ui = ui;
		this.server = server;
	}

	@Override
	public void execute(Client client, String[] args)
	{
		String reason = (args.length > 0 ? args[0] : NO_REASON);

		try
		{
			if (client.isConnected())
			{
				// send reason to client?
				client.send("Disconnected;" + reason);

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

		ui.display("Client disconnected from: " + client.getInetAddress() + "; reason: " + reason);
		server.removeClient(client);
	}
}
