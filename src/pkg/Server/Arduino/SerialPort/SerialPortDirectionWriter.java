package pkg.Server.Arduino.SerialPort;

import jssc.SerialPortException;
import pkg.Server.Arduino.Direction;

/**
 * Created by Chad on 10/31/2014.
 */
public interface SerialPortDirectionWriter {

	public void write(Direction direction, int amount) throws SerialPortException;

}
