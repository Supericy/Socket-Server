package pkg.Server.Arduino.SerialPort;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import pkg.Server.UserInterface;

/**
 * Created by Chad on 10/31/2014.
 */
public class SerialPortReader implements SerialPortEventListener {

	private final SerialPort serialPort;
	private final UserInterface ui;

	public SerialPortReader(SerialPort serialPort, UserInterface ui)
	{
		this.serialPort = serialPort;
		this.ui = ui;
	}

	public void serialEvent(SerialPortEvent event) {
		if (event.isRXCHAR()) {//If data is available
			//System.out.println(event.getEventValue());
			if (event.getEventValue() > 0) {//Check bytes count in the input buffer

				//Read data, if 10 bytes available
				try {
					byte buffer[] = serialPort.readBytes(1);

					ui.display(String.valueOf(buffer[0]));
				} catch (SerialPortException ex) {
					ui.display(ex.toString());
				}
			}
		} else if (event.isCTS()) {//If CTS line has changed state
			if (event.getEventValue() == 1) {//If line is ON
				ui.display("CTS - ON");
			} else {
				ui.display("CTS - OFF");
			}
		} else if (event.isDSR()) {///If DSR line has changed state
			if (event.getEventValue() == 1) {//If line is ON
				ui.display("DSR - ON");
			} else {
				ui.display("DSR - OFF");
			}
		}
	}
}
