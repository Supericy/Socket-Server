package kosiec.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Chad on 10/31/2014.
 */
public class ClientHandler implements Handler {

	private final CommandTranslator commandTranslator;
	private final CommandFactory commandFactory;

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
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String line = br.readLine();

			MetaCommand meta = commandTranslator.decode(line);

			Command command = commandFactory.make(meta.getCommandName());

			try
			{
				command.execute(meta.getCommandArgs());
			}
			catch (CommandException e)
			{
				System.err.println(e.getMessage());
			}

			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}



}
