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
            if(client.isConnected())
            {
                try
                {
                    String line = client.read();
                    // Authenticate Client as an authorized User of our server
                    if(verifyClient(client, line))
                    {
                        // all good
                    }
                    else
                    {   // kill that mother fucker
                        commandFactory.make("Disconnect")
                                .execute(client, new String[0]);
                    }
                }
                catch (CommandException e)
                {
                    handleException(client, e);
                }
            }
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

    private boolean verifyClient(Client client, String clientGreeting)
    {
        boolean verified = false;
        try {
            int serverIp = Integer.parseInt(client.connectedToInetAddress().toString().replaceAll("\\/","").replaceAll("\\.", ""));
            int clientIp = Integer.parseInt(client.getInetAddress().toString().replaceAll("\\/","").replaceAll("\\.", ""));
            int received = Integer.parseInt(clientGreeting);
            if(serverIp - clientIp == received)
                verified = true;

        }
        catch (NumberFormatException e)
        {
            // they didn't send what i wanted.
        }
        return verified;
    }
}
