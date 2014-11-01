package kosiec.Server.Arduino.Commands;

import jssc.SerialPortException;
import kosiec.Server.Arduino.JoystickDirection;
import kosiec.Server.Arduino.SerialPort.SerialPortDirectionWriter;
import kosiec.Server.Command;
import kosiec.Server.CommandException;

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
	public void execute(String[] args)
	{
		validateArgsLength(args.length);

		JoystickDirection direction = getJoystickDirection(args[0]);
		int amount = getAmount(args[1]);

		try
		{
			serialPortDirectionWriter.write(direction, amount);
		}
		catch (SerialPortException e)
		{
			System.err.println(e.getMessage());
		}
	}

	private void validateArgsLength(int length) throws CommandException
	{
		if (length != 2)
			throw new CommandException("ArduinoCommand expects 2 args: a (Direction)direction and an (int)amount");
	}

	private JoystickDirection getJoystickDirection(String arg)
	{
		try
		{
			return JoystickDirection.valueOf(arg.toUpperCase());
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
