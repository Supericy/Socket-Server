package kosiec.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Chad on 10/31/2014.
 */
public class ClientHandler implements Handler {

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
			PrintStream socketOut = new PrintStream(socket.getOutputStream());

			while (!done && !socket.isClosed())
			{
				try
				{
					String line = socketIn.readLine();

					if (line != null)
					{
						MetaCommand meta = commandTranslator.decode(line);

						if (meta != null)
						{
							Command c = commandFactory.make(meta.getCommandName());
							c.execute(socket, meta.getCommandArgs());
						}
					}
				}
				catch (CommandException e)
				{
					if (!socket.isClosed())
						socketOut.println(e.getMessage());
					System.err.println(e.getMessage());
				}
			}

			socket.close();
		}
		catch (IOException e)
		{
			System.err.println("Client IOException; client probably closed without signaling");
//			e.printStackTrace();
		}
	}


}
