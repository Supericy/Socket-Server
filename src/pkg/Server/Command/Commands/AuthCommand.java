package pkg.Server.Command.Commands;

import pkg.Server.Client;
import pkg.Server.Command.Command;
import pkg.Server.Command.CommandException;
import pkg.Server.Command.Permission;

import java.io.IOException;

/**
 * Created by Chad on 11/5/2014.
 */
public class AuthCommand implements Command {

	private static final String GLOBAL_PASSWORD = "ExtremePassword!";

	@Override
	public void execute(Client client, String[] args)
	{
		validateArgsLength(args.length);

		try
		{
			String password = args[0];

			if (GLOBAL_PASSWORD.equals(password))
			{
				client.addPermission(Permission.ARDUINO);

				client.send("Authenticated");
			}
			else
			{
				client.send("Denied");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void validateArgsLength(int length) throws CommandException
	{
		if (length != 1)
			throw new CommandException("AuthCommand expects 1 arg: (String)password");
	}

}
