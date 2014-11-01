package kosiec.Server.Arduino.SerialPort;

import jssc.SerialPortException;
import kosiec.Server.Arduino.JoystickDirection;

/**
 * Created by Chad on 10/31/2014.
 */
public interface SerialPortDirectionWriter {

	public void write(JoystickDirection direction, int amount) throws SerialPortException;

}
