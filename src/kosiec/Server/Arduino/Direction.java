package kosiec.Server.Arduino;

/**
 * Created by Chad on 10/31/2014.
 */
public enum Direction {
	FORWARD	((byte) 'w'),
	RIGHT	((byte) 'a'),
	REVERSE	((byte) 's'),
	LEFT	((byte) 'd');

	private final byte arduinoValue;

	private Direction(byte arduinoValue)
	{
		this.arduinoValue = arduinoValue;
	}

	public byte getArduinoValue()
	{
		return arduinoValue;
	}

}
