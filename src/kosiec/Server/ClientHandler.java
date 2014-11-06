package kosiec.Server;

import kosiec.Server.Command.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Chad on 10/31/2014.
 */
public class ClientHandler implements SocketHandler {

	private final CommandTranslator commandTranslator;
	private final CommandFactory commandFactory;

	private boolean done = false;

	public ClientHandler(CommandTranslator commandTranslator, CommandFactory commandFactory)
	{
		this.commandTranslator = commandTranslator;
		this.commandFactory = commandFactory;
	}

	@Override
	public void handle(Socket socket)
	{
		try
		{
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			PrintStream socketOut = new PrintStream(socket.getOutputStream());

			// execute a connect command
			commandFactory.make("Connect")
						  .execute(socket, new String[0]);

			while (!done && !socket.isClosed())
			{
				try
				{
					String line = socketIn.readLine();

					if (line != null)
					{
						MetaCommand meta = commandTranslator.decode(line);

						// Connect command is off limits from the client, since we only execute it once.
						if (!meta.getCommandName().equals("Connect"))
						{
							Command c = commandFactory.make(meta.getCommandName());
							c.execute(socket, meta.getCommandArgs());
						}
					}
				}
				catch (CommandException e)
				{
					handleException(socket, e);
				}
				catch (TranslationException e)
				{
					handleException(socket, e);
				}
			}

			socket.close();
		}
		catch (IOException e)
		{
			// execute a connect command
			commandFactory.make("Disconnect")
					.execute(socket, new String[0]);

//			System.err.println("Client IOException; client probably closed without signaling");
		}
	}

	private void handleException(Socket socket, Exception e) throws IOException
	{
		if (!socket.isClosed())
			new PrintStream(socket.getOutputStream()).println(e.getMessage());
		System.err.println(e.getMessage());
	}


}
