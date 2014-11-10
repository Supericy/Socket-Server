package pkg.Server.Command.Commands;

import pkg.Server.Client;
import pkg.Server.Command.Command;
import pkg.Server.Server;
import pkg.Server.UserInterface;

import java.io.IOException;

/**
 * Created by Chad on 11/5/2014.
 */
public class ConnectCommand implements Command {

	private final UserInterface ui;
	private final Server server;

	public ConnectCommand(UserInterface ui, Server server)
	{
		this.ui = ui;
		this.server = server;
	}

	@Override
	public void execute(Client client, String[] args)
	{
		try
		{

			client.send("Connected to:" + client.getServerInetAddress().toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		ui.display("Client connected from: " + client.getInetAddress());
		server.addClient(client);
	}

}
