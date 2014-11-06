package kosiec.Server.Command.Commands;

import kosiec.Server.Command.Command;
import kosiec.Server.UserInterface;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

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
	public void execute(Socket socket, String[] args)
	{
		try
		{
			new PrintStream(socket.getOutputStream()).println("Connected");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		ui.display("Client connected from: " + socket.getInetAddress());
	}

}
