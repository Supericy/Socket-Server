package pkg.Server.Arduino;

/**
 * Created by Chad on 10/31/2014.
 */
public enum Direction {
	FORWARD	((byte) 'w'),
	RIGHT	((byte) 'a'),
	REVERSE	((byte) 's'),
	LEFT	((byte) 'd'),
	STOP	((byte) 'q'),
	STRAIGHT((byte) 'n');

	private final byte directionByte;

	private Direction(byte directionByte)
	{
		this.directionByte = directionByte;
	}

	public byte getDirectionByte()
	{
		return directionByte;
	}

}
