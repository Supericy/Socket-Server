package kosiec.Server;

import kosiec.Server.Command.Command;
import kosiec.Server.Command.CommandException;
import kosiec.Server.Command.CommandFactory;
import kosiec.Server.Command.CommandTranslator;

import java.io.IOException;

/**
 * Created by Chad on 10/31/2014.
 */
public class ClientHandler implements Handler<Client> {

	private final CommandTranslator commandTranslator;
	private final CommandFactory commandFactory;
	private final UserInterface ui;

	public ClientHandler(CommandTranslator commandTranslator, CommandFactory commandFactory, UserInterface ui)
	{
		this.commandTranslator = commandTranslator;
		this.commandFactory = commandFactory;
		this.ui = ui;
	}

	public void handle(Client client)
	{
		try
		{
			// execute a connect command
			commandFactory.make("Connect")
						  .execute(client, new String[0]);

			while (client.isConnected())
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

			client.disconnect();
		}
		catch (IOException e)
		{
			// execute a connect command
			commandFactory.make("Disconnect")
					.execute(client, new String[0]);

//			System.err.println("Client IOException; client probably closed without signaling");
		}
	}

	private void handleException(Client client, Exception e) throws IOException
	{
		if (client.isConnected())
			client.send(e.getMessage());

		ui.displayError(e.getMessage());
	}

}
