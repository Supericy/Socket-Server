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
		if (args.length < 2)
			throw new CommandException("ArduinoCommand expects 2 args: a (Direction)direction and an (int)amount");

		JoystickDirection direction = JoystickDirection.valueOf(args[0]);
		int amount = Integer.parseInt(args[1]);

		try
		{
			serialPortDirectionWriter.write(direction, amount);
		}
		catch (SerialPortException e)
		{
			e.printStackTrace();
		}
	}

}
