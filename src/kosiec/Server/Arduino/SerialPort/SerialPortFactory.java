package kosiec.Server.Arduino.SerialPort;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * Created by Chad on 10/31/2014.
 */
public class SerialPortFactory {

	public SerialPort make()
	{
		SerialPort serialPort = new SerialPort("COM3");

		try
		{
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_9600,
								SerialPort.DATABITS_8,
								SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);
		}
		catch (SerialPortException e)
		{
			serialPort = null;
		}

		return serialPort;
	}

}
