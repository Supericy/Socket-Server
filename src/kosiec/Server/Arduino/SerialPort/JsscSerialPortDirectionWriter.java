package kosiec.Server.Arduino.SerialPort;

import jssc.SerialPort;
import jssc.SerialPortException;
import kosiec.Server.Arduino.JoystickDirection;

/**
 * Created by Chad on 10/31/2014.
 */
public class JsscSerialPortDirectionWriter implements SerialPortDirectionWriter {

	private final SerialPort serialPort;

	public JsscSerialPortDirectionWriter(SerialPort serialPort)
	{
		this.serialPort = serialPort;
	}

	@Override
	public void write(JoystickDirection direction, int amount) throws SerialPortException
	{

	}
}
