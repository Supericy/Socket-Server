package pkg.Server.Command;

/**
 * Created by Chad on 11/5/2014.
 */
public class PermissionDeniedException extends CommandException {
	private static final String DEFAULT_MESSAGE = "permission denied";

	public PermissionDeniedException() { super(DEFAULT_MESSAGE); }
	public PermissionDeniedException(String msg)
	{
		super(msg);
	}
}
