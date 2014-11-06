package kosiec.Server.Arduino.SerialPort;

import jssc.SerialPort;
import jssc.SerialPortException;
import kosiec.Server.Arduino.Direction;

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
	public void write(Direction direction, int amount) throws SerialPortException
	{
		byte directionByte = direction.getArduinoValue();
		byte amountByte = (byte) amount;

		serialPort.writeBytes(new byte[] {directionByte, amountByte});
	}
}
