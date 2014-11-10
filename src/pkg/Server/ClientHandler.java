package pkg.Server;

import pkg.Server.Command.*;

import java.io.IOException;

/**
 * Created by Chad on 10/31/2014.
 */
public class ClientHandler implements Handler<Client> {

	private final CommandTranslator commandTranslator;
	private final CommandFactory commandFactory;
	private final UserInterface ui;
	private final ClientAuthorizationService authorizationService;

	public ClientHandler(CommandTranslator commandTranslator, CommandFactory commandFactory, UserInterface ui, ClientAuthorizationService authorizationService)
	{
		this.commandTranslator = commandTranslator;
		this.commandFactory = commandFactory;
		this.ui = ui;
		this.authorizationService = authorizationService;
	}

	public void handle(Client client)
	{
		try
		{
			// execute a connect command
			commandFactory.make("Connect")
						  .execute(client, Command.NO_ARGS);

			// ensure client sends the correct authorization code
			if (!authorizationService.authorize(client, client.read()))
			{
				commandFactory.make("Disconnect")
						.execute(client, new String[] { ClientAuthorizationService.UNAUTHORIZED_MESSAGE });
			}

			// add permissions
			client.addPermission(Permission.GENERAL_ACCESS);
			client.send("Access Granted");

			while (client.isConnected() && client.hasPermission(Permission.GENERAL_ACCESS))
			{
				try
				{
					String line = client.read();

					if (line != null)
					{
						MetaCommand meta = commandTranslator.decode(line);

						// Connect command is off limits from the client, since we only execute it once.
						if (!meta.getCommandName().equals("Connect"))
						{
							Command c = commandFactory.make(meta.getCommandName());
							c.execute(client, meta.getCommandArgs());
						}
					}
				}
				catch (CommandException e)
				{
					handleException(client, e);
				}
			}
		}
		catch (IOException e)
		{
			ui.display("Client IOException; client probably closed without signaling");
		}

		commandFactory.make("Disconnect")
					.execute(client, Command.NO_ARGS);
	}

	private void handleException(Client client, Exception e) throws IOException
	{
		if (client.isConnected())
			client.send(e.getMessage());

		ui.displayError(e.getMessage());
	}

}
