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
		byte directionByte;
		byte amountByte;

		switch (direction)
		{
			case UP:
				directionByte = (byte)'w';
				break;
			case DOWN:
				directionByte = (byte)'s';
				break;
			case LEFT:
				directionByte = (byte)'a';
				break;
			case RIGHT:
				directionByte = (byte)'d';
				break;
			default:
				throw new SerialPortException("JsscSerialPortDirectionWriter", "write", "Direction supplied not supported");
		}

		amountByte = (byte)amount;

		serialPort.writeBytes(new byte[] {directionByte, amountByte});
	}
}
