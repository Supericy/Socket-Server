package pkg.Server.Command.Commands;

import jssc.SerialPortException;
import pkg.Server.Arduino.Direction;
import pkg.Server.Arduino.SerialPort.SerialPortDirectionWriter;
import pkg.Server.Client;
import pkg.Server.Command.Command;
import pkg.Server.Command.CommandException;
import pkg.Server.Command.Permission;
import pkg.Server.Command.PermissionDeniedException;

import java.io.IOException;

/**
 * Created by Chad on 10/31/2014.
 */
public class ArduinoCommand implements Command {

	private final SerialPortDirectionWriter serialPortDirectionWriter;

	public ArduinoCommand(SerialPortDirectionWriter serialPortDirectionWriter)
	{
		this.serialPortDirectionWriter = serialPortDirectionWriter;
	}

	@Override
	public void execute(Client client, String[] args)
	{
		if (!client.hasPermission(Permission.ARDUINO))
			throw new PermissionDeniedException("ArduinoCommand: permission denied");

		validateArgsLength(args.length);

		Direction direction = getJoystickDirection(args[0]);
		int amount = getAmount(args[1]);

		try
		{
			serialPortDirectionWriter.write(direction, amount);
			client.send("ok");
		}
		catch (SerialPortException e)
		{
			System.err.println(e.getMessage());
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
	}

	private void validateArgsLength(int length) throws CommandException
	{
		if (length != 2)
			throw new CommandException("ArduinoCommand expects 2 args: a (Direction)direction and an (int)amount");
	}

	private Direction getJoystickDirection(String arg)
	{
		try
		{
			return Direction.valueOf(arg.toUpperCase());
		}
		catch (IllegalArgumentException e)
		{
			throw new CommandException("ArduinoCommand expects the direction to be a valid JoystickDirection");
		}
	}

	private int getAmount(String arg) throws CommandException
	{
		int amount = Integer.parseInt(arg);

		if (amount < 0 || amount > 255)
			throw new CommandException("ArduinoCommand expects the amount to be an integer between 0-255 inclusive");

		return amount;
	}

}
