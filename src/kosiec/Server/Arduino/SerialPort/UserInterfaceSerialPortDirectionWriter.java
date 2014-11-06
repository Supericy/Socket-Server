package kosiec.Server.Arduino.SerialPort;

import jssc.SerialPortException;
import kosiec.Server.Arduino.Direction;
import kosiec.Server.UserInterface;

/**
 * Created by Chad on 10/31/2014.
 */
public class UserInterfaceSerialPortDirectionWriter implements SerialPortDirectionWriter {

	private final UserInterface ui;

	public UserInterfaceSerialPortDirectionWriter(UserInterface ui)
	{
		this.ui = ui;
	}

	@Override
	public void write(Direction direction, int amount) throws SerialPortException
	{
		ui.display(direction + ";" + amount);
	}

}
