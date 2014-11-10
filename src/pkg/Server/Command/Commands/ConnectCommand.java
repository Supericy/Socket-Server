package pkg.Server.Command.Commands;

import pkg.Server.Client;
import pkg.Server.Command.Command;
import pkg.Server.UserInterface;

import java.io.IOException;

/**
 * Created by Chad on 11/5/2014.
 */
public class ConnectCommand implements Command {

	private final UserInterface ui;

	public ConnectCommand(UserInterface ui)
	{
		this.ui = ui;
	}

	@Override
	public void execute(Client client, String[] args)
	{
		try
		{
			client.send(client.connectedToInetAddress().toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		ui.display("Client connected from: " + client.getInetAddress());
	}

}
