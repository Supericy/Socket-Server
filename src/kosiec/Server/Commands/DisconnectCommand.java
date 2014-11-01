package kosiec.Server.Commands;

import kosiec.Server.Command;
import kosiec.Server.UserInterface;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Chad on 11/1/2014.
 */
public class DisconnectCommand implements Command {

	protected UserInterface ui;

	public DisconnectCommand(UserInterface ui)
	{
		this.ui = ui;
	}

	@Override
	public void execute(Socket socket, String[] args)
	{
		try
		{
			new PrintStream(socket.getOutputStream()).println("Disconnected");

			try
			{
				// give the client a chance to read the msg
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			socket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		ui.display("Client disconnected");
	}
}
